package app.unit;

import app.domain.*;
import app.io.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileWriterTests {

    private static FileWriter fileWriter;

    @BeforeAll
    public static void initialize(@TempDir Path tempDir) {
        fileWriter = new FileWriter(tempDir.toString());
    }

    @Test
    public void WhenGivenReport_SavesCorrectJsonForm_ToCorrectFile() throws IOException {
        MainDetails mainDetails = new MainDetails("Oslo", "33.31,45.21", "Celsius");
        CurrentWeather currentWeather = new CurrentWeather("01-12-2022",
                new WeatherReportDetails(10, 50, 1000));
        List<WeatherReport> forecast = List.of(
                new WeatherReport("02-12-2022", new WeatherReportDetails(-2.5, 66, 1100)),
                new WeatherReport("03-12-2022", new WeatherReportDetails(0, 50, 1100)),
                new WeatherReport("04-12-2022", new WeatherReportDetails(-8.64, 26, 1300)));
        CityWeatherReport report = new CityWeatherReport(mainDetails, currentWeather, forecast);

        fileWriter.writeReportFile(report);

        Path expectedPath = fileWriter.getDestinationDir().resolve("Oslo.txt");
        String expectedJson = """
                {
                  "mainDetails" : {
                    "city" : "Oslo",
                    "coordinates" : "33.31,45.21",
                    "temperatureUnit" : "Celsius"
                  },
                  "currentWeatherReport" : {
                    "date" : "01-12-2022",
                    "temperature" : 10.0,
                    "humidity" : 50,
                    "pressure" : 1000
                  },
                  "forecastReport" : [ {
                    "date" : "02-12-2022",
                    "weather" : {
                      "temperature" : -2.5,
                      "humidity" : 66,
                      "pressure" : 1100
                    }
                  }, {
                    "date" : "03-12-2022",
                    "weather" : {
                      "temperature" : 0.0,
                      "humidity" : 50,
                      "pressure" : 1100
                    }
                  }, {
                    "date" : "04-12-2022",
                    "weather" : {
                      "temperature" : -8.64,
                      "humidity" : 26,
                      "pressure" : 1300
                    }
                  } ]
                }""";

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(Files.readString(expectedPath)).isEqualTo(expectedJson);
    }
}
