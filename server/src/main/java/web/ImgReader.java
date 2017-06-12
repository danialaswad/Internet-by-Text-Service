package web;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * ImgReader
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class ImgReader {

    public static Image getImageFromURL(String imgURL) throws IOException {
        URL url = new URL(imgURL);
        return ImageIO.read(url);
    }


}
