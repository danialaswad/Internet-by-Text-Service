package engine;

/**
 * Created by Antoine on 13/06/2017.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smslib.InboundMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmsProcesserRunnableTest {
    private InboundMessage msg;
    private HashMap<String, String> outputMessages;
    private SmsCommand smsCommand;

    @Before
    public void initialize() {
        outputMessages = new HashMap<>();
        smsCommand = new SmsCommand();
        msg = new InboundMessage(new Date(160987),"","",3,"");
    }

    @Test
    public void checkModifyingHashmap(){
        SmsProcesserRunnable thread = new SmsProcesserRunnable(msg,outputMessages,smsCommand);
        thread.run();
        Assert.assertTrue(outputMessages.size()==1);

        outputMessages.clear();
        //multi-thread
        ArrayList<Runnable> runnableArrayList = new ArrayList<>();
        for (int i = 0; i<100;i++){
            SmsCommand smsC = new SmsCommand();
            SmsProcesserRunnable th= new SmsProcesserRunnable(msg,outputMessages,smsC);
            runnableArrayList.add(th);
        }
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (Runnable r:runnableArrayList){
            executor.execute(r);
        }
        executor.shutdown();
        //Assert.assertTrue(outputMessages.size()==100);
    }
}
