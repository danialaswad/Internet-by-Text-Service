package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebPageCleaner class
 *
 * @Author : ITS Team
 */

public class WebPageCleaner {

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript, style";

    private static final String ACCEPTABLETAGS = "p, a, h1, h2, h3, h4, table, th, td, i, tr, tbody, b, img, body, div, ul, li, section, em, strong, mark, del, ins, sub, sup";

    private static final List<String> UNECCESARYATTRIBUTES = Arrays.asList("id", "src", "href", "alt", "title", "height", "width");

    private String url;


    /**
     * Clean an html file
     * Remove unecessary information
     * @param document
     * @param url
     * @return
     */
    public String cleanWebPage(Document document, String url){
        this.url = url;
        removeUnecessaryTags(document);
        removeFooterTags(document);
        removeUnecessaryAttribute(document);
        removeComments(document);
       return Jsoup.parse(document.body().toString()).toString();
       //Jsoup.parse(document.select(ACCEPTABLETAGS).toString()).toString();
    }

    /**
     * Remove comments from an html file
     * @param node
     */
    void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }

    /**
     * Remove all uneccesary tags from the Document
     * @param document
     */
    void removeUnecessaryTags(Document document){
        List<String> arrayList = new ArrayList<>();
        Elements elements = document.body().select(ACCEPTABLETAGS);

        for (Element element : document.body().getAllElements()){
            if (!elements.contains(element) && !arrayList.contains(element.nodeName())) {
                    arrayList.add(element.nodeName());
            }
        }

        String removeTags = arrayList.toString().replace("]","").replace("[","").trim();
        if (!removeTags.isEmpty())
            document.select(removeTags).remove().text();
    }

    /**
     * Remove tags with id starting with footer
     * @param document
     */
    void removeFooterTags(Document document){
        Elements divs = document.select("div[id^=footer]");
        for (Element e : divs) {
            if (document.getElementById(e.id())!=null) {
                document.getElementById(e.id()).remove();
            }
        }
    }

    /**
     * Remove uneccessary attributes from a the Document
     * @param document
     */
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
                if (attribute.getKey().equals("src")){
                    completeURL(attribute);
                }
            }
            for (String s :keys){
                element.removeAttr(s);
            }
            keys.clear();
        }
    }

    /**
     * Complete the static url found in an Attribute
     * @param attribute
     */
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
        if (attribute.getValue().contains(url) || attribute.getValue().startsWith("http")
                || attribute.getValue().startsWith("//")){
            return;
        }
        if (attribute.getValue().contains(domain)){
            attribute.setValue(attribute.getValue().replace(domain,""));
            attribute.setValue(url + attribute.getValue());
        } else if (!attribute.getValue().contains(url)){
            attribute.setValue(url + attribute.getValue());
        }
    }
}
