package engine;

import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antoine on 12/06/2017.
 */
public class SmsReceiverRunnable implements Runnable{
    private Boolean shutdown;
    private List<InboundMessage> inputMessages;
    private  HashMap<String, String> outputMessages;
    private SmsCommand smsCommand;
    public SmsReceiverRunnable(Boolean shutdown, List<InboundMessage> inputMessages, HashMap<String, String> outputMessages) {
        this.shutdown = shutdown;
        this.inputMessages = inputMessages;
        this.outputMessages = outputMessages;
        smsCommand = new SmsCommand();
    }

    @Override
    public void run() {
        for(;!shutdown;){
            for (InboundMessage msg : inputMessages){
                SmsProcesserRunnable smsProcesserRunnable = new SmsProcesserRunnable(msg,outputMessages,smsCommand);
                smsProcesserRunnable.run();
            }
        }
    }
}
