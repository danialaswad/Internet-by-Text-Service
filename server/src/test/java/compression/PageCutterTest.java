package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Antoine on 31/05/2017.
 */
public class PageCutterTest {
    protected PageCutter cutter;
    protected Document doc;

    @Before
    public void setUp() {
        byte[] encoded = new byte[0];

        String pageTest="";
        try {
            pageTest= new String(Files.readAllBytes(Paths.get("./src/test/res/tmpClean.html")),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        cutter = new PageCutter(pageTest);
        doc = Jsoup.parse(pageTest);
    }

    @Test
    public void surroundedByTagTest(){
        Element bodyElmement=doc.select("body").first();
        Element a = bodyElmement.children().first().nextElementSibling();
        Assert.assertEquals(cutter.surroundedByTag(a,a.outerHtml()),"<a href=\"/wiki/Wikipedia:Featured_articles\" title=\"This is a featured article. Click here for more information.\"><a href=\"/wiki/Wikipedia:Featured_articles\" title=\"This is a featured article. Click here for more information.\"><img alt=\"This is a featured article. Click here for more information.\" src=\"//upload.wikimedia.org/wikipedia/en/thumb/e/e7/Cscr-featured.svg/20px-Cscr-featured.svg.png\" width=\"20\" height=\"19\" srcset=\"//upload.wikimedia.org/wikipedia/en/thumb/e/e7/Cscr-featured.svg/30px-Cscr-featured.svg.png 1.5x, //upload.wikimedia.org/wikipedia/en/thumb/e/e7/Cscr-featured.svg/40px-Cscr-featured.svg.png 2x\" data-file-width=\"462\" data-file-height=\"438\"></a></a>");
        Assert.assertEquals(cutter.surroundedByTag(bodyElmement,"<body></body>"),"<body><body></body></body>");
    }
}
