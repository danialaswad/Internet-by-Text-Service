package web;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class URLShortener {

    private static final String GOOGURL = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&key=AIzaSyCPwDK5HD1C1kKJSLhLoRquom6mwv9Ydhs";

    private static final String SHORTURLID = "id";

    public String shorten(String longUrl)
    {
        String shortUrl = "";

        try
        {
            URLConnection conn = new URL(GOOGURL).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("{\"longUrl\":\"" + longUrl + "\"}");
            wr.flush();

            // Get the response
            BufferedReader rd =new BufferedReader( new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder jsonRespoonse = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                jsonRespoonse.append(line);
            }

            JSONObject json = new JSONObject(jsonRespoonse.toString());
            shortUrl = json.getString(SHORTURLID);

            wr.close();
            rd.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return shortUrl;
    }
}
