package web;


import database.ITSDatabaseSQL;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * PageManager class
 * @Author : ITS Team
 */

public class PageManager {

    private final static Logger LOG = org.apache.log4j.Logger.getLogger(PageManager.class);

    public String getWebpage(String url,String originator){
        URLReader reader = new URLReader(url);
        Document document = reader.fetchFile();
        String page = new WebPageCleaner().cleanWebPage(document,reader.getUrlString());
        ArrayList<String> pages = new PageCutter(page).getPageChunkList();

        String result = pages.remove(0);
        try {
            ITSDatabaseSQL.addPages(originator, reader.getUrlString(),pages);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return result;
        //database.webpages().put(reader.getUrlString(),new PageCutter(page).getPageChunkList());
        //return database.webpages().get(reader.getUrlString()).remove(0);
    }

    public String nexWebPage(String url,String originator){
        URLReader reader = new URLReader(url);
        String result="";
        try {
            ArrayList<String> pages = ITSDatabaseSQL.getPage(originator,reader.getUrlString());
            if (pages.size() > 0) {
                result = pages.remove(0);
                ITSDatabaseSQL.addPages(originator,reader.getUrlString(),pages);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        return result;
       /* if (database.webpages().containsKey(reader.getUrlString())){
            if (database.webpages().get(reader.getUrlString()).size() > 0){
                return database.webpages().get(reader.getUrlString()).remove(0);
            }
        }

        return "";*/
    }

    public void removeWebPage(String url,String originator) {
        try {
            ITSDatabaseSQL.removePages(originator,url);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        //database.webpages().remove(url);
    }
}
