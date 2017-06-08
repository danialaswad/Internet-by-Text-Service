package polytech.its.mobileapp.twitter;

/**
 * @author: Abdelkarim Andolerzak
 */

public class Tweet {
    private String username; //Delka
    private String userScreenName; //@karimmature
    private String tweetText;

    public Tweet(String username, String userScreenName, String tweetText) {
        this.username = username;
        this.userScreenName = userScreenName;
        this.tweetText = tweetText;
    }

    String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }
}
