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
public class SmsSenderRunnable implements Runnable {
    private Boolean shutdown;
    public SmsSenderRunnable(Boolean shutdown, List<String> outputMessages){
        this.shutdown=shutdown;
    }

    @Override
    public void run() {
        for(;!shutdown;){
        }
    }
}
