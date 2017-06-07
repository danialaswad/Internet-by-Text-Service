package database;

import twitter4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ITSDatabase {

    private Map<String, ArrayList<String>> webpages = new HashMap<>();

    private static ITSDatabase instance = new ITSDatabase();

    private Map<String,AccessToken>  twitterTokens = new HashMap<>();

    private Map<String, String> maxId = new HashMap<>();

    public ITSDatabase(){

    }

    public static ITSDatabase instance(){
        return instance;
    }


    public Map<String, ArrayList<String>> webpages(){
        return webpages;
    }

    public Map<String,AccessToken> twitterTokens(){
        return twitterTokens;
    }

    public Map<String, String> maxTweetId(){return maxId; }
}
