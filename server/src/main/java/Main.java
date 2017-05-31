import org.jsoup.nodes.Document;
import web.URLReader;
import web.WebPageCleaner;

import java.io.IOException;

/**
 * https://blogserius.blogspot.fr
 *
 * http://www.nicematin.com
 * http://www.apple.com
 */
public class Main {

    public static void main(String args[]) throws IOException {
        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");
        //new WebPageCleaner().cleanWebPage(w.fetchFile());
        //Document document = w.fetchFile();
        System.out.println(new WebPageCleaner().cleanWebPage(w.fetchFile(),w.getUrlString()));
    }
}
