package polytech.its.mobileapp.views;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.Map;

import polytech.its.mobileapp.R;
import polytech.its.mobileapp.utils.CacheUtility;
import polytech.its.mobileapp.utils.CompressionUtility;
import polytech.its.mobileapp.utils.FileManager;
import polytech.its.mobileapp.utils.ImageManager;
import polytech.its.mobileapp.utils.SmsUtility;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class HomeActivity extends AppCompatActivity implements WebFragment.OnFragmentInteractionListener, TwitterFragment.OnFragmentInteractionListener, TwitterListFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, WeatherFragment.OnFragmentInteractionListener {
    public static String PHONE_NUMBER;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private EditText URLArea;
    public ProgressBar mPbar;

    private static Context context;
    public static WebFragment webFragment;
    private static FragmentManager fragmentManager;
    private static View layout;
    private static Context dialogContext;

    public static String webSiteAsked = "www.localhost.fr";
    static boolean available = false;
    final String HOME = "<h1>Bienvenue sur ITS</h1><p>Entrez l'URL dans la barre ci-dessus et soyez patients :) </p><br>Nous économisons les arbres de la fôrêt.<br><img alt=\"Image de film\" src=\"http://anasghira.com/its/test.png\"></img>";

    static String imageData = "";

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Enregistre le SMS listener
        registerListener();

        PHONE_NUMBER = "+33628760946";
        dialogContext = HomeActivity.this;

        //Récupère webview,edittext... pour les modifier
        viewTreatment();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String newPhone = "";
        switch (item.getItemId()) {
            case R.id.action_send:
                Fragment f = fragmentManager.findFragmentById(R.id.fragment);
                if (f instanceof WeatherFragment)
                    retrieveCity();
                else
                    retrieveURL();
                return true;
            case R.id.action_stop:
                sendMessage(getString(R.string.end) + webSiteAsked);
                if (webFragment.nextButton.getVisibility() == View.VISIBLE)
                    webFragment.nextButton.setVisibility(View.INVISIBLE);
                return true;

            case R.id.action_twitter:
                twitterManagement();
                return true;

            case R.id.action_weather:
                WeatherFragment frag = new WeatherFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment, frag, "WEATHER").commit();
                return true;
            case R.id.action_save:
                try {
                    new CacheUtility().saveWebsite(this, webSiteAsked, webFragment.getExistingPageContent());
                    showToast(getString(R.string.web_saved));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            case R.id.action_history:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction.replace(R.id.fragment, historyFragment, "HISTORY");
                fragmentTransaction.commit();
                return true;

            case R.id.action_country1:
                newPhone = "+33628760946";
                if (newPhone != PHONE_NUMBER) {
                    PHONE_NUMBER = newPhone;
                    showToast(getString(R.string.service_changed));
                }
                return true;
            case R.id.action_country2:
                newPhone = "+33633192265";
                if (newPhone != PHONE_NUMBER) {
                    PHONE_NUMBER = newPhone;
                    showToast(getString(R.string.service_changed));
                }
                return true;
            case R.id.action_check:
                sendMessage(getString(R.string.isOk));
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        String message = "<h1>Service indisponible</h1><br><p> Le serveur n'a pas répondu à la requête";
                        if (!available) {
                            if (!(fragmentManager.findFragmentById(R.id.fragment) instanceof WebFragment)) {
                                Bundle bundle = new Bundle();
                                bundle.putString("content", message);
                                webFragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.fragment, webFragment).commit();
                            }
                            webFragment.clearAndUpdateView(message);
                            showToast(getString(R.string.service_unavailable));
                        }
                    }
                }.start();
                return true;
            case R.id.action_help:
                String helpPage = new FileManager().getHelpFileValue(context);

                if (!(fragmentManager.findFragmentById(R.id.fragment) instanceof WebFragment)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", helpPage);
                    webFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().remove(webFragment);
                    fragmentManager.beginTransaction().replace(R.id.fragment, webFragment).commit();
                }
                webFragment.showHelp(helpPage);
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
        layout = mCustomView.inflate(context, R.layout.custom_fullimage_dialog,
                (ViewGroup) findViewById(R.id.layout_root));


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fragmentManager = getSupportFragmentManager();
        webFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", HOME);
        webFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.fragment, webFragment).commit();

    }


    private void twitterManagement() {
        //Faire un traitement si on est déjà connecté
        Map<String, ?> credentials = retrieveTwitterCredentials();

        if ((credentials.containsKey(getString(R.string.token))) &&
                (credentials.containsKey(getString(R.string.secretToken))) &&
                (credentials.containsKey(getString(R.string.userId)))) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TwitterFragment twitterFragment = new TwitterFragment();
            fragmentTransaction.remove(webFragment);
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
     * Callback du bouton d'envoi de l'URL
     */

    String retrieveURL() {
        webSiteAsked = URLArea.getText().toString();
        mPbar.setVisibility(View.VISIBLE);
        webFragment.nextButton.setVisibility(View.GONE);

        View view = this.findViewById(android.R.id.content);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        webFragment.clearViewAndSend(getString(R.string.GET) + webSiteAsked);

        return webSiteAsked;
    }

    public void retrieveCity() {
        String cityAsked = URLArea.getText().toString();

        View view = this.findViewById(android.R.id.content);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        sendMessage(getString(R.string.getweather) + cityAsked);

    }

    private static void showTweets(String decompressed) {
        int indexOfSplit = context.getString(R.string.TWITTERHOME).length();
        decompressed = decompressed.substring(indexOfSplit);
        Bundle bundle = new Bundle();
        bundle.putString("TweetsContent", decompressed);

        TwitterListFragment tlf = new TwitterListFragment();
        tlf.setArguments(bundle);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, tlf, "twitter_list_fragment");
        ft.commit();
    }

    private static void showNextTweets(String decompressed) {
        int indexOfSplit = context.getString(R.string.TWITTERNEXT).length();
        decompressed = decompressed.substring(indexOfSplit);
        TwitterListFragment tlf = (TwitterListFragment) fragmentManager.findFragmentByTag("twitter_list_fragment");
        tlf.setTweetsToHandle(decompressed);
    }

    private static void updateWebFragment(String decompressed, String string) {

        Fragment f = fragmentManager.findFragmentById(R.id.fragment);
        String content = decompressed.substring(string.length());
        if (!(f instanceof WebFragment)) {
            WebFragment fragobj = HomeActivity.webFragment;
            Bundle bundle = fragobj.getArguments();
            bundle.putString("content", content);
            fragmentManager.beginTransaction().remove(f).commit();
            fragmentManager.beginTransaction().remove(fragobj).commit();
            fragmentManager.beginTransaction().add(R.id.fragment, fragobj).commit();
        } else
            webFragment.updateWebView(content);
    }

    private static void showWeather(String decompressed, String string) {
        Fragment f = fragmentManager.findFragmentById(R.id.fragment);
        String weatherInfo = decompressed.substring(string.length());
        if (f instanceof WeatherFragment) {
            ((WeatherFragment) f).setDisplay(weatherInfo);
        }
    }

    public static void displayPopup() {
        Bitmap bitmap = new ImageManager().imageBuilder(imageData);

        AlertDialog.Builder ImageDialog = new AlertDialog.Builder(dialogContext);
        LayoutInflater factory = LayoutInflater.from(dialogContext);
        final View view = factory.inflate(R.layout.custom_fullimage_dialog, null);
        ImageDialog.setView(view);

        ImageView showImage = (ImageView) view.findViewById(R.id.fullimage);
        showImage.setImageBitmap(bitmap);
        ImageDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        ImageDialog.show();

    }

    /**
     * Envoie un SMS à un numéro prédéfini.
     *
     * @param msg le message qui doit être envoyé
     */
    void sendMessage(String msg) {
        SmsUtility sr = new SmsUtility();
        sr.sendMessage(PHONE_NUMBER, msg);
        displayToast("Message envoyé!");
    }

    public static void displayToast(String msg) {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }


    public static void showToast(String msg) {
        displayToast(msg);
    }

    public static void saveImage() {

        String fileName = webSiteAsked + "_" + webFragment.imageURL + "_" + "cache.png";
        try {
            String newURL = "file://" + new CacheUtility().saveImage(context, fileName, imageData);
            String pageContent = webFragment.getExistingPageContent();
            pageContent = pageContent.replace(webFragment.imageURL, newURL);
            webFragment.clearAndUpdateView(pageContent);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static class SmsListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();

            if (intentExtras != null) {
            /* Get Messages */
                Object[] sms = (Object[]) intentExtras.get("pdus");
                String compressedMessage = "";

                if (sms != null) {
                    for (Object sm : sms) {
                    /* Parse Each Message */
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sm, "3gpp");
                        String phone = smsMessage.getOriginatingAddress();

                        if (phone.equals(PHONE_NUMBER)) {
                            compressedMessage += smsMessage.getMessageBody();

                        }
                    }
                }
                String decompressed = CompressionUtility.decompressFromBase64(compressedMessage, "UTF-8");

                filterResponse(decompressed);

            }
        }

        public String filterResponse(String decompressed) {
            if (decompressed.equals(context.getString(R.string.available)) && !available) {
                showToast(context.getString(R.string.disponible));
                available = true;
                return "Check service";
            } else if (decompressed.contains(context.getString(R.string.twitterSuccess))) {
                showToast(context.getString(R.string.TwitterRegisterSuccess));
                fragmentManager.beginTransaction().replace(R.id.fragment, new TwitterFragment(), "TWITTERVIEW").commit();
                return "Twitter OK";

            } else if (decompressed.contains(context.getString(R.string.TWITTERHOME))) {
                showTweets(decompressed);
                return "Show Timeline";

            } else if (decompressed.contains(context.getString(R.string.TWITTERNEXT))) {
                showNextTweets(decompressed);
                return "Show Next Tweets";

            } else if (decompressed.contains(context.getString(R.string.web))) {
                updateWebFragment(decompressed, context.getString(R.string.web));
                return "Show page";
            } else if (decompressed.contains(context.getString(R.string.next))) {
                updateWebFragment(decompressed, context.getString(R.string.next));
                return "Show next page";

            } else if (decompressed.contains(context.getString(R.string.tweetSuccess))) {
                showToast(context.getString(R.string.SuccessTweet));
                return "Tweet sent";
            } else if (decompressed.contains(context.getString(R.string.weatherCmd))) {
                showWeather(decompressed, context.getString(R.string.weatherCmd));
            } else if (decompressed.contains(context.getString(R.string.image))) {
                imageData += decompressed.substring(context.getString(R.string.image).length());
            } else if (decompressed.contains(context.getString(R.string.imgEnd))) {
                saveImage();
                displayPopup();

            }
            return "Commande incomprise";

        }


    }
}
