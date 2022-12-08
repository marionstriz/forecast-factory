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
                new RangeForecastDto("07-12-2022 03:00:00",
                        new WeatherInfoDto(16, 1000, 50)),
                new RangeForecastDto("07-12-2022 06:00:00",
                        new WeatherInfoDto(20, 1004, 36)),
                new RangeForecastDto("08-12-2022 12:00:00",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("08-12-2022 15:00:00",
                        new WeatherInfoDto(26, 1000, 40)),
                new RangeForecastDto("09-12-2022 15:00:00",
                        new WeatherInfoDto(25, 1000, 44))));

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
    @CsvSource({"18, 0",
                "23, 1" ,
                "25, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectTempValue(double expected, int index) {
        double temperatureInReport = weatherForecastReport.getForecast().get(index).getDetails().getTemperature();

        assertThat(temperatureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1002, 0",
            "1001, 1" ,
            "1000, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectPressureValue(double expected, int index) {
        double pressureInReport = weatherForecastReport.getForecast().get(index).getDetails().getPressure();

        assertThat(pressureInReport).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"68, 0",
            "40, 1" ,
            "44, 2"})
    public void givenCityName_getWeatherForecastReport_WeatherReportHasCorrectHumidityValue(double expected, int index) {
        double humidityInReport = weatherForecastReport.getForecast().get(index).getDetails().getHumidity();

        assertThat(humidityInReport).isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource({
            "07-12-2022, 0",
            "08-12-2022, 1",
            "09-12-2022, 2"
    })
    public void givenCityName_getWeatherForecastReport_ReportsAreSortedCorrectly(String expectedDate, int index) {
        WeatherReport oneDayReport = weatherForecastReport.getForecast().get(index);
        assertThat(oneDayReport.getDate()).isEqualTo(expectedDate);
    }


}
