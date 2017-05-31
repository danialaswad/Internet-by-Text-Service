package web;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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


    public Document fetchFile() {
        try {
            return Jsoup.connect(urlString).get();
        } catch (IOException e) {
            System.err.println(e.getMessage() + " is unreachable");
        }

        return error();
    }

    Document error(){
        Document doc = Jsoup.parse("<html><head><title>Error</title></head>\"\n" +
                "  + \"<body><p>" + getUrlString() + " is not available</p></body></html>");
        return doc;
    }

    public String getUrlString() {
        return urlString;
    }

    void setUrlString(String urlString) {
        this.urlString = urlString;
    }

}
