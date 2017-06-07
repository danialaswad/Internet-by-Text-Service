package polytech.its.mobileapp.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Map;

import polytech.its.mobileapp.R;
import polytech.its.mobileapp.utils.CompressionUtility;
import polytech.its.mobileapp.utils.SmsUtility;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class HomeActivity extends AppCompatActivity implements WebFragment.OnFragmentInteractionListener, TwitterFragment.OnFragmentInteractionListener, TwitterListFragment.OnFragmentInteractionListener {
    public static final String PHONE_NUMBER = "+33628760946";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private EditText URLArea;
    public ProgressBar mPbar;

    private static Context context;
    private static WebFragment webFragment;
    private static FragmentManager fragmentManager;

    String webSiteAsked = "";
    static String existingPageContent = "";
    static boolean available = false;

    public Twitter twitter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        if (Build.VERSION.SDK_INT > 17) {
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        GoogleApiClient client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                        if (!available) {
                            webFragment.clearAndUpdateView("<h1>Service indisponible</h1><br><p> Le serveur n'a pas répondu à la requête");
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void viewTreatment() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(mCustomView);
            actionBar.setDisplayShowCustomEnabled(true);

        }

        URLArea = (EditText) mCustomView.findViewById(R.id.urlEditor);
        mPbar = (ProgressBar) mCustomView.findViewById(R.id.web_view_progress);
        mPbar.setVisibility(View.GONE);

        URLArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveURL(v);
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fragmentManager = getSupportFragmentManager();
        webFragment = (WebFragment) fragmentManager.findFragmentById(R.id.fragment);

    }


    private void twitterManagement() {
        //Faire un traitement si on est déjà connecté
        Map<String, ?> credentials = retrieveTwitterCredentials();

        if ((credentials.containsKey(getString(R.string.token))) &&
                (credentials.containsKey(getString(R.string.secretToken))) &&
                (credentials.containsKey(getString(R.string.userId)))) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TwitterFragment twitterFragment = new TwitterFragment();
            fragmentTransaction.add(R.id.fragment, twitterFragment, "TWITTER");
            fragmentTransaction.commit();
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
            WebView wv = webFragment.webArea;
            wv.loadUrl(url);
        } catch (TwitterException exception) {
            exception.printStackTrace();
        }
    }

    private Map<String, ?> retrieveTwitterCredentials() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getAll();
    }


    private void registerListener() {
        IntentFilter filter = new IntentFilter(HomeActivity.SMS_RECEIVED);
        SmsListener sl = new SmsListener();
        registerReceiver(sl, filter);
    }


    /**
     * Nettoyage de la webview afin d'accueillir le futur site
     *
     * @param url
     */
    public void clearViewAndSend(String url) {
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
        webFragment.nextButton.setVisibility(View.GONE);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        clearViewAndSend(getString(R.string.GET) + webSiteAsked);
    }

    private static void showTweets(String decompressed) {
        int indexOfSplit = context.getString(R.string.TWITTERHOME).length();
        decompressed = decompressed.substring(indexOfSplit);
        Bundle bundle = new Bundle();
        bundle.putString("TweetsContent", decompressed);
        TwitterListFragment tlf = new TwitterListFragment();
        tlf.setArguments(bundle);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment, tlf, "twitter_list_fragment");
        ft.commit();
    }

    private static void showNextTweets(String decompressed) {
        int indexOfSplit = context.getString(R.string.TWITTERNEXT).length();
        decompressed = decompressed.substring(indexOfSplit);
        TwitterListFragment tlf = (TwitterListFragment) fragmentManager.findFragmentByTag("twitter_list_fragment");
        tlf.setTweetsToHandle(decompressed);
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


    public static void showToast(String msg) {
        displayToast(msg);
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
        //webFragment.webArea.loadDataWithBaseURL(null, existingPageContent, "text/html", "UTF-8", null);
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
                } else if (decompressed.contains("TWITTERCONF:SUCCESS")) {
                    showToast("Le compte Twitter a été enregistré");
                } else if (decompressed.contains(context.getString(R.string.TWITTERHOME))) {
                    showTweets(decompressed);

                } else if (decompressed.contains(context.getString(R.string.TWITTERNEXT))) {
                    showNextTweets(decompressed);

                } else if (decompressed.equals("WEB:")) {
                    webFragment.updateWebView(decompressed);
                }

            }
        }


    }
}
