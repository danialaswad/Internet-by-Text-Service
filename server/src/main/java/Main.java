import engine.ServerMulti;
import org.smslib.SMSLibException;
import twitter4j.TwitterException;

import java.io.IOException;

/**
 * Main class
 * 
 * @Author : ITS Team
 */
public class Main {

    public static void main(String args[]) throws InterruptedException, SMSLibException, IOException, TwitterException {
        testSmsServer();
    }

    public static void testSmsServer() throws InterruptedException, SMSLibException, IOException {

        ServerMulti server = new ServerMulti("0000","+33609001390","COM9");
        try
        {
            server.run();
        }
        catch (Exception e)
        {
            server.stop();
            e.printStackTrace();
        }
    }
}
