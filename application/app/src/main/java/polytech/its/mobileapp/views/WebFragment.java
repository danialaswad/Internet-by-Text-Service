package polytech.its.mobileapp.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import polytech.its.mobileapp.R;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("deprecation")
public class WebFragment extends Fragment {
    private String existingPageContent = "";

    public WebView webArea;
    public ImageButton nextButton;

    HomeActivity home;


    private OnFragmentInteractionListener mListener;

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Mise en place de la page d'accueil de l'application de SMS
     */
    private void setHomeWebView() {
        String HOME = "<h1>Bienvenue sur ITS</h1><p>Entrez l'URL dans la barre ci-dessus et soyez patients :) </p><br>Nous économisons les arbres de la fôrêt.";
        webArea.loadDataWithBaseURL(null, HOME, "text/html", "UTF-8", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);


        //home.mPbar.setVisibility(View.GONE);
        nextButton = (ImageButton) view.findViewById(R.id.nextButton);
        home = ((HomeActivity) getActivity());

        webArea = (WebView) view.findViewById(R.id.pageView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.NEXT) + home.webSiteAsked;
                home.mPbar.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.GONE);
                home.sendMessage(message);
            }
        });

        webArea.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("twitter-callback:///")) {
                    final Uri uri = Uri.parse(url);
                    final String oauthVerifierParam = uri.getQueryParameter("oauth_verifier");
                    try {
                        final AccessToken accessToken = home.twitter.getOAuthAccessToken(oauthVerifierParam);
                        String token = accessToken.getToken();
                        String tokenSecret = accessToken.getTokenSecret();
                        long userId = accessToken.getUserId();

                        savePreferences(token, tokenSecret, userId);
                        home.clearViewAndSend(getString(R.string.TwitterConf) + token + "," + tokenSecret + "," + userId);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }


                } else {
                    home.clearViewAndSend(getString(R.string.GET) + url);
                }
                return true;
            }
        });
        setHomeWebView();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void updateWebView(String messageReceived, String typePage) {
        webArea.loadUrl("about:blank");
        home.mPbar.setVisibility(View.GONE);
        messageReceived = messageReceived.substring(typePage.length());
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

    void clearAndUpdateView(String message) {
        webArea.loadUrl("about:blank");
        existingPageContent = "";
        webArea.loadDataWithBaseURL(null, message, "text/html", "utf-8", null);
    }

    public void savePreferences(String token, String secretToken, long userId) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.secretToken), secretToken);
        editor.putString(getString(R.string.token), token);
        editor.putLong(getString(R.string.userId), userId);
        editor.apply();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}