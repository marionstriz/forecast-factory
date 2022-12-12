package app.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {

    public String getFileContents(String pathString) {
        if (pathString == null || pathString.isBlank()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        Path path = Path.of(pathString);

        if (!Files.exists(path)) {
            throw new IllegalArgumentException(
                    "Please enter correct path, no file found at " + path);
        }
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error reading file at " + pathString
            );
        }
        return content;
    }
}
