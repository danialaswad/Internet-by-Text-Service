package polytech.its.mobileapp.twitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import polytech.its.mobileapp.R;
import polytech.its.mobileapp.twitter.Tweet;

/**
 * @author: Abdelkarim Andolerzak
 */

public class TwitterAdapter extends ArrayAdapter<Tweet> {
    public TwitterAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_layout, parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.nom = (TextView) convertView.findViewById(R.id.pseudoAt);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tweetText);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Tweet tweet = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(tweet.getUsername());
        viewHolder.nom.setText(tweet.getUserScreenName());
        viewHolder.text.setText(tweet.getTweetText());

        return convertView;
    }

    private class TweetViewHolder {
        public TextView pseudo;
        public TextView nom;
        public TextView text;
    }

}
