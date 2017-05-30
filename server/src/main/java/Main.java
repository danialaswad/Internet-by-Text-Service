import web.URLReader;
import web.WebPageCleaner;

import java.io.IOException;

/**
 * https://blogserius.blogspot.fr
 * Created by danial on 29/05/2017.https://en.wikipedia.org/wiki/Vampire
 * http://www.nicematin.com
 */
public class Main {

    public static void main(String args[]) throws IOException {

        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");
        System.out.println(new WebPageCleaner().cleanWebPage(w.fetchFile()));
    }
}
