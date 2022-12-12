package app.io;

import app.domain.CityWeatherReport;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class FileWriter {

    private final Path destinationDir;

    public FileWriter() {
        destinationDir = Path.of("reports");
    }

    public FileWriter(String destinationDir) {
        this.destinationDir = Path.of(destinationDir);
    }

    public void writeReportFile(CityWeatherReport report) {}
}
