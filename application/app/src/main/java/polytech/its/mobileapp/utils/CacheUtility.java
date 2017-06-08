package polytech.its.mobileapp.utils;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import polytech.its.mobileapp.history.History;

/**
 * @author: Abdelkarim Andolerzak
 */

public class CacheUtility {

    public void saveWebsite(Context context, String filename, String content) throws IOException {
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        fos.write(content.getBytes());
        fos.close();
    }

    public List<History> retrieveWebsites() {


        return null;
    }
}
