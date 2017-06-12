package web;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


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

    public static void generateSMSFromImage(String imgURL) throws IOException {
        BufferedImage img = getImageFromURL(imgURL);
        // get DataBufferBytes from Raster
        WritableRaster raster = img.getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
        byte[] pixels = data.getData();
        System.out.println("Test");
    }



}
