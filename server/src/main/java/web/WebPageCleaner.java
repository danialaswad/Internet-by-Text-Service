package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class WebPageCleaner {

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript, style";

    private static final String ACCEPTABLETAGS = "p, a, h1, h2, h3, h4, table, th, td";

    private static final List<String> UNECCESARYATTRIBUTES = Arrays.asList("id", "src", "href", "alt", "title", "height", "width");

    public String cleanWebPage(Document document){
        removeUnecessaryTags(document);
        removeFooterTags(document);
        removeUnecessaryAttribute(document);
       return Jsoup.parse(document.select(ACCEPTABLETAGS).toString()).toString();
    }

    void removeUnecessaryTags(Document document){
        document.select(UNECESSARYTAGS).remove().text();
    }

    void removeFooterTags(Document document){
        Elements divs = document.select("div[id^=footer]");
        for (Element e : divs) {
            if (document.getElementById(e.id())!=null) {
                document.getElementById(e.id()).remove();
            }
        }
    }

    void removeUnecessaryAttribute(Document document){
        Elements elements = document.getAllElements();
        List<String> keys = new ArrayList<>();
        for (Element element : elements){
            Attributes attributes = element.attributes();
            for (Attribute attribute : attributes){
                if (!UNECCESARYATTRIBUTES.contains(attribute.getKey())){
                    keys.add(attribute.getKey());
                }
            }
            for (String s :keys){
                element.removeAttr(s);
            }
            keys.clear();
        }
    }

}
