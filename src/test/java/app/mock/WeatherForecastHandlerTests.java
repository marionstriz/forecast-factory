package app.mock;

import app.api.WeatherApi;
import app.domain.WeatherReport;
import app.dto.*;
import app.weather.WeatherForecastHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WeatherForecastHandlerTests {
    @Mock
    private WeatherApi weatherApi;

    private List<WeatherReport> weatherForecast;

    @BeforeEach
    public void Initialize() {
        String city = "Tokyo";

        ForecastDto forecastDtoStub = new ForecastDto();

        forecastDtoStub.setRangeForecastDtos(List.of(
                new RangeForecastDto("2022-11-01 15:00:00",
                        new WeatherInfoDto(25, 1000, 40)),
                new RangeForecastDto("2022-11-02 00:00:00",
                        new WeatherInfoDto(-5, 1100, 80)),
                new RangeForecastDto("2022-11-02 03:00:00",
                        new WeatherInfoDto(17, 1000, 50)),
                new RangeForecastDto("2022-11-02 06:00:00",
                        new WeatherInfoDto(20, 1003, 35)),
                new RangeForecastDto("2022-11-03 00:00:00",
                        new WeatherInfoDto(5, 1300, 60)),
                new RangeForecastDto("2022-11-03 12:00:00",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("2022-11-04 00:00:00",
                        new WeatherInfoDto(11, 1506, 95)),
                new RangeForecastDto("2022-11-04 15:00:00",
                        new WeatherInfoDto(25, 1000, 40)),
                new RangeForecastDto("2022-11-05 00:00:00",
                        new WeatherInfoDto(25, 1000, 40))));

        Mockito.when(weatherApi.getForecastDtoAboutCity(city)).thenReturn(forecastDtoStub);

        weatherForecast = new WeatherForecastHandler(weatherApi).getThreeDayForecastAboutCity(city);
    }

    @Test
    public void givenCityName_getWeatherForecastReport_ShouldReturnForecastOfThreeDays() {
        assertThat(weatherForecast.size()).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void givenCityName_getWeatherForecastReport_WeatherReportInForecastIsNotNull(int index) {
        assertThat(weatherForecast.get(index)).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"10.67, 0",
                "12.5, 1" ,
                "18, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectTempValue(double expected, int index) {
        double temperatureInReport = weatherForecast.get(index).getDetails().getTemperature();

        assertThat(temperatureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1034, 0",
            "1151, 1" ,
            "1253, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectPressureValue(double expected, int index) {
        double pressureInReport = weatherForecast.get(index).getDetails().getPressure();

        assertThat(pressureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"55, 0",
            "50, 1" ,
            "67, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectHumidityValue(double expected, int index) {
        double humidityInReport = weatherForecast.get(index).getDetails().getHumidity();

        assertThat(humidityInReport).isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource({
            "02-11-2022, 0",
            "03-11-2022, 1",
            "04-11-2022, 2"
    })
    public void givenCityName_getWeatherForecastReport_ReportsAreSortedCorrectly(String expectedDate, int index) {
        WeatherReport oneDayReport = weatherForecast.get(index);
        assertThat(oneDayReport.getDate()).isEqualTo(expectedDate);
    }

}
