package polytech.its.mobileapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class HomeActivity extends AppCompatActivity {
    public static final String PHONE_NUMBER = "+33628760946";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private static WebView webArea;
    private static EditText URLArea;
    private static ProgressBar mPbar;
    private static Button nextButton;


    String webSiteAsked = "";
    static String existingPageContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Enregistre le SMS listener
        registerListener();

        //Récupère webview,edittext... pour les modifier
        viewTreatment();

        //Lire et afficher les SMS déjà présents sur le téléphone. A améliorer pour le systeme de cache?
        /*SmsUtility smsUtility = new SmsUtility();
        Cursor cursor = smsUtility.getMessages(HomeActivity.this);
        displaySms(cursor);*/


    }

    private void viewTreatment() {
        webArea = (WebView) findViewById(R.id.pageView);
        URLArea = (EditText) findViewById(R.id.urlEditor);
        mPbar = (ProgressBar) findViewById(R.id.web_view_progress);
        mPbar.setVisibility(View.GONE);
        nextButton = (Button) findViewById(R.id.nextButton);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        webArea.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webSiteAsked = url;
                clearViewAndSend(getString(R.string.GET) + webSiteAsked);
                return true;
            }
        });
        setHomeWebView();


    }

    private void registerListener() {
        IntentFilter filter = new IntentFilter(HomeActivity.SMS_RECEIVED);
        SmsListener sl = new SmsListener();
        registerReceiver(sl, filter);
    }

    /**
     * Mise en place de la page d'accueil de l'application de SMS
     */
    private void setHomeWebView() {
        webArea.loadData(getString(R.string.Accueil), "text/html", "UTF-8");
    }

    /**
     * Nettoyage de la webview afin d'accueillir le futur site
     *
     * @param url
     */
    private void clearViewAndSend(String url) {
        existingPageContent = "";
        sendMessage(url);
    }

    /**
     * Callback du bouton d'envoi de l'URL
     *
     * @param view
     */

    void retrieveURL(View view) {
        webSiteAsked = URLArea.getText().toString();
        mPbar.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        sendMessage(getString(R.string.GET) + webSiteAsked);
    }

    /**
     * Callback du boutton afin de demander la suite d'une page chargée partiellement
     *
     * @param view
     */
    void askNext(View view) {
        String message = getString(R.string.NEXT) + webSiteAsked;
        mPbar.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        sendMessage(message);
    }

    /**
     * Envoie un SMS à un numéro prédéfini.
     *
     * @param msg le message qui doit être envoyé
     */
    void sendMessage(String msg) {
        SmsUtility sr = new SmsUtility();
        sr.sendMessage(PHONE_NUMBER, msg);
        displayToast("Message sent!");
    }

    private void displayToast(String msg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }


    static void updateWebView(String messageReceived) {
        webArea.loadUrl("about:blank");
        mPbar.setVisibility(View.GONE);
        if (!messageReceived.isEmpty()) {
            existingPageContent += messageReceived;
            webArea.loadDataWithBaseURL(null, existingPageContent, "text/html", "utf-8", null);
            nextButton.setVisibility(View.VISIBLE);
        } else
            nextButton.setVisibility(View.GONE);

    }

    /**
     * Récupération des SMS déjà enregistrés et vérification qu'il correspond au numéro du serveur
     * Pas utilisée pour l'instant
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
                    existingPageContent += cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();

                cursor.moveToPrevious();
            }
        }
        cursor.close();
        webArea.loadData(existingPageContent, "text/html", "UTF-8");
    }

    public static class SmsListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
            /* Get Messages */
                Object[] sms = (Object[]) intentExtras.get("pdus");
                String compressedMessage="";

                for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], "3gpp");
                    String phone = smsMessage.getOriginatingAddress();

                    if (phone.equals(PHONE_NUMBER)) {
                        compressedMessage += smsMessage.getMessageBody().toString();

                    }
                }
                String decompressed = CompressionUtility.decompressFromBase64(compressedMessage, "UTF-8");
                updateWebView(decompressed);
            }
        }

    }
}
