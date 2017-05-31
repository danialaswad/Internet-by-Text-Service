package polytech.its.mobileapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

public class HomeActivity extends AppCompatActivity {
    public static final String PHONE_NUMBER = "+33628760946";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private static WebView webArea;
    private static EditText URLArea;
    private static ProgressBar mPbar;


    String url = "";
    static String smsContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        IntentFilter filter = new IntentFilter(HomeActivity.SMS_RECEIVED);
        SmsListener sl = new SmsListener();
        registerReceiver(sl, filter);

        webArea = (WebView) findViewById(R.id.pageView);
        URLArea = (EditText) findViewById(R.id.urlEditor);
        mPbar = (ProgressBar) findViewById(R.id.web_view_progress);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        SmsUtility sr = new SmsUtility();

        //Lire et afficher les SMS déjà présents sur le téléphone. A améliorer pour le systeme de cache? s
        /*Cursor cursor = sr.getMessages(HomeActivity.this);
        displaySms(cursor);*/

        setTextViewAccueil();


        webArea.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("TESTO", url);
                clearViewAndSend(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPbar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                mPbar.setVisibility(View.GONE);
            }
        });
    }

    private void setTextViewAccueil() {
        webArea.loadData("<h1>Bienvenue sur ITS</h1><p>Entrez l'URL dans la barre ci-dessus et soyez patients :) </p>", "text/html", "UTF-8");
    }

    /**
     * Nettoyage de la webview afin d'accueillir le futur site
     *
     * @param url
     */
    private void clearViewAndSend(String url) {
        webArea.loadUrl("about:blank");
        smsContent = "";
        sendMessage(url);
    }

    /**
     * Récupération des SMS et vérification qu'il correspond au numéro du serveur
     *
     * @param cursor
     */
    private void displaySms(Cursor cursor) {
        if (!cursor.moveToFirst()) { /* false = cursor is empty */
            return;
        }

        if (cursor.moveToLast()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                if (PHONE_NUMBER.equals(phone))
                    smsContent += cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();

                cursor.moveToPrevious();
            }
        }
        cursor.close();
        webArea.loadData(smsContent, "text/html", "UTF-8");
    }

    /**
     * Callback du bouton d'envoi
     *
     * @param view
     */

    void retrieveURL(View view) {
        //Récupérer l'URL

        url = URLArea.getText().toString();
        URLArea.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        sendMessage(url);
    }

    void sendMessage(String msg) {
        SmsUtility sr = new SmsUtility();
        sr.sendMessage(PHONE_NUMBER, msg);
    }

    static void updateTextView(String msg) {
        smsContent += msg;
        webArea.loadData(smsContent, "text/html", "UTF-8");

    }

    public static class     SmsListener extends BroadcastReceiver {
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
