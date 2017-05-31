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

    private final static int SIZE_CHUNK = 8880;

    public PageCutter(String pageString){
        this.page=  Jsoup.parse(pageString);
        Element currentElement =page.select("body").first();
        elementStack = new Stack<>();
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

        /*
         - on supprime que si on est dans un body
         - pas couper mot en deux
         - vérifier qu'on a pas dépassé la fin du texte
         */
        return null;
    }

    /**
     * Permet d'entourer htmlString avec la balise de l'élément tag
     * Respecte les attributs etc..
     * @param tag
     * @param htmlString
     * @return
     */
    private String surroundedByTag(Element tag,String htmlString){
        String openTag="<"+tag.tagName()+" "+tag.attributes().toString()+">";
        String closeTag= "<"+tag.tagName()+">";
        return null;
    }
}
