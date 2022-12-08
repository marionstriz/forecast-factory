package app.unit;

import app.domain.*;
import app.weather.WeatherReportMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WeatherReportMachineTests {

    private static WeatherReportMachine weatherReportMachine;
    private static CityWeatherReport cityWeatherReport;

    @BeforeAll
    public static void Initialize() {
        weatherReportMachine = new WeatherReportMachine();
        MainDetails mainDetails = new MainDetails("Oslo", "33.31,45.21", "Celsius");
        WeatherReport weatherReport = new WeatherReport("01-12-2022",
                new WeatherReportDetails(10, 30, 50));
        cityWeatherReport = new CityWeatherReport(mainDetails, weatherReport);
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
        String actualJson = weatherReportMachine.getWeatherReportAsJson(cityWeatherReport);

        String expectedJsonReport = """
                {
                  "mainDetails" : {
                    "city" : "Oslo",
                    "coordinates" : "33.31,45.21",
                    "temperatureUnit" : "Celsius"
                  },
                  "weatherReport" : {
                    "date" : "01-12-2022",
                    "temperature" : 10.0,
                    "pressure" : 30,
                    "humidity" : 50
                  }
                }""";
        assertThat(actualJson).isEqualTo(expectedJsonReport);
    }
}
