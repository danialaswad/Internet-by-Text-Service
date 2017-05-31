package engine;


import org.smslib.*;
import org.smslib.modem.SerialModemGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Anasse on 30/05/2017.
 */
public class SmsServer {

    SerialModemGateway gateway;

    public void run() throws Exception
    {
        OutboundNotification outboundNotification = new OutboundNotification();
        InboundNotification inboundNotification = new InboundNotification();
        System.out.println("Example: Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        gateway = new SerialModemGateway("modem.com9", "COM9",125000, "", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");
        gateway.setSmscNumber("+33609001390");
        Service.getInstance().setOutboundMessageNotification(outboundNotification);
        Service.getInstance().setInboundMessageNotification(inboundNotification);
        Service.getInstance().addGateway(gateway);
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
        while(true){
            Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
            for (InboundMessage msg : msgList){
                System.out.println(msg);
                sendMessage("+"+msg.getOriginator(),"J'ai recu ton msg !");
                gateway.deleteMessage(msg);
            }

            msgList.clear();

        }
    }

    private void sendMessage(String to, String body) throws InterruptedException, TimeoutException, GatewayException, IOException {
        OutboundMessage msg = new OutboundMessage(to, body);
        Service.getInstance().sendMessage(msg);
    }

    public class InboundNotification implements IInboundMessageNotification
    {
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg)
        {
            if (msgType == Message.MessageTypes.INBOUND) {

                System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            }
            else if (msgType == Message.MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(AGateway gateway, OutboundMessage msg)
        {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    public void stop() throws InterruptedException, SMSLibException, IOException {
        Service.getInstance().stopService();
        Service.getInstance().removeGateway(gateway);
    }


}
