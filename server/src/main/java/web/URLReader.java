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
            if (!urlString.startsWith("http://")) {
                this.urlString = "http://" + this.urlString;
            }
        }
    }


    public Document fetchFile() {
        if(urlString.equals("http://"))
            return error();
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
