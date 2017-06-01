package compression;

import org.junit.Assert;
import org.junit.Test;

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
        System.out.println(trimedPage);
        String res = " <div>\n" +
                "   Hello \n" +
                "  <i> new </i> \n" + "  ";
        Assert.assertEquals(res, trimedPage);
    }
}
