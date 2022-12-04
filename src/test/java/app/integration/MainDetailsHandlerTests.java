package app.integration;

import app.domain.MainDetails;
import app.weather.MainDetailsHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainDetailsHandlerTests {

    private static MainDetails mainDetails;

    @BeforeAll
    public static void Initialize() {
        String city = "Berlin";
        mainDetails = new MainDetailsHandler().getMainDetailsAboutCity(city);
    }

    @Test
    public void givenCityName_getWMainDetails_ShouldReturnDetailsWithCityName() {
        String expectedCity = "Berlin";
        assertThat(mainDetails.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenCityName_getMainDetails_ShouldReturnDetailsWithCoordinates() {
        String expectedCoords = "13.41,52.52";
        assertThat(mainDetails.getCoordinates()).isEqualTo(expectedCoords);
    }
}
