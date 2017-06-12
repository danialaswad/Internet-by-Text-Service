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
        //testIMG("GETIMG:http://anasghira.com/test.png");
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


    public static void testIMG(String text) throws IOException {
        SmsCommand smsCommand = new SmsCommand();
        String cryptedMsg = smsCommand.process(text,"+33668639846");
        System.out.println();
        String fin = ZLibCompression.compressToBase64(cryptedMsg,"UTF-8");

        String res = ZLibCompression.decompressFromBase64(fin,"UTF-8").split(":",2)[1];
        if(!res.equals(cryptedMsg.split(":")[1]))
            System.out.println("not");
        ImgReader.decodingImg(res);
        System.out.println("test");
    }


}
