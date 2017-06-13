package engine;

import compression.ZLibCompression;
import database.ITSDatabase;
import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SmsServer class
 * @Author : ITS Team
 **/

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
            smsCommand = new SmsCommand();
            Service.getInstance().startService();
            logModemInfo();
            ArrayList<InboundMessage> msgList = new ArrayList<>();
            while(!shutdown){
                Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
                for (InboundMessage msg : msgList){
                    LOG.info("Input Message :");
                    LOG.info("\tMessage : " +  msg.getText());
                    LOG.info("\tSender : " +  msg.getOriginator());
                    String originator = "+"+msg.getOriginator();
                    List<String> toSend = smsCommand.process(msg.getText(),originator);
                    for(String message : toSend) {
                        String cryptedMsg = ZLibCompression.compressToBase64(message, "UTF-8");
                        sendMessage(originator, cryptedMsg);
                    }
                    gateway.deleteMessage(msg);
                }
                msgList.clear();
            }
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

    public void stop() throws InterruptedException, SMSLibException, IOException {
        shutdown = true;
    }


}
