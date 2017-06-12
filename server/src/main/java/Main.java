import compression.ZLibCompression;
import engine.SmsCommand;
import engine.SmsServer;
import org.apache.log4j.Logger;
import org.smslib.SMSLibException;
import twitter4j.TwitterException;
import weather.WeatherProxy;
import web.ImgReader;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Main class
 * @Author : ITS Team
 */
public class Main {

    public static void main(String args[]) throws InterruptedException, SMSLibException, IOException, TwitterException {
        //testSmsServer();
        //testTwitter();
        //testDB();
        //writeDB();
        //ImgReader.test("http://anasghira.com/test.png");
        //ImgReader.test("http://www.ca-stomer.fr/var/ptic/storage/images/media/caroussel-page-d-accueil/image-test-1/96100-1-fre-FR/Image-test-1_large.jpg");
        //ImgReader.decodingImg("eJzz9HW3er9/b4CfOy+XFBcDAwOvp4dLEJBmBWEOJiDJxAtUAERApq6ni2NIBZCdDMSpqUAiPalKG0gxOfLIQFRBEIcdnFmvDJIPYb3SDaTZgVhSQoIhpV40F8qFmMzg6ernAmQ6JQAJADLtQ64=");
        //System.out.println(WeatherProxy.getWeather("Nice"));
        testIMG("GETIMG:http://anasghira.com/test.png");
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


    public static void testIMG(String text) throws IOException {
        SmsCommand smsCommand = new SmsCommand();
        String cryptedMsg = smsCommand.process(text,"+33668639846");
        System.out.println();
        String fin = ZLibCompression.compressToBase64(cryptedMsg,"UTF-8");

        String res = ZLibCompression.decompressFromBase64(fin,"UTF-8").split(":",2)[1];
        if(!res.equals(cryptedMsg.split(":")[1]))
            System.out.println("not");
        ImgReader.decodingImg(res);
        /*int [] data = ImgReader.SMS("http://anasghira.com/test.png");
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);

        byte[] array = byteBuffer.array();*/
        System.out.println("test");
    }


}
