package app.integration;

import app.Main;
import app.io.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTests {

    private static FileWriter fileWriter;

    @BeforeAll
    public static void initialize(@TempDir Path tempDir) {
        fileWriter = new FileWriter(tempDir.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tallinn", "Stockholm", "Helsinki", "Riga"})
    public void GivenFileWithMultipleValidCities_CreatesSeparateReportsForAll_ThatContainContent(String city) {
        Main.createReportsFromInput("many-cities.txt");
        Path expectedPath = fileWriter.getDestinationDir().resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(expectedPath.toFile().length()).isGreaterThan(0L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tallinn", "Madrid", "Seoul"})
    public void GivenFileWithValidAndInvalidCities_CreatesSeparateReportsValidCities_ThatContainContent(
            String city) {
        Main.createReportsFromInput("cities-some-invalid.txt");
        Path expectedPath = fileWriter.getDestinationDir().resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(expectedPath.toFile().length()).isGreaterThan(0L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Neverland", "Winterfell"})
    public void GivenFileWithValidAndInvalidCities_DoesNotMakeReportsForInvalidCities (
            String city) {
        Main.createReportsFromInput("cities-some-invalid.txt");
        Path expectedPath = fileWriter.getDestinationDir().resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isFalse();
    }
}
