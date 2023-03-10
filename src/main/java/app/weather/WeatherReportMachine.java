package app.weather;

import app.domain.CityWeatherReport;
import app.domain.WeatherReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

public class WeatherReportMachine {

    public CityWeatherReport getFullCityWeatherReport(String city) {
        CityWeatherReport cityWeatherReport = new CurrentWeatherHandler().getCityWeatherReport(city);
        List<WeatherReport> forecast = new WeatherForecastHandler().getThreeDayForecastAboutCity(city);

        cityWeatherReport.setForecastReport(forecast);
        return cityWeatherReport;
    }

    public String getWeatherReportAsJson(CityWeatherReport report) {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return om.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert report to json");
        }
    }
}
