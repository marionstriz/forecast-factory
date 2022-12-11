package app.unit;

import app.domain.*;
import app.weather.WeatherReportMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WeatherReportMachineTests {

    private static WeatherReportMachine weatherReportMachine;

    @BeforeAll
    public static void Initialize() {
        weatherReportMachine = new WeatherReportMachine();
    }

    @Test
    public void givenNonExistentCity_WhenGettingFullCityWeatherReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherReportMachine.getFullCityWeatherReport("Deathstar"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"       ", "", " \t \n \t\t "})
    public void givenEmptyString_WhenGettingFullCityWeatherReport_ThrowsIllegalArgumentException(String input) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherReportMachine.getFullCityWeatherReport(input));
    }

    @Test
    public void givenNull_WhenGettingFullCityWeatherReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherReportMachine.getFullCityWeatherReport(null));
    }

    @Test
    public void weatherReportJson_HasSameMainDetailsAsOriginalReport() throws JsonProcessingException {
        MainDetails mainDetails = new MainDetails("Oslo", "33.31,45.21", "Celsius");
        CurrentWeather currentWeather = new CurrentWeather("01-12-2022",
                new WeatherReportDetails(10, 50, 1000));
        List<WeatherReport> forecast = List.of(
                new WeatherReport("02-12-2022", new WeatherReportDetails(-2.5, 66, 1100)),
                new WeatherReport("03-12-2022", new WeatherReportDetails(0, 50, 1100)),
                new WeatherReport("04-12-2022", new WeatherReportDetails(-8.64, 26, 1300)));

        CityWeatherReport cityWeatherReport =
                new CityWeatherReport(mainDetails, currentWeather, forecast);

        String actualJson = weatherReportMachine.getWeatherReportAsJson(cityWeatherReport);

        String expectedJsonReport = """
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
        assertThat(actualJson).isEqualTo(expectedJsonReport);
    }
}
