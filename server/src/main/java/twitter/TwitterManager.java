package twitter;

import database.ITSDatabase;
import twitter4j.auth.AccessToken;

/**
 * Created by danial on 06/06/2017.
 */
public class TwitterManager {

    private ITSDatabase database = ITSDatabase.instance();


    public void configureAccount(String token, String tokenSecret, String id){
        AccessToken accessToken = new AccessToken(token, tokenSecret,Long.parseLong(id));
        database.twitterTokens().put(id,accessToken);
    }


}
