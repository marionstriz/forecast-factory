package app.weather;

import app.domain.FullWeatherReport;

public class WeatherReportMachine {

    public FullWeatherReport getWeatherReportAboutCity(String city) {
        // call handlers with the city name
        // create full weather report with received objects from handler
        return new FullWeatherReport();
    }

    public String getWeatherReportAsJson(FullWeatherReport report) {
        return "";
    }
}
