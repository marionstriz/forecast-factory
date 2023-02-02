package app.integration;
import app.domain.CityWeatherReport;
import app.domain.CurrentWeather;
import app.domain.MainDetails;
import app.weather.CurrentWeatherHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CurrentWeatherHandlerTests {

    private static MainDetails mainDetails;
    private static CurrentWeather currentWeather;
    private static CityWeatherReport cityWeatherReport;
    private static CurrentWeatherHandler currentWeatherHandler;

    @BeforeAll
    public static void Initialize() {
        String city = "Berlin";
        currentWeatherHandler = new CurrentWeatherHandler();
        cityWeatherReport = currentWeatherHandler.getCityWeatherReport(city);
        mainDetails = cityWeatherReport.getMainDetails();
        currentWeather = cityWeatherReport.getCurrentWeatherReport();
    }

    @Test
    public void givenCityName_ReportCannotBeNull() {
        assertThat(cityWeatherReport).isNotNull();
    }

    @Test
    public void givenCityName_ReportMainDetails_CannotBeNull() {
        assertThat(mainDetails).isNotNull();
    }

    @Test
    public void givenCityName_CurrentWeatherReport_CannotBeNull() {
        assertThat(currentWeather).isNotNull();
    }

    @Test
    public void givenCityName_ReportMainDetails_ShouldReturnDetailsWithCityName() {
        String expectedCity = "Berlin";
        assertThat(mainDetails.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenCityName_ReportMainDetails_ShouldReturnDetailsWithCoordinates() {
        String expectedCoords = "52.52,13.41";
        assertThat(mainDetails.getCoordinates()).isEqualTo(expectedCoords);
    }

    @Test
    public void givenCityNameWithWhitespace_ReportMainDetails_ContainsCityNameWithoutWhitespace() {
        String inputCity = "   Tallinn    ";
        CityWeatherReport weatherReport = currentWeatherHandler.getCityWeatherReport(inputCity);

        String expectedCity = "Tallinn";
        assertThat(weatherReport.getMainDetails().getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenNonExistentCity_GetWeatherReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getCityWeatherReport("Deathstar"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"       ", "", " \t \n \t\t "})
    public void givenEmptyString_GetWeatherReport_ThrowsIllegalArgumentException(String input) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getCityWeatherReport(input));
    }

    @Test
    public void givenNull_GetWeatherReport_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getCityWeatherReport(null));
    }

    @Test
    public void currentWeatherDto_HasTemperatureWithinRealisticCelsiusLimits() {
        double currentTemp = currentWeather.getWeather().getTemperature();
        assertThat(currentTemp).isLessThan(60);
        assertThat(currentTemp).isGreaterThan(-90);
    }

    @Test
    public void currentWeatherDto_HasPressureNotDefaultValue() {
        double currentPressure = currentWeather.getWeather().getPressure();
        assertThat(currentPressure).isNotZero();
    }

    @Test
    public void currentWeatherDto_HasHumidityNotDefaultValue() {
        double currentHumidity = currentWeather.getWeather().getHumidity();
        assertThat(currentHumidity).isNotZero();
    }
}
