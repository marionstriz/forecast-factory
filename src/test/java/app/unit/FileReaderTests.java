package app.unit;

import app.io.FileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReaderTests {

    private static FileReader fileReader;
    private static final Path pathToDataDir = Path.of("..", "data");

    @BeforeAll
    public static void initialize() {
        fileReader = new FileReader();
    }

    @ParameterizedTest
    @CsvSource({
            "city.txt, Frankfurt",
            "weird-city.txt, St. Petersburg",
            "empty-file.txt, ''",
            "not-valid.csv, Frankfurt"
    })
    public void GivenFileName_WithAnyExtension_GetContents_ShouldReturnContentsAsString(
            String file, String expectedOutput) {
        String filePath = pathToDataDir.resolve(file).toString();
        assertThat(fileReader.getFileContents(filePath)).isEqualTo(expectedOutput);
    }
}
