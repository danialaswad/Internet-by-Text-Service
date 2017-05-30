package polytech.its.mobileapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private static TextView webArea;
    public static final String PHONE_NUMBER = "+33628760946";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_view);*/
        setContentView(R.layout.activity_home);
        IntentFilter filter = new IntentFilter(HomeActivity.SMS_RECEIVED);
        SmsListener sl = new SmsListener();
        registerReceiver(sl, filter);


        SmsReader sr = new SmsReader();

        webArea = (TextView) findViewById(R.id.pageView);

        Cursor cursor = sr.getMessages(HomeActivity.this);

        displaySms(cursor);
        //}
    }

    private void displaySms(Cursor cursor) {
        if (!cursor.moveToFirst()) { /* false = cursor is empty */
            return;
        }

        String smsContent = "";
        if (cursor.moveToLast()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                if (PHONE_NUMBER.equals(phone))
                    smsContent += cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();

                cursor.moveToPrevious();
            }
        }
        cursor.close();
        webArea.setText(smsContent);
    }

    static void updateTextView(String msg) {
        webArea.setText(webArea.getText() + msg);

    }

    public static class SmsListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
            /* Get Messages */
                Object[] sms = (Object[]) intentExtras.get("pdus");

                for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], "3gpp");
                    String phone = smsMessage.getOriginatingAddress();
                    String message = smsMessage.getMessageBody().toString();

                    if (phone.equals(PHONE_NUMBER)) {
                        updateTextView(message);
                    }
                }
            }
        }
    }


}
