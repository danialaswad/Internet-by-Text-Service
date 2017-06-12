package web;

import compression.ZLibCompression;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;


/**
 * ImgReader
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class ImgReader {

    private static BufferedImage getImageFromURL(String imgURL) throws IOException {
        URL url = new URL(imgURL);
        return ImageIO.read(url);
    }

    public static byte[] getImageArray(String imgURL) throws IOException {
        byte[] imageInByte;
        BufferedImage img = getImageFromURL(imgURL);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        System.out.println("Test2");
        return imageInByte;
    }

    public static void decodingImg(String txt) throws IOException {
        //TODO
        byte[] decodedText = ZLibCompression.decodeImage(txt);
        InputStream in = new ByteArrayInputStream(decodedText);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "png", new File("test.png"));
        System.out.println(decodedText);
    }



}
