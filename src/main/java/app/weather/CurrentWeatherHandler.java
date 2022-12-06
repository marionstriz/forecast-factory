package app.weather;

import app.api.WeatherApi;
import app.domain.CityWeatherReport;

import java.util.Date;

public class CurrentWeatherHandler {

    private WeatherApi weatherApi;

    public CurrentWeatherHandler() {
        weatherApi = new WeatherApi();
    }

    public CurrentWeatherHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public CityWeatherReport getCityWeatherReport(String city) {
        return new CityWeatherReport();
    }

    public String getDateInStringFormat(Date date) {
        return "";
    }

    public String getStringFormatCoordinates(double lat, double lon) {
        return "";
    }
}
