package app.unit;

import app.domain.CityWeatherReport;
import app.domain.CurrentWeatherReport;
import app.domain.MainDetails;
import app.weather.WeatherReportMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WeatherReportMachineTests {

    private static WeatherReportMachine weatherReportMachine;
    private static CityWeatherReport cityWeatherReport;
    private static CityWeatherReport reportFromJson;
    @BeforeAll
    public static void Initialize() throws JsonProcessingException {
        weatherReportMachine = new WeatherReportMachine();
        MainDetails mainDetails = new MainDetails("Oslo", "33.31,45.21", "Celsius");
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport("01-12-2022", 10, 30, 50);
        cityWeatherReport = new CityWeatherReport(mainDetails, currentWeatherReport);

        String json = weatherReportMachine.getWeatherReportAsJson(cityWeatherReport);
        reportFromJson = new ObjectMapper().readValue(json, CityWeatherReport.class);
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
    public void weatherReportJson_HasSameMainDetailsAsOriginalReport(){
        assertThat(reportFromJson.getMainDetails())
                .isEqualTo(cityWeatherReport.getMainDetails());
    }

    @Test
    public void weatherReportJson_HasSameCurrentWeather_AsOriginalReport(){
        assertThat(reportFromJson.getCurrentWeatherReport())
                .isEqualTo(cityWeatherReport.getCurrentWeatherReport());
    }
}
