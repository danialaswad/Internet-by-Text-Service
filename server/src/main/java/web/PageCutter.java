package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.util.ArrayList;

/**
 * PageCutter class
 * @Author : ITS Team
 */

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
                break;
            }
        }

        result+=page.body().toString().substring(start,tmpInd);

        return result;
    }

    public ArrayList<String> getFirstChunk(){
        if (page.body().toString().length() < SIZE_CHUNK){
            chunkList.add(page.body().toString());
            return chunkList;
        }

        String tmp = subHtmlTags(0, SIZE_CHUNK);
        chunkList.add(tmp);

        chunkList.add(page.body().toString().substring(tmp.length(),page.body().toString().length()));

        return chunkList;
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
     * Pour découper une nouvelle page
     * @param pageString
     */
    public void resetCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList.clear();
    }

}
