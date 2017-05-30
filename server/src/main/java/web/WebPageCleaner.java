package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *  //Elements newsHeadlines = doc.select("#mp-itn b a");
 //doc.select("script, style, .hidden").remove().text();
 //return Jsoup.parse(doc.body().select("p").toString()).toString();
 ////return  new HtmlToPlainText().getPlainText(Jsoup.parse(doc.title() + doc.body().select("p")));
 */
public class WebPageCleaner {

    private Document document;

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript, style";

    private static final String ACCEPTABLETAGS = "p, a, h1, h2, h3";

    public WebPageCleaner(Document document){
        this.document = document;
    }

    public String cleanWebPage(){
        removeUnecessaryTags();
        removeFooterTags();
        removeUnecessaryAttribute();
       return Jsoup.parse(document.select(ACCEPTABLETAGS).toString()).toString();
    }

    private void removeUnecessaryTags(){
        this.document.select(UNECESSARYTAGS).remove().text();
    }

    private void removeFooterTags(){
        Elements divs = document.select("div[id^=footer]");
        for (Element e : divs){
            document.getElementById(e.id()).remove();
        }
    }

    private void removeUnecessaryAttribute(){
        Elements elements = document.getAllElements();
        for (Element element : elements){
            element.removeAttr("class");
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
