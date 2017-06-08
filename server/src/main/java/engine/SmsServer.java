package engine;


import compression.ZLibCompression;
import database.ITSDatabase;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Anasse on 30/05/2017.
 */
public class SmsServer  implements Runnable {

    private SerialModemGateway gateway;
    private SmsCommand smsCommand;
    private Boolean shutdown = false;

    private ITSDatabase database = ITSDatabase.instance();

    public SmsServer(String pin, String smscNumber, String comPort) throws GatewayException {
        /*OutboundNotification outboundNotification = new OutboundNotification();
        InboundNotification inboundNotification = new InboundNotification();*/
        gateway = new SerialModemGateway("modem.com9", comPort,125000, "", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin(pin);
        gateway.setSmscNumber(smscNumber);
        //Service.getInstance().setOutboundMessageNotification(outboundNotification);
        //Service.getInstance().setInboundMessageNotification(inboundNotification);
        Service.getInstance().addGateway(gateway);
    }


    public void run() {
        try {
            readDatabase();
            smsCommand = new SmsCommand();
            Service.getInstance().startService();
            System.out.println();
            System.out.println("Modem Information:");
            System.out.println("  Manufacturer: " + gateway.getManufacturer());
            System.out.println("  Model: " + gateway.getModel());
            System.out.println("  SIM IMSI: " + gateway.getImsi());
            System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
            System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
            System.out.println();
            ArrayList<InboundMessage> msgList = new ArrayList<InboundMessage>();
            while(!shutdown){
                Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
                for (InboundMessage msg : msgList){
                    String originNumber = "+"+msg.getOriginator();
                    System.out.println(msg);
                    String msgBody = smsCommand.process(msg.getText());
                    String cryptedMsg = ZLibCompression.compressToBase64(msgBody,"UTF-8");
                    sendMessage(originNumber,cryptedMsg);
                    gateway.deleteMessage(msg);
                }
                msgList.clear();
            }
            saveDatabase();
            Service.getInstance().stopService();
            Service.getInstance().removeGateway(gateway);

        } catch (SMSLibException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }



    private void sendMessage(String to, String body) throws InterruptedException, TimeoutException, GatewayException, IOException {
        OutboundMessage msg = new OutboundMessage(to, body);
        Service.getInstance().sendMessage(msg);
        System.out.println(msg);
    }

    private void saveDatabase(){

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream("database.txt");
            oos = new ObjectOutputStream(fout);

            // sérialization de l'objet
            oos.writeObject(database) ;

            System.out.println("Done");

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

    public void readDatabase(){

        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream("database.txt");
            ois = new ObjectInputStream(fin);
            database = (ITSDatabase) ois.readObject();
            ITSDatabase.setInstance(database);
            System.out.println("test");

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
        System.out.println("hello");
    }

    public void stop() throws InterruptedException, SMSLibException, IOException {
        shutdown = true;
    }


}
