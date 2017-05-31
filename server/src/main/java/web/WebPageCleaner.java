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


public class WebPageCleaner {

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript, style";

    private static final String ACCEPTABLETAGS = "p, a, h1, h2, h3, h4, table, th, td";

    private static final List<String> UNECCESARYATTRIBUTES = Arrays.asList("id", "src", "href", "alt", "title", "height", "width");

    private String url;


    public String cleanWebPage(Document document, String url){
        this.url = url;
        removeUnecessaryTags(document);
        removeFooterTags(document);
        removeUnecessaryAttribute(document);
       return Jsoup.parse(document.body().toString()).toString();//Jsoup.parse(document.select(ACCEPTABLETAGS).toString()).toString();
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
                if (attribute.getKey().equals("href")){
                    completeURL(attribute);
                }
            }
            for (String s :keys){
                element.removeAttr(s);
            }
            keys.clear();
        }
    }

    void completeURL(Attribute attribute){
        String domain="";
        if (url.contains("http://")) {
            domain = url;
            domain = domain.replaceAll("http://", "");
        } else if (url.contains("https://")) {
            domain = url;
            domain = domain.replaceAll("https://", "");
        } else {
            domain = url;
            url = "https://" + url;
        }
        if (attribute.getValue().contains(url)){
            return;
        }
        if (attribute.getValue().contains(domain)){
            attribute.setValue(attribute.getValue().replace(domain,""));
            attribute.setValue(url + attribute.getValue());
            System.out.println("1 :"+ attribute.getValue());
        } else if (!attribute.getValue().contains(url)){
            attribute.setValue(url + attribute.getValue());
            System.out.println("2 :" +attribute.getValue());
        }
    }

}
