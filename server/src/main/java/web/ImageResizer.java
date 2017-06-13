package web;


import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

public class ImageResizer {

    private static final int MAX_DIM = 100;

    public static BufferedImage resizeImage(BufferedImage original){
        return Scalr.resize(original, MAX_DIM);
    }
}
