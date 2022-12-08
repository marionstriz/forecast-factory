package app.unit;

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

        forecastDto = new ForecastDto();

        forecastDto.setRangeForecastDtos(List.of(
                new RangeForecastDto("02-11-2022",
                        new WeatherInfoDto(17, 1000, 50)),
                new RangeForecastDto("03-11-2022",
                        new WeatherInfoDto(20, 1003, 35)),
                new RangeForecastDto("04-11-2022",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("05-11-2022",
                        new WeatherInfoDto(25, 1000, 40))));

        List<WeatherInfoDto> weatherInfoDtos = List.of(
                new WeatherInfoDto(2, 100, 10),
                new WeatherInfoDto(9, 200, 5),
                new WeatherInfoDto(4, 120, 15));

        weatherReport = weatherForecastHandler.calculateOneDayReport(weatherInfoDtos);
    }

    @Test
    public void WhenGivenDate_GetDateInStringFormat_ShouldReturnInCorrectFormat() {
        String date = "2022-12-30 15:00:00";

        String actualDate = weatherForecastHandler.getDateInCorrectStringFormat(date);
        String expectedStringDate = "30-12-2022";

        assertThat(actualDate).isEqualTo(expectedStringDate);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAverageTempCorrectlyForOneWeatherReport() {
        Double actualAverageTemp = weatherReport.getDetails().getTemperature();
        Double expectedAverageTemp = 5.0;
        assertThat(actualAverageTemp).isEqualTo(expectedAverageTemp);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAveragePressureCorrectlyForOneWeatherReport() {
        int actualAveragePressure = weatherReport.getDetails().getPressure();
        int expectedAveragePressure = 140;
        assertThat(actualAveragePressure).isEqualTo(expectedAveragePressure);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAverageHumidityCorrectlyForOneWeatherReport() {
        int actualAverageHumidity = weatherReport.getDetails().getHumidity();
        int expectedHumidityAverage = 10;
        assertThat(actualAverageHumidity).isEqualTo(expectedHumidityAverage);
    }

    @Test
    public void WhenGivenForecastDto_FilterToMapByDate_GetsResultOfThreeKeyAndValuePairs() {
        Map<String, List<WeatherInfoDto>> map
                = weatherForecastHandler.filterWeatherInfoDtosToMapByDate(forecastDto);
        assertThat(map).hasSize(4);
    }

    @Test
    public void WhenGivenForecastDto_FilterToMapByDate_HasCorrectKeysInMap() {
        Map<String, List<WeatherInfoDto>> map
                = weatherForecastHandler.filterWeatherInfoDtosToMapByDate(forecastDto);
        assertThat(map).containsKeys("02-11-2022", "03-11-2022", "04-11-2022", "05-11-2022");
    }

}
