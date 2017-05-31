import compression.PageCutter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import web.URLReader;
import web.WebPageCleaner;

/**
 * Created by Antoine on 30/05/2017.
 */
public class MainPerso {
    public static void main(String[] args) {
        URLReader w = new URLReader("https://en.wikipedia.org/wiki/Vampire");
        String str = new WebPageCleaner().cleanWebPage(w.fetchFile());
        PageCutter cutter = new PageCutter(str);
        //System.out.println(str);
        //TAGNAME = body , html img nom de la balise quoi
        Document doc = Jsoup.parse(str);
        Element bodyElmement=doc.select("body").first();
        /*Elements bodyElements = doc.getElementsByTag("body");
        for (Element e : bodyElements){
            System.out.println( "-------"+e.toString());
        }*/
        System.out.println("-------Body:"+bodyElmement.toString());
        //Element a = doc.select("a").first();
        Element a = bodyElmement.children().first().nextElementSibling();
        System.out.println("------classname:"+a.className());
        System.out.println("------tagname:"+a.tagName());
        System.out.println("------Ce qu'il y a à l'intérieur plus les balises:"+a.outerHtml());
        System.out.println("------toString:"+a.toString()); //comme outer mais sur tout ceux qui ont le même tagname je crois priviligié outer
        System.out.println("------selector:"+a.cssSelector()); //utile => doc.select( ceci) selectionne cet élément
        System.out.println("------Ce qu'il y a à l'intérieur:"+a.html()); //ce qu'il y a à l'intérieur
        System.out.println("------les attributs:"+a.attributes().toString());
        System.out.println("------testSurrounded:"+cutter.surroundedByTag(a,a.outerHtml()));
    }
}
