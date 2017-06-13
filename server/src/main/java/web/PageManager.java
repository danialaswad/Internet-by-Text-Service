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

    public String getWebPage(String url, String originator){
        URLReader reader = new URLReader(url);
        Document document = reader.fetchFile();
        String page = new WebPageCleaner().cleanWebPage(document,reader.getUrlString());
        ArrayList<String> pages = new PageCutter(page).getFirstChunk();

        String result = pages.get(0);
        if(pages.size()>1) {
            try {
                ITSDatabaseSQL.addPages(originator, reader.getUrlString(), pages.get(1));
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
        return result;
 }

    public String nextWebPage(String url, String originator){
        URLReader reader = new URLReader(url);
        String result="";
        try {
            String page = ITSDatabaseSQL.getPage(originator,reader.getUrlString());
            ArrayList<String> pages = new PageCutter(page).getFirstChunk();
            result += pages.get(0);
            if (pages.size()>1){
                ITSDatabaseSQL.addPages(originator, reader.getUrlString(),pages.get(1));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        return result;
    }

    public void removeWebPage(String url,String originator) {
        try {
            ITSDatabaseSQL.removePages(originator,url);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
    }
}
