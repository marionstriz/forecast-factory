package app.unit;

import app.io.FileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class FileReaderTests {

    private static FileReader fileReader;
    private static Path pathToDataDir;

    @BeforeAll
    public static void initialize() {
        fileReader = new FileReader();
        pathToDataDir = Path.of("src", "test", "java", "app", "data");
    }

    @ParameterizedTest
    @CsvSource({
            "city.txt, Frankfurt",
            "weird-city.txt, St. Petersburg",
            "empty-file.txt, ''",
            "not-valid.csv, Frankfurt"
    })
    public void givenFileName_WithAnyExtension_GetContents_ShouldReturnContentsAsString(
            String file, String expectedOutput) {
        String filePath = pathToDataDir.resolve(file).toString();
        assertThat(fileReader.getFileContents(filePath)).isEqualTo(expectedOutput);
    }

    @Test
    public void givenFileName_ThatDoesNotExist_GetContents_ThrowsIllegalArgumentException() {
        String filePath = pathToDataDir.resolve("doesnt-exist.txt").toString();
        assertThatIllegalArgumentException().isThrownBy(
                () -> fileReader.getFileContents(filePath));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", " \t \n"})
    public void givenBlankString_AsFileName_GetContents_ThrowsIllegalArgumentException(String input) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> fileReader.getFileContents(input));
    }

    @Test
    public void givenNull_GetContents_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> fileReader.getFileContents(null));
    }
}
