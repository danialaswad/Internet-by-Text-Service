package weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;

/**
 * WeatherProxy class
 *
 * @Author : ITS Team
 **/
public class WeatherProxy {
    final static String urlAPI = "http://api.openweathermap.org/data/2.5/weather?APPID=a45c1e831e27780fcaf1cb59dd223396&units=metric&mode=xml&q=";

    /**
     * Returns the wheather from a given city
     * @param city
     * @return String
     * @throws IOException
     */
    public static String getWeather(String city) throws IOException {
        String urlString = urlAPI + city;
        Document result = Jsoup.connect(urlString).get();
        return processRequest(result);
    }

    /**
     * Filter the response given by the API to retrieve only the necessary info
     * @param response
     * @return String
     */
    static String processRequest(Document response){
        String temp = response.select("temperature").first().attr("value").split("\\.")[0];
        String weather = response.select("weather").first().attr("value");
        String weatherID = response.select("weather").first().attr("number");
        String vent = response.select("speed").first().attr("value");
        int ventKM = (int)(Float.parseFloat(vent)*3.6);
        String humidite = response.select("humidity").first().attr("value");
        String sunrise = response.select("sun").first().attr("rise").split("T",2)[1];
        String sunset = response.select("sun").first().attr("set").split("T",2)[1];
        String name = response.select("city").first().attr("name");
        String output = name+","+temp+"°C,"+weatherID+","+weather+","+ventKM+" Km/h,"+humidite+"%,"+sunrise+" (UTC),"+sunset+" (UTC)";
        return output;
    }
}
