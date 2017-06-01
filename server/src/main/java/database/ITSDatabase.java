package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ITSDatabase {

    private Map<String, ArrayList<String>> webpages = new HashMap<>();

    private static ITSDatabase instance = new ITSDatabase();

    public ITSDatabase(){

    }

    public static ITSDatabase instance(){
        return instance;
    }


    public Map<String, ArrayList<String>> webpages(){
        return webpages;
    }
}
