package app.integration;

import app.domain.MainDetails;
import app.dto.CurrentWeatherDto;
import app.weather.MainDetailsHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MainDetailsHandlerTests {

    private static MainDetails mainDetails;
    private static MainDetailsHandler mainDetailsHandler;

    @BeforeAll
    public static void Initialize() {
        String city = "Berlin";
        mainDetailsHandler = new MainDetailsHandler();
        mainDetails = mainDetailsHandler.getMainDetailsAboutCity(city);
    }

    @Test
    public void givenCityName_getWMainDetails_ShouldReturnDetailsWithCityName() {
        String expectedCity = "Berlin";
        assertThat(mainDetails.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenCityName_getMainDetails_ShouldReturnDetailsWithCoordinates() {
        String expectedCoords = "52.52,13.41";
        assertThat(mainDetails.getCoordinates()).isEqualTo(expectedCoords);
    }

    @Test
    public void givenCityNameWithWhitespace_GetMainDetails_ContainsCityNameWithoutWhitespace() {
        String inputCity = "   Tallinn    ";
        MainDetails mainDetails = mainDetailsHandler.getMainDetailsAboutCity(inputCity);

        String expectedCity = "Tallinn";
        assertThat(mainDetails.getCity()).isEqualTo(expectedCity);
    }

    @Test
    public void givenNonExistentCity_GetMainDetails_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () ->mainDetailsHandler.getMainDetailsAboutCity("Deathstar"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"       ", "", " \t \n \t\t "})
    public void givenEmptyString_GetMainDetails_ThrowsIllegalArgumentException(String input) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> mainDetailsHandler.getMainDetailsAboutCity(input));
    }

    @Test
    public void givenNull_GetMainDetails_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> mainDetailsHandler.getMainDetailsAboutCity(null));
    }
}
