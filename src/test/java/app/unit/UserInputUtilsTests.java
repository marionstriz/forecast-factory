package app.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static app.helpers.UserInputUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class UserInputUtilsTests {

    private final String validExtensionFile = "city.txt";
    private final String invalidExtensionFile = "not-valid.csv";
    private final String notAFile = "not-a-file";

    @Test
    public void givenFileWithCorrectExtension_HasCorrectFileExtension_ReturnsTrue(){
        assertThat(hasCorrectFileExtension(validExtensionFile)).isFalse();
    }

    @Test
    public void givenFileWithWrongExtension_hHasCorrectFileExtension_ReturnsFalse(){
        assertThat(hasCorrectFileExtension(invalidExtensionFile)).isFalse();
    }

    @Test
    public void givenValidFile_isFile_ReturnsTrue(){
        assertThat(isFile(validExtensionFile)).isTrue();
    }

    @Test
    public void givenValidFile_WithWrongExtenstion_isFile_ReturnsTrue(){
        assertThat(isFile(invalidExtensionFile)).isTrue();
    }

    @Test
    public void givenIncorrectFile_isFile_ReturnsFalse(){
        assertThat(isFile(notAFile)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n"})
    public void givenBlankString_IsFile_ReturnsFalse(String string) {
        assertThat(isFile(string)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n"})
    public void givenBlankString_HasCorrectFileExtension_ReturnsFalse(String string) {
        assertThat(hasCorrectFileExtension(string)).isFalse();
    }

    @Test
    public void givenNull_IsFile_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> isFile(null));
    }

    @Test
    public void givenNull_HasCorrectFileExtension_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> hasCorrectFileExtension(null));
    }

    @Test
    public void givenNull_GetFileContentsOrInput_ThrowsIllegalArgumentException() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> getFileContentsOrInput(null));
    }

    @Test
    public void givenValidFile_getFileContentsOrInput_ReturnsContents(){
        String cityWithSymbolsFile = "weird-city.txt";
        String expected = "Saint Petersburg";
        assertThat(getFileContentsOrInput(cityWithSymbolsFile)).isEqualTo(expected);
    }

    @Test
    public void givenInvalidFile_getFileContentsOrInput_ReturnsContents(){
        assertThat(getFileContentsOrInput(notAFile)).isEqualTo(notAFile);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n"})
    public void givenBlankString_getFileContentsOrInput_ReturnsSameBlankString(String string) {
        assertThat(getFileContentsOrInput(string)).isEqualTo(string);
    }
}
