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
 * Main class
 * @Author : ITS Team
 */
public class Main {

    public static void main(String args[]) throws InterruptedException, SMSLibException, IOException, TwitterException {
        testSmsServer();
        //testTwitter();
        //testDB();
        //writeDB();
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


}
