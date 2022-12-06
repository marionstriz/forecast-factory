package app.weather;

import app.domain.CityWeatherReport;

public class WeatherReportMachine {

    public CityWeatherReport getFullCityWeatherReport(String city) {
        // call handlers with the city name
        // create full weather report with received objects from handler
        return new CityWeatherReport();
    }

    public String getWeatherReportAsJson(CityWeatherReport report) {
        return "";
    }
}
