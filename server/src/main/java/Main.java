import web.URLReader;
import web.WebPageCleaner;

/**https://blogserius.blogspot.fr
 * https://en.wikipedia.org/wiki/Vampire
 *http://www.nicematin.com
 */
public class Main {

    public static void main(String args[]){

        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");

        System.out.println(new WebPageCleaner().cleanWebPage(w.fetchFile()));
    }
}
