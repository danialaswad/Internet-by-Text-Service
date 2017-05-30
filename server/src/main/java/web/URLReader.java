package web;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class URLReader {

    private String urlString;
    private Document document;

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
            this.document = Jsoup.connect(urlString).get();
            return this.document.toString();
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
