package web;

import org.junit.Test;

import java.io.IOException;


public class ImageManagerTest {


    @Test
    public void imageFetcherTest() throws IOException {
        ImageManager imgManager = new ImageManager();

        String imgString = imgManager.getImage("https://www.wikipedia.org/portal/wikipedia.org/assets/img/Wikipedia-logo-v2.png");

        System.out.println(imgString.length());

        imgString = imgManager.getImage("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Mangalarga_Marchador_Conformação.jpg/290px-Mangalarga_Marchador_Conformação.jpg");

        System.out.println(imgString.length());
    }
}
