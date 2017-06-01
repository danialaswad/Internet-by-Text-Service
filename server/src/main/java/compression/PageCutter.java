package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
/**
 * Created by Antoine on 30/05/2017.
 */
public class PageCutter {

    private Document page;
    private int currentSize,posIni;
    private ArrayList<String> chunkList;


    private final static int SIZE_CHUNK = 9000;

    public PageCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList=new ArrayList<String>();
        currentSize=0;
        posIni=0;
    }


    public String nextPackage(){
        String result="";
        //On veut le prochain élément trop gros à rajouter
        Element tooBig=nextOverflowElement();
        //on coupe jusqu'à lui
        result=cutUntilElement()
        //on ajoute ce que l'on peut

        result=page.outerHtml().substring(posIni,posIni+currentSize);
        posIni=posIni+currentSize+1;
        currentSize=0;
        return result;
    }

    public Element nextOverflowElement(){
        return null;
    }

    public String cutUntilElement(Element e){
        return null;
    }

    public String add

    public ArrayList<String> getChunkList(){
        return chunkList;
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

    public void setPage(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList.clear();
    }

}
