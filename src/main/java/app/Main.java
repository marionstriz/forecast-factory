package app;

import app.domain.CityWeatherReport;
import app.helpers.UserInputUtils;
import app.io.FileWriter;
import app.weather.WeatherReportMachine;
import org.apache.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.error("Please enter city or file name");
            return;
        }
        String input = String.join(" ", args);

        createReportsFromInput(input);
    }

    public static void createReportsFromInput(String input) {
        createReportsFromInputGivenPath(input, null);
    }

    public static void createReportsFromInputGivenPath(String input, String path) {
        WeatherReportMachine machine = new WeatherReportMachine();
        FileWriter writer = path == null ? new FileWriter() : new FileWriter(path);

        List<String> cities;
        try {
            cities = UserInputUtils.getCitiesFromInput(input);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return;
        }
        for (String city : cities) {
            try {
                CityWeatherReport weatherReport = machine.getFullCityWeatherReport(city);

                writer.writeReportFile(weatherReport);
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
