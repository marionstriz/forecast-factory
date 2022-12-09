package app.mock;

import app.api.WeatherApi;
import app.domain.WeatherForecastReport;
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

    private WeatherForecastReport weatherForecastReport;

    @BeforeEach
    public void Initialize() {
        String city = "Tokyo";

        ForecastDto forecastDtoStub = new ForecastDto();

        forecastDtoStub.setRangeForecastDtos(List.of(
                new RangeForecastDto("2022-12-07 03:00:00",
                        new WeatherInfoDto(16, 1000, 50)),
                new RangeForecastDto("2022-12-07 06:00:00",
                        new WeatherInfoDto(20, 1004, 36)),
                new RangeForecastDto("2022-12-08 12:00:00",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("2022-12-08 15:00:00",
                        new WeatherInfoDto(26, 1000, 40)),
                new RangeForecastDto("2022-12-09 15:00:00",
                        new WeatherInfoDto(24, 1003, 46)),
                new RangeForecastDto("2022-12-10 15:00:00",
                        new WeatherInfoDto(25, 1020, 49)),
                new RangeForecastDto("2022-12-11 15:00:00",
                        new WeatherInfoDto(29, 1090, 50))));

        Mockito.when(weatherApi.getForecastDtoAboutCity(city)).thenReturn(forecastDtoStub);

        weatherForecastReport = new WeatherForecastHandler(weatherApi).getWeatherForecastReport(city);
    }

    @Test
    public void givenCityName_getWeatherForecastReport_ShouldReturnForecastOfThreeDays() {
        assertThat(weatherForecastReport.getForecast().size()).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void givenCityName_getWeatherForecastReport_WeatherReportInForecastIsNotNull(int index) {
        assertThat(weatherForecastReport.getForecast().get(index)).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"23, 0",
                "24, 1" ,
                "25, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectTempValue(double expected, int index) {
        double temperatureInReport = weatherForecastReport.getForecast().get(index).getDetails().getTemperature();

        assertThat(temperatureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1001, 0",
            "1003, 1" ,
            "1020, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectPressureValue(double expected, int index) {
        double pressureInReport = weatherForecastReport.getForecast().get(index).getDetails().getPressure();

        assertThat(pressureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"40, 0",
            "46, 1" ,
            "49, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectHumidityValue(double expected, int index) {
        double humidityInReport = weatherForecastReport.getForecast().get(index).getDetails().getHumidity();

        assertThat(humidityInReport).isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource({
            "08-12-2022, 0",
            "09-12-2022, 1",
            "10-12-2022, 2"
    })
    public void givenCityName_getWeatherForecastReport_ReportsAreSortedCorrectly(String expectedDate, int index) {
        WeatherReport oneDayReport = weatherForecastReport.getForecast().get(index);
        assertThat(oneDayReport.getDate()).isEqualTo(expectedDate);
    }


}
