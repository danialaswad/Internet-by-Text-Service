package polytech.its.mobileapp.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import polytech.its.mobileapp.history.History;

/**
 * @author: Abdelkarim Andolerzak
 */

public class CacheUtility {

    public File saveWebsite(Context context, String filename, String content) throws IOException {
        File mydir = context.getDir("cachedSites", Context.MODE_PRIVATE); //Creating an internal dir;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateAndTime = sdf.format(new Date());
        File f = new File(mydir, filename + "_" + currentDateAndTime);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(content.getBytes());
        fos.close();

        return f;


    }

    public Map<String, History> retrieveWebsites(Context context) throws IOException {
        File directory = context.getDir("cachedSites", Context.MODE_PRIVATE); //Creating an internal dir;s
        File[] files = directory.listFiles();
        ArrayList<File> list = new ArrayList<>(Arrays.asList(files));
        Map<String, History> savedSites = new HashMap<>();
        for (File f : list) {
            if (!f.isDirectory()) {
                String name = f.getName();
                String content = "";
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(f), "UTF8"));
                String str;
                // read inside if it is not null (-1 means empty)
                while ((str = in.readLine()) != null) {
                    content += str;
                }
                History h = new History(name, content);
                savedSites.put(name, h);
                in.close();
            }
        }
        return savedSites;
    }
}
