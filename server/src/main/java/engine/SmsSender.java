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
 * SmsSender
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Prendre le semaphore
                OutboundMessage msg = toSendQueue.remove(0);
                outputSemaphore.getSemaphore().release();
                //relacher le semaphore
                try {
                    sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (GatewayException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
