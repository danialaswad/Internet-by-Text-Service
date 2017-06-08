package polytech.its.mobileapp.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import polytech.its.mobileapp.R;
import polytech.its.mobileapp.twitter.Tweet;
import polytech.its.mobileapp.twitter.TwitterAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwitterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwitterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterListFragment extends Fragment {
    ListView listTweet;
    HomeActivity home;
    List<Tweet> listOfTweets;
    TwitterAdapter adapter;


    private OnFragmentInteractionListener mListener;

    public TwitterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TwitterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterListFragment newInstance() {
        return new TwitterListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_twitter_list, container, false);
        listTweet = (ListView) v.findViewById(R.id.listView);
        home = (HomeActivity) this.getActivity();
        ImageButton next = (ImageButton) v.findViewById(R.id.nextTweets);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = getString(R.string.TWITTERNEXT) + getTwitterId();
                home.sendMessage(message);
            }
        });

        String tweetsString = getArguments().getString("TweetsContent");

        listOfTweets = retrieveTweet(tweetsString);
        adapter = new TwitterAdapter(getActivity(), listOfTweets);
        listTweet.setAdapter(adapter);
        return v;

    }

    public long getTwitterId() {
        SharedPreferences sharedPref = home.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getLong(getString(R.string.userId), 0);

    }

    private List<Tweet> retrieveTweet(String tweetsString) {
        List<Tweet> listTweets = new ArrayList<>();
        try {
            JSONArray tweetArray = new JSONArray(tweetsString);
            for (int i = 0; i < tweetArray.length(); i++) {
                JSONObject jo = tweetArray.getJSONObject(i);
                listTweets.add(new Tweet(jo.getString("un"), "@" + jo.getString("usn"), jo.getString("text")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listTweets;
    }

    public void setTweetsToHandle(String tweetsToHandle) {
        List newTweets = retrieveTweet(tweetsToHandle);
        listOfTweets.addAll(newTweets);
        adapter.notifyDataSetChanged();
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
