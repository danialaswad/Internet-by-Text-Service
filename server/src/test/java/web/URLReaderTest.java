package web;

import org.junit.Assert;
import org.junit.Test;


public class URLReaderTest {

    private String url = "http://www.nicematin.com";

    @Test
    public void existingWebsiteTest(){
        URLReader reader =  new URLReader(url);
        Assert.assertTrue(!reader.fetchFile().toString().equals(""));
    }

    @Test
    public void nonExistingWebsite(){
        URLReader reader = new URLReader("karim mediat");
        Assert.assertEquals(reader.error().toString(),reader.fetchFile().toString());
    }

    @Test
    public void completingURl(){
        URLReader reader = new URLReader("www.nicematin.com");
        Assert.assertEquals("https://www.nicematin.com", reader.getUrlString());
    }
}
