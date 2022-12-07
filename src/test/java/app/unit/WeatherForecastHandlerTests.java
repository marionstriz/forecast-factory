package app.unit;

import app.domain.WeatherForecastReport;
import app.domain.WeatherReport;
import app.dto.ForecastDto;
import app.dto.RangeForecastDto;
import app.dto.WeatherInfoDto;
import app.weather.WeatherForecastHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherForecastHandlerTests {
    private static WeatherForecastHandler weatherForecastHandler;

    private static ForecastDto forecastDto;

    private static WeatherReport weatherReport;

    @BeforeAll
    public static void Initialize() {
        weatherForecastHandler = new WeatherForecastHandler();

        forecastDto.setRangeForecastDtos(List.of(
                new RangeForecastDto("2-11-2022",
                        new WeatherInfoDto(17, 1000, 50)),
                new RangeForecastDto("2-11-2022",
                        new WeatherInfoDto(20, 1003, 35)),
                new RangeForecastDto("3-11-2022",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("4-11-2022",
                        new WeatherInfoDto(25, 1000, 40))));

        List<WeatherInfoDto> weatherInfoDtos = List.of(
                new WeatherInfoDto(2, 100, 10),
                new WeatherInfoDto(9, 150, 5),
                new WeatherInfoDto(4, 120, 15));

        weatherReport = weatherForecastHandler.calculateOneDayReport(weatherInfoDtos);
    }

    @Test
    public void WhenGivenDate_GetDateInStringFormat_ShouldReturnInCorrectFormat() {
        String expectedStringDate = "30-12-2022";
        String date = "2022-12-12 15:00:00";
        assertThat(weatherForecastHandler.getDateInCorrectStringFormat(date))
                .isEqualTo(expectedStringDate);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAverageTempCorrectlyForOneWeatherReport() {
        Double AverageTemp = weatherReport.getDetails().getTemperature();
        Double expectedAverageTemp = 5.0;
        assertThat(AverageTemp).isEqualTo(expectedAverageTemp);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAveragePressureCorrectlyForOneWeatherReport() {
        int AveragePressure = weatherReport.getDetails().getPressure();
        int expectedAveragePressure = 185;
        assertThat(AveragePressure).isEqualTo(expectedAveragePressure);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAverageHumidityCorrectlyForOneWeatherReport() {
        int averageHumidity = weatherReport.getDetails().getHumidity();
        int expectedHumidityAverage = 10;
        assertThat(averageHumidity).isEqualTo(expectedHumidityAverage);
    }

    @Test
    public void WhenGivenForecastDto_FilterToMapByDate_GetsResultOfThreeKeyAndValuePairs() {
        Map<String, WeatherInfoDto> map
                = weatherForecastHandler.filterWeatherInfoDtosToMapByDate(forecastDto);
        assertThat(map).hasSize(3);
    }

    @Test
    public void WhenGivenForecastDto_FilterToMapByDate_HasCorrectKeysInMap() {
        Map<String, WeatherInfoDto> map
                = weatherForecastHandler.filterWeatherInfoDtosToMapByDate(forecastDto);
        assertThat(map).containsKeys("2-11-2022", "3-11-2022", "4-11-2022");
    }

}
