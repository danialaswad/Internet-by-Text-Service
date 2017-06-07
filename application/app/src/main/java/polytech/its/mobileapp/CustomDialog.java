package polytech.its.mobileapp;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Karim on 07/06/2017.
 */

public class CustomDialog extends DialogFragment {

    public CustomDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_tweet, new LinearLayout(getActivity()), false);

        // Retrieve layout elements
        TextView title = (TextView) view.findViewById(R.id.tweetArea);

        // Set values
        title.setText("Ecrivez votre tweet");

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
}
