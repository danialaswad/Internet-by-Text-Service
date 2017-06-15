package twitter;

import database.ITSDatabaseSQL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import twitter4j.JSONException;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void processStatusesTests() throws JSONException, TwitterException {
        TwitterManager tm = new TwitterManager();

        Status status1 = mockStatus("mosser","petittroll","We test ITS with mockito !!");
        Status status2 = mockStatus("andoKarim","karimmature","Ohh yeah mockito c'est la vie !!");


        ArrayList<Status> list = new ArrayList<>();
        list.add(status1);
        list.add(status2);

        JSONArray expected = new JSONArray();
        JSONObject object = new JSONObject().put("un","mosser").put("usn","petittroll").put("text","We test ITS with mockito !!");
        expected.put(object);
        object = new JSONObject().put("un","andoKarim").put("usn","karimmature").put("text","Ohh yeah mockito c'est la vie !!");
        expected.put(object);

        JSONArray result = tm.processStatuses(list);

        Assert.assertEquals(expected.toString(),result.toString());
    }

    private Status mockStatus(String name,String userName,String text){
        User user = mockUser(name,userName);
        Status status = mock(Status.class);
        when(status.getText()).thenReturn(text);
        when(status.getUser()).thenReturn(user);
        return status;
    }

    private User mockUser(String name,String userName) {
        User user = mock(User.class);
        when(user.getName()).thenReturn(name);
        when(user.getScreenName()).thenReturn(userName);
        return user;
    }

}
