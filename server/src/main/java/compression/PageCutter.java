package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Created by Antoine on 30/05/2017.
 */
public class PageCutter {

    private Document page;
    private int posIniNext;

    private final static int SIZE_CHUNK = 8880;

    public PageCutter(String pageString){
        this.page=  Jsoup.parse(pageString);
        posIniNext = 0;
    }


    public String nextPackage(){
        int posFinNext = posIniNext;
        int tmpPosFinNext = posFinNext;
        /*
        while (posFinNext - posIniNext < SIZE_CHUNK ){}
         */
        return page.toString().substring(posIniNext,posFinNext);
    }
}
