package app.mock;

import app.api.WeatherApi;
import app.domain.MainDetails;
import app.dto.CoordinatesDto;
import app.dto.CurrentWeatherDto;
import app.dto.WeatherInfoDto;
import app.weather.CurrentWeatherHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CurrentWeatherHandlerTests {

    @Mock
    private WeatherApi weatherApi;

    private MainDetails mainDetails;

    @BeforeEach
    public void Initialize() {
        String city = "Tokyo";

        CurrentWeatherDto currentWeatherDtoStub = new CurrentWeatherDto();
        currentWeatherDtoStub.setCity(city);

        CoordinatesDto coordinatesDtoStub = new CoordinatesDto();
        coordinatesDtoStub.setLon(139.6917);
        coordinatesDtoStub.setLat(35.6895);
        currentWeatherDtoStub.setCoordinates(coordinatesDtoStub);
        currentWeatherDtoStub.setWeatherInfoDto(new WeatherInfoDto());

        Mockito.when(weatherApi.getCurrentWeatherDtoAboutCity(city)).thenReturn(currentWeatherDtoStub);

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
