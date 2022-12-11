package app.integration;

import app.domain.CityWeatherReport;
import app.helpers.FileUtils;
import app.weather.WeatherReportMachine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class FileUtilsTests {

    private static WeatherReportMachine weatherReportMachine;
    private static CityWeatherReport regFileCityReport;
    private static CityWeatherReport weirdFileCityReport;

    @BeforeAll
    public static void initialize() {
        weatherReportMachine = new WeatherReportMachine();
        String normalFile = "city.txt";
        String weirdFile = "weird-city.txt";

        String contents = FileUtils.getFileContentsOrInput(normalFile);
        regFileCityReport = weatherReportMachine.getFullCityWeatherReport(contents);

        contents = FileUtils.getFileContentsOrInput(weirdFile);
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
        String contents = FileUtils.getFileContentsOrInput(file);

        assertThatIllegalArgumentException().isThrownBy(
                () -> weatherReportMachine.getFullCityWeatherReport(contents));
    }

    @Test
    public void WhenGivenFileWithValidCity_ShouldWriteJsonReportToFile(@TempDir Path tempDir)
            throws IOException {
        Path cityPath = tempDir.resolve("city.txt");

        String expectedJson = weatherReportMachine.getWeatherReportAsJson(regFileCityReport);

        FileUtils.writeFile(cityPath.toString());
        assertThat(Files.exists(cityPath)).isTrue();
        assertThat(Files.readString(cityPath)).isEqualTo(expectedJson);
    }
}
