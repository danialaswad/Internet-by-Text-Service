package polytech.its.mobileapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import polytech.its.mobileapp.views.HomeActivity;
import polytech.its.mobileapp.views.HomeActivity.SmsListener;

import static org.junit.Assert.assertEquals;

/**
 * Created by Karim on 11/06/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
class SmsListenerTest {

    SmsListener smsListener;
    HomeActivity activity;

    @Before
    public void init() {
        activity = Robolectric.buildActivity(HomeActivity.class)
                .create()
                .resume()
                .get();
        smsListener = new SmsListener();
    }

    @Test
    public void filterResponse() {

        String message1 = "ITS:AVAILABLE";
        String message2 = "TWITTERCONF:AVAILABLE";
        String message3 = "WEB:BLABBLA";
        String message4 = "TWITTERHOME:BLABLA";
        String message5 = "TWITTERNEXT:BLLBLBL";
        String message6 = "WEBNEXT:ZAEAZEAZE";
        String message7 = "TWEET:SUCCESS";
        String message8 = "BLAberpezr";


        String response = smsListener.filterResponse(message1);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message2);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message3);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message4);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message5);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message6);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message7);
        assertEquals("Salut", response);

        response = smsListener.filterResponse(message8);
        assertEquals("Salut", response);


    }

}