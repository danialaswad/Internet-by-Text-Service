import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebSite {

    private String urlString;

    public WebSite(String urlString){
       this.urlString = urlString;
    }

    private String error(){
        return "Website not available";
    }

    public String getWebSite() {

        try {
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();

            String encoding = con.getContentEncoding();  // ** WRONG: should use "con.getContentType()" instead but it returns something like "text/html; charset=UTF-8" so this value must be parsed to extract the actual encoding
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(in, encoding);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return error();
    }
}
