package app.integration;

import app.domain.WeatherReport;
import app.weather.WeatherForecastHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WeatherForecastHandlerTests {

    private static WeatherForecastHandler weatherForecastHandler;
    private static List<WeatherReport> weatherForecast;

    @BeforeAll
    public static void Initialize(){
        String city = "Stockholm";
        weatherForecastHandler = new WeatherForecastHandler();
        weatherForecast = weatherForecastHandler.getThreeDayForecastAboutCity(city);
    }

    @Test
    public void givenCityName_WeatherForecastReportCannotBeNull() {
        assertThat(weatherForecast).isNotNull();
    }

    @Test
    public void givenCityName_WeatherForecastHasThreeDayReport() {
        assertThat(weatherForecast.size()).isEqualTo(3);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void givenCityName_getWeatherForecastReport_WeatherReportInForecastIsNotNull(int index) {
        assertThat(weatherForecast.get(index)).isNotNull();
    }

    @Test
    public void givenNonExistentCity_GetWeatherForecastReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherForecastHandler.getThreeDayForecastAboutCity("Deathstar"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"       ", "", " \t \n \t\t "})
    public void givenEmptyString_GetWeatherForecastReport_ThrowsIllegalArgumentException(String input) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherForecastHandler.getThreeDayForecastAboutCity(input));
    }

    @Test
    public void givenNull_GetWeatherForecastReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherForecastHandler.getThreeDayForecastAboutCity(null));
    }

}

