package polytech.its.mobileapp.Weather;

/**
 * @author: Abdelkarim Andolerzak
 */

public class WeatherData {
    String city;
    String temperature;
    String iconName;
    String description;
    String wind;
    String humidity;
    String sunrise;
    String sunset;

    public WeatherData(String city, String temperature, String iconName, String description, String wind, String humidity, String sunset, String sunrise) {
        this.city = city;
        this.temperature = temperature;
        this.iconName = iconName;
        this.description = description;
        this.wind = wind;
        this.humidity = humidity;
        this.sunset = sunset;
        this.sunrise = sunrise;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIconName() {
        return iconName;
    }

    public String getDescription() {
        return description;
    }

    public String getWind() {
        return wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }


    public static WeatherData getInstance(String weatherInfo) {
        String[] infos = weatherInfo.split(",");
        return new WeatherData(infos[0], infos[1], infos[2], infos[3], infos[4], infos[5], infos[6], infos[7]);
    }
}
