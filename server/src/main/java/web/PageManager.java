package web;


import database.ITSDatabase;
import org.jsoup.nodes.Document;

/**
 * PageManager class
 * @Author : ITS Team
 */

public class PageManager {

    private ITSDatabase database = ITSDatabase.instance();

    public String getWebpage(String url){
        URLReader reader = new URLReader(url);
        Document document = reader.fetchFile();
        String page = new WebPageCleaner().cleanWebPage(document,reader.getUrlString());
        database.webpages().put(reader.getUrlString(),new PageCutter(page).getPageChunkList());
        return database.webpages().get(reader.getUrlString()).remove(0);
    }

    public String nexWebPage(String url){
        URLReader reader = new URLReader(url);

        if (database.webpages().containsKey(reader.getUrlString())){
            if (database.webpages().get(reader.getUrlString()).size() > 0){
                return database.webpages().get(reader.getUrlString()).remove(0);
            }
        }

        return "";
    }

    public void removeWebPage(String url) {
        database.webpages().remove(url);
    }
}
