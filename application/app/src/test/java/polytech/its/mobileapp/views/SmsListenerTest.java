package polytech.its.mobileapp.views;

import android.content.Context;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.shadows.ShadowHandler;

import java.io.IOError;
import java.io.IOException;

import polytech.its.mobileapp.views.HomeActivity.SmsListener;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Karim on 11/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsListenerTest {

    SmsListener smsListener;

    @Mock
    HomeActivity home;

    @Before
    public void init() {
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
//
//        String response = smsListener.filterResponse(message1);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message2);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message3);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message4);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message5);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message6);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message7);
//        assertEquals("Salut", response);
//
//        response = smsListener.filterResponse(message8);
//        assertEquals("Salut", response);


    }

}