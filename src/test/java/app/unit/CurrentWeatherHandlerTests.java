package app.unit;

import app.domain.CityWeatherReport;
import app.weather.CurrentWeatherHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CurrentWeatherHandlerTests {

    private static CurrentWeatherHandler currentWeatherHandler;
    private static CityWeatherReport cityWeatherReport;

    @BeforeAll
    public static void Initialize() {
        currentWeatherHandler = new CurrentWeatherHandler();

        String city = "Manila";
        cityWeatherReport = currentWeatherHandler.getCityWeatherReport(city);
    }

    @Test
    public void WhenGivenDate_GetDateInStringFormat_ShouldReturnInCorrectFormat() {
        String expectedStringDate = "30-12-2022";
        Date date = new GregorianCalendar(2022, Calendar.DECEMBER, 30).getTime();
        assertThat(currentWeatherHandler.getDateFromMillisInStringFormat(date.getTime()/1000))
                .isEqualTo(expectedStringDate);
    }

    @Test
    public void WhenGivenCoordinatesAsDoubles_GetStringFormatCoordinates_GivesCoordinatesInCorrectFormat() {
        double givenLat = 59.345987;
        double givenLon = 103.12378;

        assertThat(currentWeatherHandler.getStringFormatCoordinates(givenLat, givenLon)).isEqualTo("59.35,103.12");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-150, 150})
    public void GivenTooHighOrTooLowLatitude_GetStringFormatCoordinates_ThrowsIllegalArgumentException(double givenLat) {
        double givenLon = -95.12378;

        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getStringFormatCoordinates(givenLat, givenLon));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-190, 230})
    public void GivenTooHighOrTooLowLongitude_GetStringFormatCoordinates_ThrowsIllegalArgumentException(double givenLon) {
        double givenLat = 13.986;

        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getStringFormatCoordinates(givenLat, givenLon));
    }

    @Test
    public void GetMainDetails_ReturnsMainDetails_WithCelsiusAsTempUnit() {
        String expectedUnit = "Celsius";
        assertThat(cityWeatherReport.getMainDetails().getTemperatureUnit()).isEqualTo(expectedUnit);
    }

}
