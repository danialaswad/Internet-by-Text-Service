package compression;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PageCutterTest {

    @Test
    public void cutPageTest(){
        String html ="<body> \n" +
                "  <div>\n" +
                "    Hello \n" +
                "   <i> new </i> \n" +
                "   <p class=\"unecessary\" id=\"necessary\">this must <i>not</i> be remove</p> world \n" +
                "  </div> \n" +
                "  <p id=\"n\">this must not be remove</p> \n" +
                " </body>\n";
        PageCutter p = new PageCutter(html);
        String trimedPage = p.subHtmlTags(0,24);
        Assert.assertEquals("<body> \n" +
                "  <div>\n" +
                "    Hell",html.substring(0,24));

        String body = "<body> \n"+ " ";
        Assert.assertEquals(body, trimedPage);



        Assert.assertEquals("  <div>\n" +
                "    Hello \n" +
                "   <i> new </i> \n" +
                "   <p class=\"unecessary\" id=\"ne",html.substring(8,75) );
        trimedPage = p.subHtmlTags(8,75);
        String res = " <div>\n" +
                "   Hello \n" +
                "  <i> new </i> \n" + "  ";
        Assert.assertEquals(res, trimedPage);
    }

    @Test
    public void getChunkListTest(){
        String pageTest="";
        try {
            pageTest= new String(Files.readAllBytes(Paths.get("./src/test/res/tmpClean.html")),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PageCutter p = new PageCutter(pageTest);
        ArrayList<String> chunkList = p.getPageChunkList();
        for (String chunk:chunkList) {
            Assert.assertTrue(chunk.length() <= 1000);
        }

    }
}
