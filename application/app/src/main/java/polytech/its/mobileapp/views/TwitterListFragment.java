package polytech.its.mobileapp.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        TwitterListFragment fragment = new TwitterListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_twitter_list, container, false);
        listTweet = (ListView) getActivity().findViewById(R.id.listView);

        List<Tweet> tweets = new ArrayList<>();
        TwitterAdapter adapter = new TwitterAdapter(getActivity(), tweets);
        listTweet.setAdapter(adapter);
        return v;

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
