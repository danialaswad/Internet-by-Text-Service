package web;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class URLReader {


    private String urlString;

    public URLReader(String urlString){
        this.urlString = urlString;
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(this.urlString)) {
            if (!urlString.startsWith("https://")) {
                this.urlString = "https://" + this.urlString;
            }
        }
    }


    public String fetchFile() {

        try {

            Document doc = Jsoup.connect(urlString).get();
            Elements newsHeadlines = doc.select("#mp-itn b a");
            doc.select("script, style, .hidden").remove().text();
            return  new HtmlToPlainText().getPlainText(Jsoup.parse(doc.title() + doc.body().select("p")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return error();
    }

    private String error(){
        return getUrlString() + " not available";
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }
}
