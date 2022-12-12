package app.integration;

import app.domain.CityWeatherReport;
import app.io.FileReader;
import app.weather.WeatherReportMachine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class FileReaderTests {

    private static FileReader fileReader;
    private static WeatherReportMachine weatherReportMachine;
    private static CityWeatherReport regFileCityReport;
    private static CityWeatherReport weirdFileCityReport;
    private static final Path pathToDataDir = Path.of("..", "data");

    @BeforeAll
    public static void initialize() {
        fileReader = new FileReader();
        weatherReportMachine = new WeatherReportMachine();

        String normalFile = "city.txt";
        String weirdFile = "weird-city.txt";

        String contents = fileReader.getFileContents(pathToDataDir.resolve(normalFile).toString());
        regFileCityReport = weatherReportMachine.getFullCityWeatherReport(contents);

        contents = fileReader.getFileContents(pathToDataDir.resolve(weirdFile).toString());
        weirdFileCityReport = weatherReportMachine.getFullCityWeatherReport(contents);
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWith_MainDetails() {
        assertThat(regFileCityReport.getMainDetails()).isNotNull();
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWith_CurrentWeather() {
        assertThat(regFileCityReport.getCurrentWeatherReport()).isNotNull();
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWith_Forecast() {
        assertThat(regFileCityReport.getForecastReport()).isNotNull();
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWith_CorrectCityName() {
        String expectedName = "Frankkfurt";
        assertThat(regFileCityReport.getMainDetails().getCity()).isEqualTo(expectedName);
    }

    @Test
    public void WhenGivenFileWithValidCityWithSymbols_ShouldCreateReportWith_CorrectCityName() {
        String expectedName = "Saint Petersburg";
        assertThat(weirdFileCityReport.getMainDetails().getCity()).isEqualTo(expectedName);
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWith_CorrectCoordinates() {
        String expectedCoords = "50.11,8.68";
        assertThat(regFileCityReport.getMainDetails().getCoordinates()).isEqualTo(expectedCoords);
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldCreateReportWithForecast_ThatHasThreeEntries() {
        assertThat(regFileCityReport.getForecastReport()).hasSize(3);
    }

    @Test
    public void WhenGivenFileWithNonexistentCity_CreateReport_ShouldThrowIllegalArgumentException() {
        String file = "nonexistent-city.txt";
        String contents = fileReader.getFileContents(pathToDataDir.resolve(file).toString());

        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherReportMachine.getFullCityWeatherReport(contents));
    }
}
