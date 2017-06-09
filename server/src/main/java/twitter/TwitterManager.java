package twitter;

import database.ITSDatabase;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import java.util.List;

/**
 * TwitterManager class
 * @Author : ITS Team
 */


public class TwitterManager {

    private static final String APIKEY = "pIi6kVnGy12GFjECWELC8R4VK";
    private static final String APISECRET = "WsCcYqlpJJ15DaRsKd7AnviQUEEn9Rd1PugDt6uFcdUvCz2Fxa";


    private final static Logger LOG = org.apache.log4j.Logger.getLogger(TwitterManager.class);

    private ITSDatabase database = ITSDatabase.instance();

    private Twitter twitter;

    public TwitterManager(){
        twitter = TwitterFactory.getSingleton();
        try {
            twitter.setOAuthConsumer(APIKEY, APISECRET);
        }catch (IllegalStateException e){
            LOG.warn(e.getMessage());
        }
    }

    public void configureAccount(String token, String tokenSecret, String id){
        AccessToken accessToken = new AccessToken(token, tokenSecret,Long.parseLong(id));
        database.twitterTokens().put(id,accessToken);
    }

    public String getHomeTimeline(String id) throws TwitterException {

        return getHomeTimeline(id,"");
    }

    public String getNextHomeTimeline(String id) throws TwitterException {

        return getHomeTimeline(id,database.maxTweetId().get(id));
    }

    String getHomeTimeline(String id, String maxId) throws TwitterException {

        AccessToken accessToken = database.twitterTokens().get(id);
        twitter.setOAuthAccessToken(accessToken);

        Paging paging = new Paging(1,5);
        if ( !maxId.isEmpty()) {
            paging.setMaxId(Long.parseLong(maxId));
        }

        List<Status> statuses = twitter.getHomeTimeline(paging);
        JSONArray jsonArray = new JSONArray();

        for (Status status : statuses) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("un",status.getUser().getName());
            jsonObject.put("usn", status.getUser().getScreenName());
            jsonObject.put("text", status.getText());
            jsonArray.put(jsonObject);
        }

        Long m = statuses.get(statuses.size()-1).getId() -1;
        database.maxTweetId().put(id,m.toString());

        twitter.setOAuthAccessToken(null);
        LOG.info("[" + id + "] Tweets retrieve");
        return jsonArray.toString();
    }


    public boolean postTweet(String id, String tweet){
        Status status = null;
        AccessToken accessToken = database.twitterTokens().get(id);
        twitter.setOAuthAccessToken(accessToken);
        try {
            status = twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            LOG.error(e.getMessage());
            return false;
        }
        LOG.info("[" +id + "] status updated to : \"" + status.getText() + "\"");
        return true;
    }


}
