package app.integration;

import app.api.WeatherApi;
import app.dto.CurrentWeatherDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherApiTests {

    private static WeatherApi weatherApi;
    private static CurrentWeatherDto currentWeatherDto;

    @BeforeAll
    public static void initialize() {
        weatherApi = new WeatherApi();

        String city = "Tallinn";
        currentWeatherDto = weatherApi.getCurrentWeatherDtoAboutCity(city);
    }

    @Test
    public void currentWeatherDto_ContainsCityName() {
        String expectedCity = "Tallinn";
        assertThat(currentWeatherDto.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void currentWeatherDto_ContainsCoords() {
        double expectedLon = 24.7535;
        double expectedLat = 59.437;
        assertThat(currentWeatherDto.getCoordinates())
                .extracting("lat", "lon")
                .contains(expectedLat, expectedLon);
    }

    @Test
    public void currentWeatherDto_HasWeatherInfo() {
        assertThat(currentWeatherDto.getWeatherInfoDto()).isNotNull();
    }

    @Test
    public void currentWeatherDto_HasTemperatureWithinRealisticCelsiusLimits() {
        // uses highest/lowest recorded temperature values plus a few degrees.
        double currentTemp = currentWeatherDto.getWeatherInfoDto().getTemp();
        assertThat(currentTemp).isLessThan(60);
        assertThat(currentTemp).isGreaterThan(-90);
    }
}
