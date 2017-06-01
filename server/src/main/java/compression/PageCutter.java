package compression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.util.ArrayList;

public class PageCutter {

    private Document page;
    private ArrayList<String> chunkList;


    private final static int SIZE_CHUNK = 1000;

    public PageCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList=new ArrayList<>();
    }

    String subHtmlTags(int start, int end){
        String result="";

        int tmpInd = 0;
        for (int i = end; i >= start ; i--){
            if (page.body().toString().charAt(i) == '<'){
                tmpInd = i ;
                if (isATag(tmpInd))
                    break;
            }
        }

        result+=page.body().toString().substring(start,tmpInd);

        return result;
    }


    /**
     *
     * @return la liste des morceaux de la page
     */
    public ArrayList<String> getPageChunkList(){

        if (page.body().toString().length() < SIZE_CHUNK){
            chunkList.add(page.body().toString());
            return chunkList;
        }

        int start = 0;
        int end = SIZE_CHUNK;

        while (SIZE_CHUNK + start < page.body().toString().length()){
            String tmp = subHtmlTags(start, end);

            start += tmp.length();
            end = start+SIZE_CHUNK;
            chunkList.add(tmp);

        }

        if (start < page.body().toString().length() ){
            chunkList.add(page.body().toString().substring(start,page.body().toString().length()));
        }

        return chunkList;
    }

    /**
     * Spécifie si le caractère à la position pos est une partie constituante d'une balise
     * @param pos
     * @return
     */
    private boolean isATag(int pos){
        /*
        On a la position à tester qui correcpond à la position de "<" - 1
        on peut regarder si le mot qui suit directement c'est un p, a, h1, h2, h3, h4, table, th, td, i, tr, tbody, b, img, body, div, ul, li
        si oui on regarde si on a un > avant un <
         */


        //TODO
        return true;
    }

    /**
     * Pour découper une nouvelle page
     * @param pageString
     */
    public void resetCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList.clear();
    }

}
