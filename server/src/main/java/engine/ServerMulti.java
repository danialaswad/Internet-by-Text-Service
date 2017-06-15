package engine;

import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ServerMulti class
 *
 * @Author : ITS Team
 **/

public class ServerMulti  implements Runnable {

    private SerialModemGateway gateway;
    private SmsCommand smsCommand;
    private Boolean shutdown = false;

    private static final Logger LOG = Logger.getLogger(ServerMulti.class);

    private ArrayList<OutboundMessage> toSendQueue = new ArrayList<>();

    public ServerMulti(String pin, String smscNumber, String comPort) throws GatewayException {
        gateway = new SerialModemGateway("modem.com9", comPort,125000, "", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin(pin);
        gateway.setSmscNumber(smscNumber);
        Service.getInstance().addGateway(gateway);
    }


    public void run() {
        try {
            SmsSender senderThread = new SmsSender(toSendQueue);
            senderThread.start();
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
                    SmsProcessor smsProcessor = new SmsProcessor(msg.getText(),originator,toSendQueue);
                    Thread processorThread = new Thread(smsProcessor);
                    processorThread.start();
                    gateway.deleteMessage(msg);
                }
                msgList.clear();
            }
            senderThread.interrupt();
            senderThread.join();
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


    public void stop(){
        shutdown = true;
    }


}

