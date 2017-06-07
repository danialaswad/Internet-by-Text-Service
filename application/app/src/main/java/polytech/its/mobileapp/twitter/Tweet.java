package polytech.its.mobileapp.twitter;

/**
 * @author: Abdelkarim Andolerzak
 */

public class Tweet {
    private String username; //Delka
    private String userScreenName; //@karimmature
    private String tweetText;

    public Tweet(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
}
