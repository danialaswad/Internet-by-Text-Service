package engine;

import compression.ZLibCompression;
import org.apache.log4j.Logger;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antoine on 12/06/2017.
 */
public class SmsProcesserRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(SmsProcesserRunnable.class);
    private InboundMessage msg;
    private  HashMap<String, String> outputMessages;
    private SmsCommand smsCommand;
    public SmsProcesserRunnable(InboundMessage msg, HashMap<String, String> outputMessages, SmsCommand smsCommand) {
        this.msg = msg;
        this.outputMessages = outputMessages;
        this.smsCommand = smsCommand;
    }

    @Override
    public void run() {
        try {
            LOG.info("Input Message :");
            String originator = "+"+msg.getOriginator();
            LOG.info("\tMessage : " +  msg.getText());
            LOG.info("\tSender : " +  originator);
            //mutex?
            String compressMsg = ZLibCompression.compressToBase64(smsCommand.process(msg.getText(),originator),"UTF-8");
            outputMessages.put(originator,compressMsg);
            Service.getInstance().deleteMessage(msg);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (GatewayException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
