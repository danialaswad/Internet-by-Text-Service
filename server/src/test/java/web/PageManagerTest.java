package web;

import org.junit.Assert;
import org.junit.Test;

/**
 * PageManagerTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class PageManagerTest {

    @Test
    public void getWebPageTest(){
        PageManager pm = new PageManager();
        String expected = "<body> \n" +
                " <h1>HTML Ipsum Presents</h1> \n" +
                " <p> senectus et netus et malesuada fames ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante. Donec eu libero sit amet quam egestas semper. Mauris placerat eleifend leo. Quisque sit amet est et sapien ullamcorper pharetra. Vestibulum erat wisi, condimentum sed, , ornare sit amet, wisi. Aenean fermentum, elit eget tincidunt condimentum, eros ipsum rutrum orci, sagittis tempus lacus enim ac dui. <a href=\"http://anasghira.com/lorem2\" title=\"Dracula (1958 film)\">Dracula</a> in turpis pulvinar facilisis. Ut felis.</p> \n" +
                " <h2>Header Level 2</h2>  \n" +
                "</body>";
        String actual = pm.getWebPage("http://anasghira.com/its/lorem","");

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void nextWebPageTest(){
        PageManager pm = new PageManager();
        pm.getWebPage("http://anasghira.com/its/lorem2","");
        String expected = "<p>Vestibulum tincidunt orci in neque efficitur tempus. Nulla vulputate vulputate ultrices. Maecenas pellentesque, metus at malesuada pulvinar, quam nibh fermentum lacus, et dapibus nibh felis eu odio. Nulla eget velit id dolor egestas volutpat. Pellentesque rutrum tellus ac dui tincidunt pulvinar. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris ut magna nunc. Aenean sollicitudin ut quam sed condimentum. Phasellus pulvinar ultricies lacus, ut vehicula eros tincidunt quis. Curabitur id nibh posuere, molestie mi commodo, pharetra arcu. Proin sed urna semper, euismod.</p>  \n" +
                "</body>";
        String actual = pm.nextWebPage("http://anasghira.com/its/lorem2","");

        Assert.assertEquals(expected,actual);
    }
}
