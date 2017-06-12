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
        Document test = Jsoup.connect(urlString).get();
        String temp = test.select("temperature").first().attr("value");
        String weather = test.select("weather").first().attr("value");
        return temp+","+weather;
    }
}
