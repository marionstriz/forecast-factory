package app.weather;

import app.api.WeatherApi;
import app.domain.MainDetails;

public class MainDetailsHandler {

    private WeatherApi weatherApi;
    private String celsiusUnit = "Celsius";

    public MainDetailsHandler() {
        weatherApi = new WeatherApi();
    }

    public MainDetailsHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public MainDetails getMainDetailsAboutCity(String city) {
        return new MainDetails();
    }

    public String getStringFormatCoordinates(double lat, double lon) {
        return "";
    }
}
