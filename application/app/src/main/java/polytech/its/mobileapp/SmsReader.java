package polytech.its.mobileapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Karim on 30/05/2017.
 */
public class SmsReader {


    public Cursor getMessages(Context app ) {

        Cursor cur = app.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        return cur;
    }

}
