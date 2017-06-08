package polytech.its.mobileapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import polytech.its.mobileapp.R;

/**
 * @author: Abdelkarim Andolerzak
 */

public class TweetDialog extends DialogFragment {
    public EditText tweetArea;
    public Button sendTweetButton;
    public String initialButtonText;
    public HomeActivity home;

    public TweetDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_tweet, new LinearLayout(getActivity()), false);

        tweetArea = (EditText) view.findViewById(R.id.tweetArea);
        sendTweetButton = (Button) view.findViewById(R.id.sendTweetButton);
        initialButtonText = sendTweetButton.getText().toString();
        // Retrieve layout elements
        tweetArea.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() >= 140) {
                    tweetArea.setTextColor(Color.RED);
                    sendTweetButton.setClickable(false);

                } else {
                    tweetArea.setTextColor(Color.BLACK);
                    sendTweetButton.setClickable(true);
                }
                int remaining = 140 - tweetArea.getText().length();
                sendTweetButton.setText(initialButtonText + "(" + remaining + ")");

            }

            public void afterTextChanged(Editable s) {

                // This sets a textview to the current length
                // mTextView.setTweetText(String.valueOf(s.length()));

            }
        });
        home = ((HomeActivity) getActivity());

        final long twitterId = getTwitterId();

        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.sendMessage(getString(R.string.TWEET) + twitterId + "," + tweetArea.getText());
                dismiss();
            }
        });
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    public long getTwitterId() {
        SharedPreferences sharedPref = home.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getLong(getString(R.string.userId), 0);

    }
}
