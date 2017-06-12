package engine;

import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoine on 12/06/2017.
 */
public class SmsReceiverRunnable implements Runnable{
    private Boolean shutdown;
    private List<InboundMessage> inputMessages;
    private List<String> outputMessages;
    public SmsReceiverRunnable(Boolean shutdown, List<InboundMessage> inputMessages, List<String> outputMessages) {
        this.shutdown = shutdown;
        this.inputMessages = inputMessages;
        this.outputMessages = outputMessages;
    }

    @Override
    public void run() {
        for(;!shutdown;){
            for (InboundMessage msg : inputMessages){
                SmsProcesserRunnable smsProcesserRunnable = new SmsProcesserRunnable(msg,outputMessages);
            }
        }
    }
}
