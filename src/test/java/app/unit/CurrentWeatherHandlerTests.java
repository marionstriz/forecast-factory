package app.unit;

import app.domain.CurrentWeatherReport;
import app.domain.MainDetails;
import app.weather.CurrentWeatherHandler;
import app.weather.MainDetailsHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CurrentWeatherHandlerTests {

    private CurrentWeatherHandler currentWeatherHandler;
    private CurrentWeatherReport currentWeatherReport;

    @BeforeAll
    public void Initialize() {
        currentWeatherHandler = new CurrentWeatherHandler();

        String city = "Manila";
        currentWeatherReport = currentWeatherHandler.getCurrentWeatherReport(city);
    }

    @Test
    public void WhenGivenDate_GetDateInStringFormat_ShouldReturnInCorrectFormat() {
        String expectedStringDate = "30-12-2022";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, 12);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        Date date = cal.getTime();

        assertThat(currentWeatherHandler.getDateInStringFormat(date)).isEqualTo(expectedStringDate);
    }

    @Test
    public void givenNull_GetDateInStringFormat_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> currentWeatherHandler.getDateInStringFormat(null));
    }

}
