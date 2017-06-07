package polytech.its.mobileapp.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

/**
 * Created by Karim on 30/05/2017.
 */
public class SmsUtility {


    public Cursor getMessages(Context app) {

        Cursor cur = app.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        return cur;
    }

    public void sendMessage(String phone,String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null, null);
    }

}
