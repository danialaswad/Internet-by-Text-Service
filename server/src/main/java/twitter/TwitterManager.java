package twitter;

import database.ITSDatabase;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.List;

/**
 * Created by danial on 06/06/2017.
 */
public class TwitterManager {

    private static final String APIKEY = "pIi6kVnGy12GFjECWELC8R4VK";
    private static final String APISECRET = "WsCcYqlpJJ15DaRsKd7AnviQUEEn9Rd1PugDt6uFcdUvCz2Fxa";

    private ITSDatabase database = ITSDatabase.instance();

    private Twitter twitter;

    public TwitterManager(){
        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(APIKEY,APISECRET);
    }

    public void configureAccount(String token, String tokenSecret, String id){
        AccessToken accessToken = new AccessToken(token, tokenSecret,Long.parseLong(id));
        database.twitterTokens().put(id,accessToken);
    }

    public String getHomeTimeline(String id) throws TwitterException {

        AccessToken accessToken = database.twitterTokens().get(id);
        twitter.setOAuthAccessToken(accessToken);
        String result = "";
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
                result += status.getUser().getName() + ":" +status.getText();
            }
        twitter.setOAuthAccessToken(null);
        return result;
    }

    public boolean postTweet(String id, String tweet){
        Status status = null;
        AccessToken accessToken = database.twitterTokens().get(id);
        twitter.setOAuthAccessToken(accessToken);
        try {
            status = twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
        return true;
    }


}
