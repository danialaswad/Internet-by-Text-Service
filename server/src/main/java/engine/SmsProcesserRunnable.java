package engine;

import compression.ZLibCompression;
import org.apache.log4j.Logger;
import org.smslib.InboundMessage;

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
        LOG.info("Input Message :");
        LOG.info("\tMessage : " +  msg.getText());
        LOG.info("\tSender : " +  msg.getOriginator());
        //mutex?
        String compressMsg = ZLibCompression.compressToBase64(smsCommand.process(msg.getText()),"UTF-8");
        outputMessages.put("+"+msg.getOriginator(),compressMsg);
    }
}
