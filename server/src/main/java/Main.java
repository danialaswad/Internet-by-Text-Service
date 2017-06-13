import compression.ZLibCompression;
import engine.ServerMulti;
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
        testSmsServer();
        //testIMG("GETIMG:http://www.welovebuzz.com/wp-content/uploads/2016/05/cropped-paysage-maroc.jpg");
    }

    public static void testSmsServer() throws InterruptedException, SMSLibException, IOException {

        ServerMulti server = new ServerMulti("0000","+33609001390","COM9");
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
        /*SmsCommand smsCommand = new SmsCommand();
        String cryptedMsg = smsCommand.process(text,"+33668639846");
        System.out.println();
        String fin = ZLibCompression.compressToBase64(cryptedMsg,"UTF-8");

        String res = ZLibCompression.decompressFromBase64(fin,"UTF-8").split(":",2)[1];
        if(!res.equals(cryptedMsg.split(":")[1]))
            System.out.println("not");
        ImgReader.decodingImg(res);
        System.out.println("test");*/
    }
}
