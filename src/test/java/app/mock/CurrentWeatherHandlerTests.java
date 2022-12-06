package app.mock;

import app.api.WeatherApi;
import app.domain.MainDetails;
import app.dto.CoordinatesDto;
import app.dto.CurrentWeatherDto;
import app.weather.CurrentWeatherHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentWeatherHandlerTests {

    @Mock
    private static WeatherApi weatherApi;

    private static MainDetails mainDetails;

    @BeforeAll
    public static void Initialize() {
        String city = "Tokyo";

        CurrentWeatherDto currentWeatherDtoStub = new CurrentWeatherDto();
        currentWeatherDtoStub.setCity(city);

        CoordinatesDto coordinatesDtoStub = new CoordinatesDto();
        coordinatesDtoStub.setLon(139.6917);
        coordinatesDtoStub.setLat(35.6895);
        currentWeatherDtoStub.setCoordinates(coordinatesDtoStub);

        when(weatherApi.getCurrentWeatherDtoAboutCity(anyString())).thenReturn(currentWeatherDtoStub);

        mainDetails = new CurrentWeatherHandler(weatherApi).getCityWeatherReport(city).getMainDetails();
    }

    @Test
    public void givenCityName_getWeatherReport_ShouldReturDetails_WithCityName() {
        String expectedCity = "Tokyo";
        assertThat(mainDetails.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenCityName_getMainDetails_ShouldReturnDetails_WithCoordinates() {
        String expectedCoordinates = "35.69,139.69";
        assertThat(mainDetails.getCoordinates()).isEqualTo(expectedCoordinates);
    }

    @Test
    public void givenCityName_getMainDetails_ShouldReturnDetails_WithCelsiusAsUnit() {
        String expectedUnit = "Celsius";
        assertThat(mainDetails.getTemperatureUnit()).isEqualTo(expectedUnit);
    }
}
