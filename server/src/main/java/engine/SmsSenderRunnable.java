package engine;

import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.modem.SerialModemGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Antoine on 12/06/2017.
 */
public class SmsSenderRunnable implements Runnable {
    private Boolean shutdown;
    private HashMap<String, String> outputMessages;

    private static final Logger LOG = Logger.getLogger(SmsSenderRunnable.class);

    public SmsSenderRunnable(Boolean shutdown, HashMap<String, String> outputMessages){
        this.shutdown=shutdown;
        this.outputMessages = outputMessages;
    }

    @Override
    public void run() {
        for(;!shutdown;){
            try {
                Iterator iterator = outputMessages.entrySet().iterator();
                while (iterator.hasNext()){
                    HashMap.Entry<String,String> pair = (HashMap.Entry<String,String>)iterator.next();
                    sendMessage(pair.getKey(),pair.getValue());
                    iterator.remove();
                }
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

    private void sendMessage(String to, String body) throws InterruptedException, TimeoutException, GatewayException, IOException {
        OutboundMessage msg = new OutboundMessage(to, body);
        Service.getInstance().sendMessage(msg);
        LOG.info("Output Message :");
        LOG.info("\tMessage : " +  msg.getText());
    }
}
