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
        String str = new WebPageCleaner().cleanWebPage(w.fetchFile(),w.getUrlString());
        PageCutter cutter = new PageCutter("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>HTML</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body bgcolor=\"#FFFFE8\" topmargin=\"5\">\n" +
                "\n" +
                "    <!--\n" +
                "    Ceci est un commentaires ;-)\n" +
                "    Visible que via la source !\n" +
                "    -->\n" +
                "\n" +
                "\n" +
                "    <!-- Début Top Menu -->\n" +
                "    <a name=\"top\"></a>\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"771\" align=\"center\">\n" +
                "      <tr>\n" +
                "        <td width=\"200\" align=\"left\">\n" +
                "          <a href=\"http://www.softastuces.com\" title=\"Accueil de SoftAstuces\"><img src=\"/images/tuto/base_site/logo.gif\" width=\"200\" height=\"60\" border=\"0\" alt=\"SoftAstuces.com\"></a>\n" +
                "        </td>\n" +
                "        <td width=571 valign=\"middle\">\n" +
                "          <a href=\"/astu/index.php\"><font color=\"#400040\" size=\"+1\">Les Astuces</font></a> | \n" +
                "          <a href=\"/tuto/index.php\"><font color=\"#860411\" size=\"+1\">Les Tutoriaux</font></a> | \n" +
                "          <a href=\"javascript:;\"><font color=\"#0000A0\" size=\"+1\">Le Forum</font></a> | \n" +
                "          <a href=\"/download/index.php\"><font color=\"#808080\" size=\"+1\">Zone Download</font></a>\n" +
                "          \n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "    <!-- Fin Top Menu -->\n" +
                "    \n" +
                "    <!-- \n" +
                "    Break...\n" +
                "    -->\n" +
                "    \n" +
                "    <!-- Début Main Table -->\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"771\" align=\"center\">\n" +
                "      \n" +
                "      <!-- Début Ligne du Haut -->\n" +
                "      <tr>\n" +
                "        <td colspan=\"3\"><img src=\"/images/divers/pixels/pixeln.gif\" width=\"100%\" height=\"1\" alt=\"\"></td>\n" +
                "      </tr>\n" +
                "      <!-- Fin Ligne du Haut -->\n" +
                "      \n" +
                "      <tr>\n" +
                "        <!-- Début Menu de Gauche -->\n" +
                "        <td width=\"161\" align=\"right\">\n" +
                "          <p>\n" +
                "            <font color=\"#000000\"><span class=\"important\">Les Astuces</span></font><br />\n" +
                "            <a href=\"/astu/internet/index.php\" style=\"color:#008000; text-decoration:none\">Internet</a><br />\n" +
                "            <a href=\"/astu/messa/index.php\" style=\"color:#008000; text-decoration:none\">Messagerie</a><br />\n" +
                "            <a href=\"/astu/office/index.php\" style=\"color:#008000; text-decoration:none\">Office</a><br />\n" +
                "            <a href=\"/astu/secu/index.php\" style=\"color:#008000; text-decoration:none\">Sécurité</a><br />\n" +
                "            <a href=\"/astu/win/index.php\" style=\"color:#008000; text-decoration:none\">Windows</a>\n" +
                "          </p>\n" +
                "          \n" +
                "          <p>\n" +
                "            <font color=\"#000000\"><span class=\"important\">Les Tutoriaux</span></font><br />\n" +
                "            <a href=\"/tuto/compression/index.php\" style=\"color:#008000; text-decoration:none\">Compression</a><br />\n" +
                "            <a href=\"/tuto/site/index.php\" style=\"color:#008000; text-decoration:none\">Créer un Site</a><br />\n" +
                "            <a href=\"/tuto/maint/index.php\" style=\"color:#008000; text-decoration:none\">Maintenance</a><br />\n" +
                "            <a href=\"/tuto/messa/index.php\" style=\"color:#008000; text-decoration:none\">Messagerie</a><br />\n" +
                "            <a href=\"/tuto/mp3/index.php\" style=\"color:#008000; text-decoration:none\">MP3</a><br />\n" +
                "            <a href=\"/tuto/secu/index.php\" style=\"color:#008000; text-decoration:none\">Sécurité</a><br />\n" +
                "            <a href=\"/tuto/tweak/index.php\" style=\"color:#008000; text-decoration:none\">Tweakage</a>\n" +
                "          </p>\n" +
                "        </td>\n" +
                "        <!-- Fin Menu de Gauche -->\n" +
                "        \n" +
                "        <!-- Début Séparatiion Menu/Body -->\n" +
                "        <td width=\"10\" align=\"center\"><img src=\"/images/divers/pixels/pixeln.gif\" width=\"1\" height=\"100%\" alt=\"\"></td>\n" +
                "        <!-- Fin Séparatiion Menu/Body -->\n" +
                "        \n" +
                "        <!-- Début Main Body -->\n" +
                "        <td width=\"600\" valign=\"top\">\n" +
                "          <h1 align=\"center\">HTML !</h1>\n" +
                "                \n" +
                "          <!-- Début Mini Tableau ;-) -->\n" +
                "          <table border=\"0\" cellpadding=\"3\" cellspacing=\"1\" bgcolor=\"#CCCC00\">\n" +
                "            <tr>\n" +
                "              <td bgcolor=\"#FFFFF9\">\n" +
                "                <font color=\"arial\" face=\"#400040\">\n" +
                "                  <h2>(Balise Hx...)</h2>\n" +
                "                  Voila une page HTML si vous voulez l'analyser...\n" +
                "                  <br /><br />\n" +
                "                  Amusez-vous bien ;-)\n" +
                "                </font>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>  \n" +
                "          <!-- Fin Mini Tableau -->\n" +
                "          \n" +
                "          \n" +
                "          <br /><br /><br />\n" +
                "          <div align=\"right\">\n" +
                "            <a href=\"#top\" onclick=\"alert('Recherchez <a name=\\'top\\'></a>');\" title=\"Retourner en Haut\"><i>Top...</i></a>\n" +
                "          </div>\n" +
                "          \n" +
                "        </td>\n" +
                "        <!-- Fin Main Body -->\n" +
                "      </tr>\n" +
                "      \n" +
                "      <!-- Début Ligne du Bas -->\n" +
                "      <tr>\n" +
                "        <td colspan=\"3\"><img src=\"/images/divers/pixels/pixeln.gif\" width=\"100%\" height=\"1\" alt=\"\"></td>\n" +
                "      </tr>\n" +
                "      <!-- Fin Ligne du Bas -->\n" +
                "      \n" +
                "    </table>\n" +
                "    <!-- Fin Main Table -->\n" +
                "\n" +
                "  </body>\n" +
                "</html>");
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
        if (bodyElmement.nextElementSibling()==null){
            System.out.println("body hasn't next sibling");
        }
        System.out.println(a.children().first().children().toString()+"tuz");
        if (a.children().first().children().isEmpty()){
            System.out.println("img n'a pas d'enfant");
        }

        if (bodyElmement.attributes().size()==0){
            System.out.println("body n'a pas d'attribut");
        }

        System.out.println(cutter.getChunkList().size());
    }
}
