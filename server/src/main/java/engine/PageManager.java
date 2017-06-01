package engine;


import compression.PageCutter;
import database.ITSDatabase;
import org.jsoup.nodes.Document;
import web.URLReader;
import web.WebPageCleaner;

import java.util.ArrayList;
import java.util.List;


public class PageManager {

    private ITSDatabase database = ITSDatabase.instance();

    public String getWebpage(String url){
        URLReader reader = new URLReader(url);
        if (database.webpages().keySet().contains(reader.getUrlString())){
            return database.webpages().get(reader.getUrlString()).get(0);
        }else {
            Document document = reader.fetchFile();
            String page = new WebPageCleaner().cleanWebPage(document,reader.getUrlString());
            ArrayList<String> packages = new PageCutter(page).getPageChunkList();
            database.webpages().put(reader.getUrlString(),packages);
            return packages.get(0);
        }
    }

    public String nexWebPage(String url, int page){
        //TODO
        return "";
    }
}
