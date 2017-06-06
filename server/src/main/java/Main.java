import compression.ZLibCompression;
import engine.SmsCommand;
import engine.SmsServer;
import org.smslib.SMSLibException;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import web.URLReader;
import web.WebPageCleaner;

import java.io.IOException;

/**
 * https://blogserius.blogspot.fr
 *
 * http://www.nicematin.com
 * http://www.apple.com
 * https://en.wikipedia.org/wiki/Silverio_Pérez
 */
public class Main {

    public static void main(String args[]) throws InterruptedException, SMSLibException, IOException, TwitterException {
        //AIzaSyCPwDK5HD1C1kKJSLhLoRquom6mwv9Ydhs
        //URLReader w = new URLReader("http://anasghira.com/lorem");
        //new WebPageCleaner().cleanWebPage(w.fetchFile());
        //Document document = w.fetchFile();
        //System.out.println(new WebPageCleaner().cleanWebPage(w.fetchFile(), w.getUrlString()));
        //testSmsServer();
        testTwitter();
    }

    public static void testSmsServer() throws InterruptedException, SMSLibException, IOException {

        SmsServer server = new SmsServer("0000","+33609001390","COM9");
        try
        {
            server.run();
            //server.testSend("+33668639846","<header class=\"so-header js-so-header _fixed\">\n");
        }
        catch (Exception e)
        {
            server.stop();
            e.printStackTrace();
        }
    }

    public static void testTwitter(){
        SmsCommand smsCommand = new SmsCommand();
        String configResult = smsCommand.process("TWITTERCONF:941791010-bU9IpOXf8KgOPQ2OdXYAQw4qBKOtSG8a2qKGUdEg,LG8wAfcGVUoCgNCt44VLUJjx2rpbDLIfYHX2OHZnIy4ZT,941791010");
        System.out.println(configResult);
        String timeline = smsCommand.process("TWEET:941791010,ITSTest");
        System.out.println(timeline);
        System.out.println("FIN");

    }


}
