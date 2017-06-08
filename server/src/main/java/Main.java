import compression.ZLibCompression;
import database.ITSDatabase;
import engine.SmsCommand;
import engine.SmsServer;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.smslib.SMSLibException;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import web.URLReader;
import web.WebPageCleaner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        //testTwitter();
        //testDB();
        writeDB();
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
       /* SmsCommand smsCommand = new SmsCommand();
        String configResult = smsCommand.process("TWITTERCONF:941791010-bU9IpOXf8KgOPQ2OdXYAQw4qBKOtSG8a2qKGUdEg,LG8wAfcGVUoCgNCt44VLUJjx2rpbDLIfYHX2OHZnIy4ZT,941791010");
        System.out.println(configResult);
        String home  = smsCommand.process("TWITTERHOME:941791010");
        System.out.println(home);
        System.out.println(home.length());

        home = smsCommand.process("TWITTERNEXT:941791010");
        System.out.println(home);
        System.out.println(home.length());
        System.out.println("FIN");*/
        Logger logger = Logger.getLogger(Main.class);
        logger.debug("Here is some DEBUG");
        logger.info("Here is some INFO");
        logger.warn("Here is some WARN");
        logger.error("Here is some ERROR");
        logger.fatal("Here is some FATAL");

    }

    public static void testDB(){
        ITSDatabase db = ITSDatabase.instance();
        db.maxTweetId().put("hello","hello");
        db.maxTweetId().put("anasse","con");

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("test.txt"))) {

            oos.writeObject(db);
            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void writeDB(){
        ITSDatabase db = null;

        try (ObjectInputStream ois
                     = new ObjectInputStream(new FileInputStream("test.txt"))) {

            db = (ITSDatabase) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(db.maxTweetId().toString());

    }


}
