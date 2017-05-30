import web.URLReader;
import web.WebPageCleaner;

/**https://blogserius.blogspot.fr
 * Created by danial on 29/05/2017.https://en.wikipedia.org/wiki/Vampire
 *http://www.nicematin.com
 */
public class Main {

    public static void main(String args[]){
        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");

        WebPageCleaner cleaner = new WebPageCleaner(w.fetchFile());

        System.out.println(cleaner.cleanWebPage());
    }
}
