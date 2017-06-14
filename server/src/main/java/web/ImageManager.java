package web;


import compression.ZLibCompression;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {

    private static final int MAX_DIM = 120;

    /**
     * Fetch an image from a given url, and return the image in the form of a string converted from an byte[]
     * using the Base64 encoder
     * @param url
     * @return String
     * @throws IOException
     */
    public static String getImage(String url) throws IOException {
        String imageString;

        BufferedImage img = ImgReader.getImageFromURL(url);
        img = resizeImage(img);
        //writeBufferedImage(img,"bmp");
        byte[] imgArray = ImgReader.getImageArray(img,"jpg");
        imageString = ZLibCompression.encodeImage(imgArray);

        return imageString;
    }

    /**
     * Fetch an image from a given url, and return the image in the form of a list of string
     * the string has been encoded in Base64 and compressed using ZlibCompression class
     * @param url
     * @return ArrayList
     * @throws IOException
     */
    public static ArrayList<String> getImageStringInList(String url) throws IOException {
        BufferedImage img = ImgReader.getImageFromURL(url);
        img = resizeImage(img);
        byte[] imgArray = ImgReader.getImageArray(img,"jpg");
        String result = ZLibCompression.encodeImage(imgArray);

        return getArrayListFromString(result);
    }

    /**
     * Resize a given BufferedImage
     * @param original
     * @return BufferedImage
     */
    private static BufferedImage resizeImage(BufferedImage original){
        return Scalr.resize(original, MAX_DIM);
    }

    /**
     * Stores string into a list of 1000 characters
     * @param img
     * @return ArrayList<String>
     */
    static ArrayList<String> getArrayListFromString(String img){
        ArrayList<String> list = new ArrayList<>();
        int start = 0;
        int end = 1000;
        while (end < img.length()){
            list.add(img.substring(start,end));
            start=end;
            end+=1000;
        }
        if (start < img.length()){
            list.add(img.substring(start,img.length()));
        }
        return list;
    }

    /**
     * Write BufferedImage to a file
     * @param image
     * @param format
     */
    void writeBufferedImage(BufferedImage image,String format){
        try {
            ImageIO.write(image,format,new File("test.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
