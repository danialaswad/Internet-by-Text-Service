package engine;

import org.apache.log4j.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SmsSender class
 *
 * @Author : ITS Team
 **/
public class SmsSender extends Thread {

    private SemOutputMessage outputSemaphore = SemOutputMessage.getInstance();
    private ArrayList<OutboundMessage> toSendQueue;

    private static final Logger LOG = Logger.getLogger(SmsSender.class);

    public SmsSender(ArrayList<OutboundMessage> output){
        toSendQueue = output;
    }

    @Override
    public void run() {
        while(!isInterrupted()){

            if(toSendQueue.size()>0){
                try {
                    outputSemaphore.getSemaphore().acquire();
                    OutboundMessage msg = toSendQueue.remove(0);
                    outputSemaphore.getSemaphore().release();
                    sendMessage(msg);
                } catch (InterruptedException | IOException | GatewayException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(OutboundMessage msg) throws InterruptedException, TimeoutException, GatewayException, IOException {
        Service.getInstance().sendMessage(msg);
        LOG.info("Output Message :");
        LOG.info("\tMessage : " +  msg.getText());
    }
}
