package app.integration;

import app.domain.CityWeatherReport;
import app.io.FileWriter;
import app.weather.WeatherReportMachine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileWriterTests {

    private static FileWriter fileWriter;
    private static WeatherReportMachine machine;

    @BeforeAll
    public static void initialize(@TempDir Path tempDir) {
        fileWriter = new FileWriter(tempDir.toString());
        machine = new WeatherReportMachine();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rio", "Los Angeles"})
    public void WhenGivenReport_ShouldWriteReportAsJsonToFile(String city)
            throws IOException {

        CityWeatherReport report = machine.getFullCityWeatherReport(city);
        fileWriter.writeReportFile(report);

        String expectedJson = machine.getWeatherReportAsJson(report);
        Path expectedPath = fileWriter.getDestinationDir().resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(Files.readString(expectedPath)).isEqualTo(expectedJson);
    }
}
