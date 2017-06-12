package engine;

import compression.ZLibCompression;
import database.ITSDatabase;
import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SmsServer class
 * @Author : ITS Team
 */

public class SmsServer  implements Runnable {

    private SerialModemGateway gateway;
    private SmsCommand smsCommand;
    private Boolean shutdown = false;

    private static final Logger LOG = Logger.getLogger(SmsServer.class);

    private static final String DBFILE = "database.txt";

    private ITSDatabase database = ITSDatabase.instance();

    public SmsServer(String pin, String smscNumber, String comPort) throws GatewayException {
        gateway = new SerialModemGateway("modem.com9", comPort,125000, "", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin(pin);
        gateway.setSmscNumber(smscNumber);
        Service.getInstance().addGateway(gateway);
    }


    public void run() {
        try {
            readDatabase();
            smsCommand = new SmsCommand();
            Service.getInstance().startService();
            logModemInfo();
            ArrayList<InboundMessage> msgList = new ArrayList<>();

            //Pool
            ExecutorService executor = Executors.newFixedThreadPool(2);
            //Runnables
            SmsProcesserRunnable smsProcesserRunnable = new SmsProcesserRunnable();
            SmsReceiverRunnable smsReceiverRunnable = new SmsReceiverRunnable();
            while(!shutdown){

                executor.execute(smsReceiverRunnable);
                executor.execute(smsProcesserRunnable);
                //attente de la terminaison de toutes les taches
                executor.shutdown(); //normalement on arrive pas ici
                Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
                for (InboundMessage msg : msgList){
                    LOG.info("Input Message :");
                    LOG.info("\tMessage : " +  msg.getText());
                    LOG.info("\tSender : " +  msg.getOriginator());
                    String cryptedMsg = ZLibCompression.compressToBase64(smsCommand.process(msg.getText()),"UTF-8");
                    sendMessage("+"+msg.getOriginator(),cryptedMsg);
                    gateway.deleteMessage(msg);
                }
            }
            //arrête les threads en cours et renvoie ceux qui étaient en attentes
            List<Runnable> awaitingExecution = executor.shutdownNow();
            //nettoye la liste de demande
            msgList.clear();
            //sauvegarde la database pour la prochaine utilisation
            saveDatabase();
            Service.getInstance().stopService();
            Service.getInstance().removeGateway(gateway);

        } catch (SMSLibException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void logModemInfo() throws InterruptedException, TimeoutException, GatewayException, IOException {
        LOG.info("Modem Manufacturer: " + gateway.getManufacturer());
        LOG.info("Modem Model: " + gateway.getModel());
        LOG.info("Modem SIM IMSI: " + gateway.getImsi());
        LOG.info("Modem Signal Level: " + gateway.getSignalLevel() + " dBm");
        LOG.info("Modem Battery Level: " + gateway.getBatteryLevel() + "%");
    }



    private void sendMessage(String to, String body) throws InterruptedException, TimeoutException, GatewayException, IOException {
        OutboundMessage msg = new OutboundMessage(to, body);
        Service.getInstance().sendMessage(msg);
        LOG.info("Output Message :");
        LOG.info("\tMessage : " +  msg.getText());
    }

    private void saveDatabase(){

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(DBFILE);
            oos = new ObjectOutputStream(fout);
            // sérialization de l'objet
            oos.writeObject(database) ;
            LOG.info("Database stored in file " + DBFILE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readDatabase(){

        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream(DBFILE);
            ois = new ObjectInputStream(fin);
            database = (ITSDatabase) ois.readObject();
            ITSDatabase.setInstance(database);
            LOG.info("Database retrieve from file " + DBFILE);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void stop() throws InterruptedException, SMSLibException, IOException {
        shutdown = true;
    }


}
