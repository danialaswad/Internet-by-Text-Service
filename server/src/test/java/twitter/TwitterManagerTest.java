package twitter;

import database.ITSDatabaseSQL;
import org.junit.Assert;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.auth.AccessToken;

import java.sql.SQLException;
import java.util.List;

/**
 * TwitterManagerTest
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class TwitterManagerTest {

    @Test
    public void configureAccountTest() throws SQLException {
        TwitterManager tm = new TwitterManager();
        tm.configureAccount("testToken","testSecretToken","0123");
        AccessToken expected = new AccessToken("testToken","testSecretToken",Long.parseLong("0123"));
        List<String> list = ITSDatabaseSQL.getTwitterToken("0123");
        Assert.assertTrue(expected.equals(new AccessToken(list.get(0),list.get(1),Long.parseLong("0123"))));
    }

}
