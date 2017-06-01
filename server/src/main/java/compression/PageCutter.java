package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
//juste pac couper entre deux "<>"
//recuperer tout jusqu 'au debut de la balise + la balise : doc.outerHtml.substring(0,mabalise.outterHtml.length)
/**
 * Created by Antoine on 30/05/2017.
 */
public class PageCutter {

    private Document page;
    private int currentSize;
    private ArrayList<String> chunkList;


    private final static int SIZE_CHUNK = 9000;

    public PageCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList=new ArrayList<String>();
    }


    private String nextPackage(){
        return null;
    }

    public String cut(){
        return null;
    }

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
