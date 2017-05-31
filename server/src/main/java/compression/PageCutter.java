package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.Stack;


/**
 * Created by Antoine on 30/05/2017.
 */
public class PageCutter {

    private Document page;
    private Element currentElement;
    private Stack<Element> elementStack;
    private int size;

    private final static int SIZE_CHUNK = 8880;

    public PageCutter(String pageString){
        this.page=  Jsoup.parse(pageString);
        currentElement =page.select("body").first();
        elementStack = new Stack<>();
        size=0;
    }

    /*
    Ini dans la pile mettre html,header,body
    Element = position de départ
    (i)Si en ajoutant le  prochain Element on ne depasse pas taille max
            on l'ajoute on passe au suivant
       Sinon
            Si cet élément à des éléments à l'intérieur
                On enregistre l'Element OU On enregistre le nom de la balise (pour fermer et pour le prochain paquet) dans une Stack
                mdofication taille max
                (i) sur les éléments à l'intérieur de celui-ci
            Sinon
                 On
            on enregistre nouvelle position de départ
            on entoure le contenu par ce qu'il y a en sommet de pile

     Algo simple :
      on regarde juste si le prochain de body prend pas trop de place pour l'ajouter sinon on ferme avec les balises body header et html et on envoie

     */
    public String nextPackage(){
        return write(currentElement);
    }

    private String write(Element element){
        elementStack.add(element);
        String result="";

        if(size+element.outerHtml().length()<=SIZE_CHUNK){
            /*Si en ajoutant le prochain element je ne dépasse pas la taille max*/
            elementStack.pop();
            size+=element.outerHtml().length();
            if (element.nextElementSibling()==null){
                return element.outerHtml();
            }else {
                return element.outerHtml() + write(element.nextElementSibling());
            }
        }else{
            /* je ne peux pas ajouter l'élément car trop gros*/
           if (!element.children().isEmpty()){
               /*Si cet élément à des éléments à l'intérieur*/

               //modification de la taille pour anticiper quand on devra refermer avec surroundedByTag
               size+=5+(element.tagName().length()*2); // pour anticiper <tagName> </tagName>
               if (!(element.attributes().size()==0))
                   size+=element.attributes().toString().length(); //j'ajoute la longueur de l'attribut
               //while (size <=SIZE_CHUNK) ??
               result = write(element.children().first()); //write les éléments à l'intérieur de celui-ci
           }

           //enregistre nouvelle position de départ
           currentElement=element;
            return surroundedByTag(elementStack.pop(),result);
        }
    }
    /**
     * Permet d'entourer htmlString avec la balise de l'élément tag
     * Respecte les attributs etc..
     * @param tag
     * @param htmlString
     * @return
     */
    public String surroundedByTag(Element tag,String htmlString){
        String openTag="<"+tag.tagName()+tag.attributes().toString()+">";
        String closeTag= "</"+tag.tagName()+">";
        return openTag+htmlString+closeTag;
    }
}
