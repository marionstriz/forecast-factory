package app;

import app.domain.CityWeatherReport;
import app.helpers.UserInputUtils;
import app.io.FileWriter;
import app.weather.WeatherReportMachine;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.error("Please enter city or file name");
            return;
        }
        String input = String.join(" ", args);
        WeatherReportMachine machine = new WeatherReportMachine();
        FileWriter writer = new FileWriter();

        try {
            String city = UserInputUtils.getFileContentsOrInput(input);
            CityWeatherReport weatherReport = machine.getFullCityWeatherReport(city);

            writer.writeReportFile(weatherReport);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }

    public static void createReportsFromInput(String input) {
        //getCitiesFromInput
        //for loop over cities to create reports and files
    }
}
