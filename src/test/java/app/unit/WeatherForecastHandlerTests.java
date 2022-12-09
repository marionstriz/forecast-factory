package app.unit;

import app.domain.WeatherReport;
import app.domain.WeatherReportDetails;
import app.dto.ForecastDto;
import app.dto.RangeForecastDto;
import app.dto.WeatherInfoDto;
import app.weather.WeatherForecastHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherForecastHandlerTests {
    private static WeatherForecastHandler weatherForecastHandler;
    private static WeatherReportDetails weatherReportDetails;
    private static LinkedHashMap<String, List<WeatherInfoDto>> forecastMap;

    @BeforeAll
    public static void Initialize() {
        weatherForecastHandler = new WeatherForecastHandler();

        ForecastDto forecastDto = new ForecastDto();

        forecastDto.setRangeForecastDtos(List.of(
                new RangeForecastDto("2022-11-01 15:00:00",
                        new WeatherInfoDto(25, 1000, 40)),
                new RangeForecastDto("2022-11-02 03:00:00",
                        new WeatherInfoDto(17, 1000, 50)),
                new RangeForecastDto("2022-11-02 06:00:00",
                        new WeatherInfoDto(20, 1003, 35)),
                new RangeForecastDto("2022-11-03 12:00:00",
                        new WeatherInfoDto(20, 1002, 40)),
                new RangeForecastDto("2022-11-04 15:00:00",
                        new WeatherInfoDto(25, 1000, 40)),
                new RangeForecastDto("2022-11-05 15:00:00",
                        new WeatherInfoDto(25, 1000, 40))));

        List<WeatherInfoDto> weatherInfoDtos = List.of(
                new WeatherInfoDto(2, 100, 10),
                new WeatherInfoDto(9, 200, 5),
                new WeatherInfoDto(4, 120, 15));

        forecastMap = weatherForecastHandler.filterWeatherInfoDtosToMapByDate(forecastDto);

        weatherReportDetails = weatherForecastHandler.calculateOneDayReportDetails(weatherInfoDtos);
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
        Double actualAverageTemp = weatherReportDetails.getTemperature();
        Double expectedAverageTemp = 5.0;
        assertThat(actualAverageTemp).isEqualTo(expectedAverageTemp);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAveragePressureCorrectlyForOneWeatherReport() {
        int actualAveragePressure = weatherReportDetails.getPressure();
        int expectedAveragePressure = 140;
        assertThat(actualAveragePressure).isEqualTo(expectedAveragePressure);
    }

    @Test
    public void WhenGivenWeatherInfoDtosOfSameDate_CalculatesAverageHumidityCorrectlyForOneWeatherReport() {
        int actualAverageHumidity = weatherReportDetails.getHumidity();
        int expectedHumidityAverage = 10;
        assertThat(actualAverageHumidity).isEqualTo(expectedHumidityAverage);
    }

    @Test
    public void WhenGivenForecastDto_FilterToMapByDate_GetsResultOfThreeKeyAndValuePairs() {
        assertThat(forecastMap).hasSize(5);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "01-11-2022",
            "02-11-2022",
            "03-11-2022",
            "04-11-2022",
            "05-11-2022"
    })
    public void WhenGivenForecastDto_FilterToMapByDate_ContainsKey(String existingKey) {
        assertThat(forecastMap).containsKey(existingKey);
    }

    @ParameterizedTest
    @CsvSource({
            "02-11-2022, 2",
            "03-11-2022, 1",
            "04-11-2022, 1"
    })
    public void WhenGivenForecastDto_FilterToMapByDate_HasCorrectNumberOfRangeForecasts(String key, int listSize) {
        assertThat(forecastMap.get(key)).hasSize(listSize);
    }

}
