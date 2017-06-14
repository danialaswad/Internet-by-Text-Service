package web;

import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImgReaderTest {

    private static final String URL = "https://static.pexels.com/photos/24205/pexels-photo.jpg";
    @Test
    public void getImageTest() throws IOException {
        BufferedImage image = ImgReader.getImageFromURL(URL);

        Assert.assertTrue(!image.equals(null));
    }

    @Test
    public void getImageArrayTest() throws IOException {
        BufferedImage image = ImgReader.getImageFromURL(URL);

        byte[] images = ImgReader.getImageArray(image,"jpg");

        Assert.assertTrue(images.length != 0);
    }
}
