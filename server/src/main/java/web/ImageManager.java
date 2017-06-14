package web;


import compression.ZLibCompression;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {

    private static final int MAX_DIM = 100;

    public String getImage(String url) throws IOException {
        String imageString;

        BufferedImage img = ImgReader.getImageFromURL(url);
        img = resizeImage(img);
        byte[] imgArray = ImgReader.getImageArray(img);
        imageString = ZLibCompression.encodeImage(imgArray);

        return imageString;
    }


    private static BufferedImage resizeImage(BufferedImage original){
        return Scalr.resize(original, MAX_DIM);
    }
}
