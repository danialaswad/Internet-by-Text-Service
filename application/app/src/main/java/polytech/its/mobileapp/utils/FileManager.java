package polytech.its.mobileapp.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import polytech.its.mobileapp.R;

/**
 * Created by Karim on 11/06/2017.
 */

public class FileManager {

    public String getHelpFileValue(Context context) {
        String content = "";
        StringBuffer buf = new StringBuffer();
        InputStream inputStream = context.getResources().openRawResource(R.raw.help_page);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if (inputStream != null) {
            try {
                while ((content = reader.readLine()) != null) {
                    buf.append(content + "\n");
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf.toString();
    }
}
