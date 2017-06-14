package twitter;

import database.ITSDatabaseSQL;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TwitterManager class
 * @Author : ITS Team
 */


public class TwitterManager {

    private static final String APIKEY = "pIi6kVnGy12GFjECWELC8R4VK";
    private static final String APISECRET = "WsCcYqlpJJ15DaRsKd7AnviQUEEn9Rd1PugDt6uFcdUvCz2Fxa";


    private final static Logger LOG = org.apache.log4j.Logger.getLogger(TwitterManager.class);


    private Twitter twitter;

    public TwitterManager(){
        twitter = TwitterFactory.getSingleton();
        try {
            twitter.setOAuthConsumer(APIKEY, APISECRET);
        }catch (IllegalStateException e){
            LOG.warn(e.getMessage());
        }
    }

    /**
     * Stores the id, token and the token secret in the database
     * @param token
     * @param tokenSecret
     * @param id
     */
    public void configureAccount(String token, String tokenSecret, String id){
        //AccessToken accessToken = new AccessToken(token, tokenSecret,Long.parseLong(id));
        try {
            ITSDatabaseSQL.addTwitterToken(id,token,tokenSecret);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        //database.twitterTokens().put(id,accessToken);
    }

    /**
     * Returns the latest tweet from the user timeline
     * @param id
     * @return String
     * @throws TwitterException
     */
    public String getHomeTimeline(String id) throws TwitterException {

        return getHomeTimeline(id,"");
    }

    /**
     * Returns the next timeline tweets. Tweets will be retrieve starting from the max tweet id
     * @param id
     * @return String
     * @throws TwitterException
     */
    public String getNextHomeTimeline(String id) throws TwitterException {
        String maxTweet = "";
        try {
            maxTweet = ITSDatabaseSQL.getMaxTweetID(id);
            return getHomeTimeline(id,maxTweet);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return "";
    }

    /**
     * Retrieve the timeline of a given id and the max tweet id
     * A maximum of 5 tweets will be retrieve
     * @param id
     * @param maxId
     * @return
     * @throws TwitterException
     */
    String getHomeTimeline(String id, String maxId) throws TwitterException {
        JSONArray jsonArray = new JSONArray();
        try {
            ArrayList<String> list = ITSDatabaseSQL.getTwitterToken(id);
            AccessToken accessToken = new AccessToken(list.get(0),list.get(1),Long.parseLong(id));
            twitter.setOAuthAccessToken(accessToken);

            Paging paging = new Paging(1,5);
            if ( !maxId.isEmpty()) {
                paging.setMaxId(Long.parseLong(maxId));
            }

            List<Status> statuses = twitter.getHomeTimeline(paging);
            jsonArray = processStatuses(statuses);
            Long m = statuses.get(statuses.size()-1).getId() -1;

            ITSDatabaseSQL.addMaxTweet(id,m.toString());
            twitter.setOAuthAccessToken(null);
            LOG.info("[" + id + "] Tweets retrieve");
            return jsonArray.toString();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        return jsonArray.toString();
    }

    /**
     * Filter all the unecessary info from a status
     * @param statuses
     * @return JSONArray
     */
    private JSONArray processStatuses(List<Status> statuses){
        JSONArray jsonArray = new JSONArray();
        for (Status status : statuses) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("un",status.getUser().getName());
            jsonObject.put("usn", status.getUser().getScreenName());
            jsonObject.put("text", status.getText());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    /**
     * Post tweet on twitter by providing tweet id and the message
     * @param id
     * @param tweet
     * @return boolean
     */
    public boolean postTweet(String id, String tweet){
        Status status = null;
        ArrayList<String> list = null;
        try {
            list = ITSDatabaseSQL.getTwitterToken(id);
            AccessToken accessToken = new AccessToken(list.get(0),list.get(1),Long.parseLong(id));
            twitter.setOAuthAccessToken(accessToken);
            status = twitter.updateStatus(tweet);
        } catch (TwitterException | SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        LOG.info("[" +id + "] status updated to : \"" + status.getText() + "\"");
        return true;
    }


}
