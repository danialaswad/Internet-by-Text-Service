package engine;

import compression.ZLibCompression;
import org.apache.log4j.Logger;
import org.smslib.OutboundMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * SmsProcessor
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class SmsProcessor implements Runnable {

    String originator;
    String txt;
    SmsCommand smsCommand;
    ArrayList<OutboundMessage> toSendQueue;
    private static final Logger LOG = Logger.getLogger(SmsProcessor.class);
    private SemOutputMessage outputSemaphore = SemOutputMessage.getInstance();

    public SmsProcessor(String msg,String from, ArrayList<OutboundMessage> outputQueue){
        smsCommand = new SmsCommand();
        txt = msg;
        originator = from;
        toSendQueue = outputQueue;
    }


    public void run() {
        LOG.info("Process Message :");
        LOG.info("\tMessage : " +  txt);
        LOG.info("\tSender : " +  originator);
        List<String> toSend = smsCommand.process(txt,originator);
        for(String message : toSend) {
            String cryptedMsg = ZLibCompression.compressToBase64(message, "UTF-8");
            sendMessage(originator, cryptedMsg);
        }
    }

    private void sendMessage(String to, String body){
        OutboundMessage msg = new OutboundMessage(to, body);
        try {
            outputSemaphore.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toSendQueue.add(msg);
        outputSemaphore.getSemaphore().release();
        //lacher le semaphore
    }
}
