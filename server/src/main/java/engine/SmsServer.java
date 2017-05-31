package engine;


import org.smslib.*;
import org.smslib.modem.SerialModemGateway;
import web.URLReader;
import web.WebPageCleaner;

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
        /*String msgBody = "<h1>HTML Ipsum Presents</h1>\n" +
                "\n" +
                "<p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. <em>Aenean ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"http://google.fr\" title=\"Dracula (1958 film)\">Dracula</a> in turpis pulvinar facilisis. Ut felis.</p>\n" +
                "\n" +
                "<h2>Header Level 2</h2>";
        //TODO decouper le string
        sendMessage("+33666360803",msgBody);*/
        ArrayList<InboundMessage> msgList = new ArrayList<InboundMessage>();
        while(true){
            Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
            for (InboundMessage msg : msgList){
                System.out.println(msg);
                //msg.getText();
                URLReader w = new URLReader(msg.getText());
                String msgBody = new WebPageCleaner().cleanWebPage(w.fetchFile(), w.getUrlString());
                /*String msgBody = "<h1>HTML Ipsum Presents</h1>\n" +
                        "\n" +
                        "<p><strong>Pellentesque habitant morbi tristique</strong> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. <em>Aenean ultricies mi vitae est.</em> Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, <code>commodo vitae</code>, ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"#\">Donec non enim</a> in turpis pulvinar facilisis. Ut felis.</p>\n" +
                        "\n" +
                        "<h2>Header Level 2</h2>";*/
                //TODO decouper le string
                sendMessage("+"+msg.getOriginator(),msgBody);
                gateway.deleteMessage(msg);
            }

            msgList.clear();

        }
    }

    private void sendMessage(String to, String body) throws InterruptedException, TimeoutException, GatewayException, IOException {
        OutboundMessage msg = new OutboundMessage(to, body);
        Service.getInstance().sendMessage(msg);
        System.out.println(msg);
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
