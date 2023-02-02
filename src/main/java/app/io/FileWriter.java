package app.io;

import app.domain.CityWeatherReport;
import app.weather.WeatherReportMachine;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileWriter {

    private final Path destinationDir;
    private final Logger logger = Logger.getLogger(FileWriter.class.getSimpleName());

    public FileWriter() {
        destinationDir = Path.of("reports");
    }

    public FileWriter(String destinationDir) {
        this.destinationDir = Path.of(destinationDir);
    }

    public void writeReportFile(CityWeatherReport report) {
        String cityName = report.getMainDetails().getCity();
        String fileName = cityName + ".json";
        Path destination = destinationDir.resolve(fileName);

        checkReportsDirExistsOrCreate();
        if (Files.exists(destination)) {
            logger.info("Rewriting report file " + fileName);
        }
        String reportJson = new WeatherReportMachine().getWeatherReportAsJson(report);

        try {
            Files.writeString(destination, reportJson);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error writing file to " + destination);
        }
        logger.info("Wrote report to file " + fileName);
    }

    private void checkReportsDirExistsOrCreate() {
        if (!Files.exists(destinationDir)) {
            try {
                Files.createDirectories(destinationDir);
            } catch (IOException e) {
                throw new RuntimeException("Error creating reports directory");
            }
        }
    }
}
