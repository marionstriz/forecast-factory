package app.weather;

import app.domain.CityWeatherReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class WeatherReportMachine {

    public CityWeatherReport getFullCityWeatherReport(String city) {

        return new CurrentWeatherHandler().getCityWeatherReport(city);
    }

    public String getWeatherReportAsJson(CityWeatherReport report) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return om.writeValueAsString(report);
    }
}
