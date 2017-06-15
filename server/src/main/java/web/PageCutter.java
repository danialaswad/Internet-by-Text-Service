package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.util.ArrayList;

/**
 * PageCutter class
 *
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

    /**
     * Retrieve the 1st 1000 character from an html text
     * @param start
     * @param end
     * @return String
     */
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

    /**
     * Retrieve the 1st 1000 character in a given string
     * Returns an arraylist that contains the 1st 1000 character in the and the rest
     * @return Arraylist
     */
    ArrayList<String> getFirstChunk(){
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
     * Pour découper une nouvelle page
     * @param pageString
     */
    public void resetCutter(String pageString){
        page=  Jsoup.parse(pageString);
        chunkList.clear();
    }

}
