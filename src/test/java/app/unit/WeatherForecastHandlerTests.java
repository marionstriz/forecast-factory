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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class WeatherForecastHandlerTests {
    private static WeatherForecastHandler weatherForecastHandler;
    private static WeatherReportDetails weatherReportDetails;
    private static Map<String, List<WeatherInfoDto>> forecastMapStartingToday;

    private static ForecastDto forecastDtoFromTomorrow;
    private static Map<String, List<WeatherInfoDto>> forecastMapStartingTomorrow;

    @BeforeAll
    public static void Initialize() {
        weatherForecastHandler = new WeatherForecastHandler();

        ForecastDto forecastDtoWtihToday = new ForecastDto();
        forecastDtoFromTomorrow = new ForecastDto();

        forecastDtoWtihToday.setRangeForecastDtos(List.of(
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

        forecastDtoFromTomorrow.setRangeForecastDtos(List.of(
                new RangeForecastDto("2018-09-15 00:00:00",
                        new WeatherInfoDto(25, 1000, 40)),
                new RangeForecastDto("2018-09-16 00:00:00",
                        new WeatherInfoDto(5, 1100, 80)),
                new RangeForecastDto("2018-09-17 00:00:00",
                        new WeatherInfoDto(2, 1000, 30)),
                new RangeForecastDto("2018-09-18 00:00:00",
                        new WeatherInfoDto(10, 1000, 75))));

        List<WeatherInfoDto> weatherInfoDtos = List.of(
                new WeatherInfoDto(2, 100, 10),
                new WeatherInfoDto(9, 200, 5),
                new WeatherInfoDto(4, 120, 15));

        forecastMapStartingToday = weatherForecastHandler.filterThreeFullDaysWeatherInfosToMapByDate(forecastDtoWtihToday);
        forecastMapStartingTomorrow = weatherForecastHandler.filterThreeFullDaysWeatherInfosToMapByDate(forecastDtoFromTomorrow);

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
        assertThat(forecastMapStartingToday).hasSize(3);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "02-11-2022",
            "03-11-2022",
            "04-11-2022"
    })
    public void WhenGivenForecastDto_FilterToMapByDate_ContainsKey(String existingKey) {
        assertThat(forecastMapStartingToday).containsKey(existingKey);
    }

    @ParameterizedTest
    @CsvSource({
            "02-11-2022, 3",
            "03-11-2022, 2",
            "04-11-2022, 2"
    })
    public void WhenGivenForecastDto_FilterToMapByDate_HasCorrectNumberOfRangeForecasts(String key, int listSize) {
        assertThat(forecastMapStartingToday.get(key)).hasSize(listSize);
    }

    @Test
    public void WhenGivenForecastDto_WithFirstForecastAtMidnight_FilterToMapIncludesIt() {
        assertThat(forecastMapStartingTomorrow.get("15-09-2018")).isNotNull();
    }

    @Test
    public void WhenGivenMap_WithFirstForecastAtMidnight_FinalReportIncludesIt() {
        List<WeatherReport> forecast = weatherForecastHandler.extractThreeDayReportFromForecastDto(forecastDtoFromTomorrow);

        String firstForecastDate = forecast.get(0).getDate();
        String expectedDate = "15-09-2018";

        assertThat(firstForecastDate).isEqualTo(expectedDate);
    }

    @Test
    public void WhenExtractingThreeDayForecast_ThrowsErrorIfThreeDaysNotPossible() {
        ForecastDto dto = new ForecastDto();
        dto.setRangeForecastDtos(List.of(new RangeForecastDto("24-12-2022 03:00:00", new WeatherInfoDto(-10, 1500, 85))));

        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherForecastHandler.extractThreeDayReportFromForecastDto(dto));
    }
}
