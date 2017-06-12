package engine;


import database.ITSDatabase;
import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SmsServer class
 * @Author : ITS Team
 */

public class SmsServer  implements Runnable {

    private SerialModemGateway gateway;
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
            Service.getInstance().startService();
            logModemInfo();
            List<InboundMessage> inputMessages = new ArrayList<>();
            HashMap<String,String> outputMessages = new HashMap<String, String>();
            //Pool
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); //ou Executors.newFixedThreadPool(2)
            //Runnables
            SmsReceiverRunnable smsReceiverRunnable = new SmsReceiverRunnable(shutdown,inputMessages,outputMessages);
            SmsSenderRunnable smsSenderRunnable = new SmsSenderRunnable(shutdown,outputMessages);
            executor.execute(smsReceiverRunnable);
            executor.execute(smsSenderRunnable);

            while(!shutdown){
                Service.getInstance().readMessages(inputMessages, InboundMessage.MessageClasses.ALL);
            }

            //nettoye la liste des demandes et des réponses
            outputMessages.clear();
            inputMessages.clear();

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
