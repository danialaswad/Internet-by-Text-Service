package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;


/**
 *  //Elements newsHeadlines = doc.select("#mp-itn b a");
 //doc.select("script, style, .hidden").remove().text();
 //return Jsoup.parse(doc.body().select("p").toString()).toString();
 ////return  new HtmlToPlainText().getPlainText(Jsoup.parse(doc.title() + doc.body().select("p")));
 */
public class WebPageCleaner {

    private Document document;

    private static final String UNECESSARYTAGS = "script, span, .hidden, noscript";

    public WebPageCleaner(Document document){
        this.document = document;
    }

    public String cleanWebPage(){
        removeUnecessaryTags();
        removeFooterTags();

        /*Whitelist wl = Whitelist.simpleText();
        wl.addTags("div", "span","p"); // add additional tags here as necessary
        String clean = Jsoup.clean(document.toString(), wl);*/

        //return Jsoup.parse(clean).toString();
       return document.select("p,a").html();
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
