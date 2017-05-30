package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebPageCleaner {

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript, style";

    private static final String ACCEPTABLETAGS = "p, a, h1, h2, h3, h4";

    public String cleanWebPage(Document document){
        removeUnecessaryTags(document);
        removeFooterTags(document);
        removeUnecessaryAttribute(document);
       return Jsoup.parse(document.select(ACCEPTABLETAGS).toString()).toString();
    }

    private void removeUnecessaryTags(Document document){
        document.select(UNECESSARYTAGS).remove().text();
    }

    private void removeFooterTags(Document document){
        Elements divs = document.select("div[id^=footer]");
        for (Element e : divs) {
            if (document.getElementById(e.id())!=null) {
                document.getElementById(e.id()).remove();
            }
        }
    }

    private void removeUnecessaryAttribute(Document document){
        Elements elements = document.getAllElements();
        for (Element element : elements){
            element.removeAttr("class");
        }
    }

}
