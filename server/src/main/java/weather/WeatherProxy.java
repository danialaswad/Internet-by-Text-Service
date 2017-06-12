package weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;

/**
 * weather.WeatherProxy
 *
 * @author Anasse GHIRA
 * @version 1.0
 */
public class WeatherProxy {
    final static String urlAPI = "http://api.openweathermap.org/data/2.5/weather?APPID=a45c1e831e27780fcaf1cb59dd223396&units=metric&mode=xml&q=";

    public static String getWeather(String city) throws IOException {
        String urlString = urlAPI + city;
        Document result = Jsoup.connect(urlString).get();
        String temp = result.select("temperature").first().attr("value").split("\\.")[0];
        String weather = result.select("weather").first().attr("value");
        String weatherID = result.select("weather").first().attr("number");
        String vent = result.select("speed").first().attr("value");
        double ventKM = Float.parseFloat(vent)*3.6;
        String humidite = result.select("humidity").first().attr("value");
        String sunrise = result.select("sun").first().attr("rise");
        String sunset = result.select("sun").first().attr("set");
        return temp+"°C,"+weatherID+","+weather+","+vent+","+humidite+","+sunrise+","+sunset;
    }
}
