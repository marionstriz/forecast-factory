package app;

import app.domain.CityWeatherReport;
import app.weather.WeatherReportMachine;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        if (args.length == 0) {
            System.out.println("Must enter city name");
            return;
        }
        String city = args[0];

        WeatherReportMachine machine = new WeatherReportMachine();
        try {
            CityWeatherReport weatherReport = machine.getFullCityWeatherReport(city);
            System.out.println(machine.getWeatherReportAsJson(weatherReport));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
