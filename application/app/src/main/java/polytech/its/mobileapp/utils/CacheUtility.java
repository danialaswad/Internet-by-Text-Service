package polytech.its.mobileapp.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import polytech.its.mobileapp.history.History;

/**
 * @author: Abdelkarim Andolerzak
 */

public class CacheUtility {

    public void saveWebsite(Context context, String filename, String content) throws IOException {
        File f = new File(context.getCacheDir(), filename);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(content.getBytes());
        fos.close();
    }

    public Map<String, History> retrieveWebsites(Context context) throws IOException {
        File directory = context.getCacheDir();
        File[] files = directory.listFiles();
        ArrayList<File> list = new ArrayList<>(Arrays.asList(files));
        Map<String, History> savedSites = new HashMap<>();
        for (File f : list) {
            if (!f.isDirectory()) {
                String name = f.getName();
                String content = "";
                FileInputStream fis = new FileInputStream(f);
                int size;

                // read inside if it is not null (-1 means empty)
                while ((size = fis.read()) != -1) {
                    // add & append content
                    content += Character.toString((char) size);
                }
                History h = new History(name, content);
                savedSites.put(name, h);
                fis.close();
            }
        }
        return savedSites;
    }
}
