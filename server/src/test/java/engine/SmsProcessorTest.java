package engine;

import org.junit.Assert;
import org.junit.Test;
import org.smslib.OutboundMessage;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

/**
 * SmsProcessorTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class SmsProcessorTest {

    @Test
    public void sendMessageTest(){
        ArrayList<OutboundMessage> output = new ArrayList<>();
        SmsProcessor smsProcessor = new SmsProcessor("","",output);
        smsProcessor.sendMessage("+33665639845","Hello");
        Assert.assertEquals("+33665639845",output.get(0).getRecipient());
        Assert.assertEquals("Hello",output.get(0).getText());
    }

    @Test
    public void runTest(){
        ArrayList<OutboundMessage> output = new ArrayList<>();
        SmsProcessor smsProcessor = new SmsProcessor("Salut je test ITS","+33665639845",output);
        smsProcessor.run();
        Assert.assertEquals("+33665639845",output.get(0).getRecipient());
        Assert.assertEquals("eJwLd3WysskwsvNNLC1LzCxOVUjOz81NzEtJtdEHigIAmfkKDw==",output.get(0).getText());
    }
}
