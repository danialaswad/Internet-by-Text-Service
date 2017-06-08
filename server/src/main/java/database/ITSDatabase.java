package database;

import twitter4j.auth.AccessToken;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ITSDatabase implements Serializable{

    private  static  final  long serialVersionUID =  1350092881346723535L;

    private Map<String, ArrayList<String>> webpages = new HashMap<>();

    private static ITSDatabase instance = new ITSDatabase();

    private Map<String,AccessToken>  twitterTokens = new HashMap<>();

    private Map<String, String> maxId = new HashMap<>();

    public ITSDatabase(){

    }

    public static ITSDatabase instance(){
        return instance;
    }

    public static void setInstance(ITSDatabase instance) {
        ITSDatabase.instance = instance;
    }


    public Map<String, ArrayList<String>> webpages(){
        return webpages;
    }

    public Map<String,AccessToken> twitterTokens(){
        return twitterTokens;
    }

    public Map<String, String> maxTweetId(){return maxId; }


}
