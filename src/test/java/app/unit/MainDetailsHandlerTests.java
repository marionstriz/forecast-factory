package app.unit;

import app.domain.MainDetails;
import app.weather.MainDetailsHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MainDetailsHandlerTests {

    private MainDetailsHandler mainDetailsHandler;
    private MainDetails mainDetails;

    @BeforeAll
    public void Initialize() {
        mainDetailsHandler = new MainDetailsHandler();

        String city = "Manila";
        mainDetails = mainDetailsHandler.getMainDetailsAboutCity(city);
    }

    @Test
    public void WhenGivenCoordinatesAsDoubles_GetStringFormatCoordinates_GivesCoordinatesInCorrectFormat() {
        double givenLat = 59.345987;
        double givenLon = 103.12378;

        assertThat(mainDetailsHandler.getStringFormatCoordinates(givenLat, givenLon)).isEqualTo("59.34,103.12");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-150, 150})
    public void GivenTooHighOrTooLowLatitude_GetStringFormatCoordinates_ThrowsIllegalArgumentException(double givenLat) {
        double givenLon = -95.12378;

        assertThatIllegalArgumentException().isThrownBy(
                () -> mainDetailsHandler.getStringFormatCoordinates(givenLat, givenLon));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-190, 230})
    public void GivenTooHighOrTooLowLongitude_GetStringFormatCoordinates_ThrowsIllegalArgumentException(double givenLon) {
        double givenLat = 13.986;

        assertThatIllegalArgumentException().isThrownBy(
                () -> mainDetailsHandler.getStringFormatCoordinates(givenLat, givenLon));
    }

    @Test
    public void GetMainDetails_ReturnsMainDetails_WithCelsiusAsTempUnit() {
        String expectedUnit = "Celsius";
        assertThat(mainDetails.getTemperatureUnit()).isEqualTo(expectedUnit);
    }
}
