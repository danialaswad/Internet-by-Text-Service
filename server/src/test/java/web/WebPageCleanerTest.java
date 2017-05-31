package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;


public class WebPageCleanerTest {


    @Test
    public void removeFooterTest(){
        Document document = Jsoup.parse("<div id=\"footer-main\">This is a test</div>");
        new WebPageCleaner().removeFooterTags(document);
        Assert.assertEquals(Jsoup.parse("").toString(), document.toString());
    }

    @Test
    public void tagsNotRemove(){
        Document document = Jsoup.parse("<p>this must not be remove</p>");
        new WebPageCleaner().removeFooterTags(document);
        Assert.assertEquals(Jsoup.parse("<p>this must not be remove</p>").toString(), document.toString());
    }

    @Test
    public void removeCertainFooterTags(){
        Document document = Jsoup.parse("<div id=\"head\">this must not be remove</div><div id=\"footer-main\">This is a <p>this must not be remove</p> test</div>");
        new WebPageCleaner().removeFooterTags(document);
        Assert.assertEquals(Jsoup.parse("<div id=\"head\">this must not be remove</div>").toString(), document.toString());
    }

    @Test
    public void removeUnecessaryTest(){
        Document document = Jsoup.parse("<p>this must not be remove</p>");
        new WebPageCleaner().removeFooterTags(document);
        Assert.assertEquals(Jsoup.parse("<p>this must not be remove</p>").toString(), document.toString());
    }
}
