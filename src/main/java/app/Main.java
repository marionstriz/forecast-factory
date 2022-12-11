package app;

import app.domain.CityWeatherReport;
import app.weather.WeatherReportMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) throws JsonProcessingException {
        org.apache.log4j.BasicConfigurator.configure();
        if (args.length == 0) {
            logger.error("Must enter city name");
            return;
        }
        String city = args[0];

        WeatherReportMachine machine = new WeatherReportMachine();
        try {
            CityWeatherReport weatherReport = machine.getFullCityWeatherReport(city);
            System.out.println(machine.getWeatherReportAsJson(weatherReport));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }
}
