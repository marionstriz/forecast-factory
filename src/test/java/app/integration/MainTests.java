package app.integration;

import app.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTests {

    @TempDir
    private static Path tempDir;
    private static Path pathToDataDir;

    @BeforeAll
    public static void initialize() {
        pathToDataDir = Path.of("src", "test", "java", "app", "data");
        Main.createReportsFromInputGivenPath(
                pathToDataDir.resolve("cities-some-invalid.txt").toString(), tempDir.toString());
        Main.createReportsFromInputGivenPath(
                pathToDataDir.resolve("many-cities.txt").toString(), tempDir.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tallinn", "Stockholm", "Helsinki", "Rīga"})
    public void GivenFileWithMultipleValidCities_CreatesSeparateReportsForAll_ThatContainContent(String city) {
        Path expectedPath = tempDir.resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(expectedPath.toFile().length()).isGreaterThan(0L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Dubai", "Madrid", "Seoul"})
    public void GivenFileWithValidAndInvalidCities_CreatesSeparateReportsValidCities_ThatContainContent(
            String city) {
        Path expectedPath = tempDir.resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isTrue();
        assertThat(expectedPath.toFile().length()).isGreaterThan(0L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Neverland", "Winterfell"})
    public void GivenFileWithValidAndInvalidCities_DoesNotMakeReportsForInvalidCities (
            String city) {
        Path expectedPath = tempDir.resolve(city + ".json");

        assertThat(Files.exists(expectedPath)).isFalse();
    }
}
