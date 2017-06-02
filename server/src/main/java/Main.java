import compression.ZLibCompression;
import engine.SmsServer;
import org.smslib.SMSLibException;
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

    public static void main(String args[]) throws InterruptedException, SMSLibException, IOException {
        //AIzaSyCPwDK5HD1C1kKJSLhLoRquom6mwv9Ydhs
        //URLReader w = new URLReader("http://anasghira.com/lorem");
        //new WebPageCleaner().cleanWebPage(w.fetchFile());

        //Document document = w.fetchFile();
        //System.out.println(new WebPageCleaner().cleanWebPage(w.fetchFile(), w.getUrlString()));
        testSmsServer();
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


}
