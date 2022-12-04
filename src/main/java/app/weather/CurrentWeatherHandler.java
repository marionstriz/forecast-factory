package app.weather;

import app.api.WeatherApi;
import app.domain.CurrentWeatherReport;

import java.util.Date;

public class CurrentWeatherHandler {

    private WeatherApi weatherApi;

    public CurrentWeatherHandler() {
        weatherApi = new WeatherApi();
    }

    public CurrentWeatherHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public CurrentWeatherReport getCurrentWeatherReport(String city) {
        return new CurrentWeatherReport();
    }

    public String getDateInStringFormat(Date date) {
        return "";
    }
}
