package twitter;

import database.ITSDatabase;
import org.junit.Assert;
import org.junit.Test;
import twitter4j.auth.AccessToken;

/**
 * TwitterManagerTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class TwitterManagerTest {

    @Test
    public void configureAccountTest(){
        TwitterManager tm = new TwitterManager();
        tm.configureAccount("testToken","testSecretToken","0123");
        ITSDatabase database = ITSDatabase.instance();
        AccessToken expected = new AccessToken("testToken","testSecretToken",Long.parseLong("0123"));
        Assert.assertEquals(expected,database.twitterTokens().get("0123"));
    }

}
