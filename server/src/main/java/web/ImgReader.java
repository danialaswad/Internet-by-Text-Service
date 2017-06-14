package web;

import compression.ZLibCompression;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.HttpURLConnection;
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

    /**
     * Fetch an image from the given url
     * @param imgURL
     * @return BufferedImage
     * @throws IOException
     */
    static BufferedImage getImageFromURL(String imgURL) throws IOException {
        URL url = new URL(imgURL);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        return ImageIO.read(connection.getInputStream());
    }

    public static byte[] getImageArray(String imgURL) throws IOException {
        //TODO
        byte[] imageInByte;
        BufferedImage img = getImageFromURL(imgURL);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "bmp", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        System.out.println("Test2");
        return imageInByte;
    }

    /**
     * Converts a Bufferedmage into an array of bytes
     * @param image
     * @return byte[]
     * @throws IOException
     */
    static byte[] getImageArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

}
