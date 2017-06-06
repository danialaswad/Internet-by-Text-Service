package twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * TwitterTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class TwitterTest {

    private ConfigurationBuilder configurationBuilder;
    private Twitter twitter;

    public TwitterTest() throws TwitterException, IOException {

        configurationBuilder  = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey("D0P3ASuzisny7WImMXDnAHRqK");
        configurationBuilder.setOAuthConsumerSecret("HgzslT0pdSNNYOuRnr2ft3RZ4Z92jOCHiylhxYKQTD9BgRZtFH");
        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("pIi6kVnGy12GFjECWELC8R4VK", "WsCcYqlpJJ15DaRsKd7AnviQUEEn9Rd1PugDt6uFcdUvCz2Fxa");
        RequestToken requestToken = twitter.getOAuthRequestToken("oob");
        AccessToken accessToken = new AccessToken("941791010-bU9IpOXf8KgOPQ2OdXYAQw4qBKOtSG8a2qKGUdEg","LG8wAfcGVUoCgNCt44VLUJjx2rpbDLIfYHX2OHZnIy4ZT",941791010);
        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try {
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }*/
        System.out.println(accessToken.toString());
        twitter.setOAuthAccessToken(accessToken);
        List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
        }
        System.out.println("size : " + statuses.size());
        System.out.println("Fin");
    }
}
