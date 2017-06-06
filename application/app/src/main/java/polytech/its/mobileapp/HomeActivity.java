package polytech.its.mobileapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Map;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class HomeActivity extends AppCompatActivity {
    public static final String PHONE_NUMBER = "+33628760946";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private final String HOME = "<h1>Bienvenue sur ITS</h1><p>Entrez l'URL dans la barre ci-dessus et soyez patients :) </p><br>Nous économisons les arbres de la fôrêt.";

    private static WebView webArea;
    private static EditText URLArea;
    private static ProgressBar mPbar;
    private static Button nextButton;
    private static ActionBar actionBar;
    private static View actionBarView;
    private static View mCustomView;

    private static Context context;


    String webSiteAsked = "";
    static String existingPageContent = "";

    private Twitter twitter;

    static boolean available = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        if (android.os.Build.VERSION.SDK_INT > 17) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Enregistre le SMS listener
        registerListener();

        //Récupère webview,edittext... pour les modifier
        viewTreatment();

        //Lire et afficher les SMS déjà présents sur le téléphone. A améliorer pour le systeme de cache?
        /*SmsUtility smsUtility = new SmsUtility();
        Cursor cursor = smsUtility.getMessages(HomeActivity.this);
        displaySms(cursor);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                sendMessage("OK:?");
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        if (available == false) {
                            clearAndUpdateView("<h1>Service indisponible</h1><br><p> Le serveur n'a pas répondu à la requête");
                            showToast("SERVICE INDISPONIBLE");
                        }
                    }
                }.start();
                return true;

            case R.id.action_twitter:
                twitterManagement();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void twitterManagement() {
        //Faire un traitement si on est déjà connecté
        Map<String, ?> credentials = retrieveTwitterCredentials();

        if ((credentials.containsKey(getString(R.string.token))) &&
                (credentials.containsKey(getString(R.string.secretToken))) &&
                (credentials.containsKey(getString(R.string.userId)))) {
            //Faire le traitement car on a déjà autorisé
        } else {
            twitterRegistration();
        }
    }

    private void twitterRegistration() {

        //Connexion etc
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(getString(R.string.consumer_key));
        configurationBuilder.setOAuthConsumerSecret(getString(R.string.consumer_secret));


        final TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter = twitterFactory.getInstance();
        try {
            final RequestToken requestToken = twitter.getOAuthRequestToken("twitter-callback:///");
            final String url = requestToken.getAuthenticationURL();
            WebView wv = (WebView) findViewById(R.id.pageView);
            wv.loadUrl(url);
        } catch (TwitterException exception) {
            exception.printStackTrace();
        }
    }

    private Map<String, ?> retrieveTwitterCredentials() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getAll();
    }

    private void viewTreatment() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        actionBarView = getSupportActionBar().getCustomView();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(mCustomView, new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        actionBar.setDisplayShowCustomEnabled(true);


        webArea = (WebView) findViewById(R.id.pageView);
        URLArea = (EditText) mCustomView.findViewById(R.id.urlEditor);
        mPbar = (ProgressBar) mCustomView.findViewById(R.id.web_view_progress);
        mPbar.setVisibility(View.GONE);
        nextButton = (Button) mCustomView.findViewById(R.id.nextButton);

        URLArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveURL(v);
            }
        });


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        webArea.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webSiteAsked = url;
                if (webSiteAsked.contains("twitter-callback:///") == true) {
                    final Uri uri = Uri.parse(url);
                    final String oauthVerifierParam = uri.getQueryParameter("oauth_verifier");
                    try {
                        final AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifierParam);
                        String token = accessToken.getToken();
                        String tokenSecret = accessToken.getTokenSecret();
                        long userId = accessToken.getUserId();

                        savePreferences(token, tokenSecret, userId);
                        clearViewAndSend(getString(R.string.TwitterConf) + token + "," + tokenSecret + "," + userId);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }


                } else {
                    clearViewAndSend(getString(R.string.GET) + webSiteAsked);
                }
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
        webArea.loadDataWithBaseURL(null, HOME, "text/html", "UTF-8", null);
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
        clearViewAndSend(getString(R.string.GET) + webSiteAsked);
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

    private static void displayToast(String msg) {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    static void clearAndUpdateView(String message) {
        webArea.loadUrl("about:blank");
        existingPageContent = "";
        webArea.loadDataWithBaseURL(null, message, "text/html", "utf-8", null);


    }


    static void updateWebView(String messageReceived) {
        webArea.loadUrl("about:blank");
        mPbar.setVisibility(View.GONE);
        if (!messageReceived.isEmpty()) {
            existingPageContent += messageReceived;
            webArea.loadDataWithBaseURL(null, existingPageContent, "text/html", "utf-8", null);
            if (nextButton != null)
                nextButton.setVisibility(View.VISIBLE);
        } else {
            webArea.loadDataWithBaseURL(null, existingPageContent, "text/html", "utf-8", null);
            if (nextButton != null)
                nextButton.setVisibility(View.GONE);
        }

    }

    public static void showToast(String msg) {
        displayToast(msg);
    }

    public void savePreferences(String token, String secretToken, long userId) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.secretToken), secretToken);
        editor.putString(getString(R.string.token), token);
        editor.putLong(getString(R.string.userId), userId);
        editor.commit();
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
        webArea.loadDataWithBaseURL(null, existingPageContent, "text/html", "UTF-8", null);
    }

    public static class SmsListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
            /* Get Messages */
                Object[] sms = (Object[]) intentExtras.get("pdus");
                String compressedMessage = "";

                for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], "3gpp");
                    String phone = smsMessage.getOriginatingAddress();

                    if (phone.equals(PHONE_NUMBER)) {
                        compressedMessage += smsMessage.getMessageBody().toString();

                    }
                }
                String decompressed = CompressionUtility.decompressFromBase64(compressedMessage, "UTF-8");
                if (decompressed.equals("ITS:AVAILABLE") && available == false) {
                    showToast("The service is available");
                    available = true;
                } else if (!decompressed.equals("ITS:AVAILABLE"))
                    updateWebView(decompressed);
            }
        }


    }
}
