package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherReport {
    private String date;
    private WeatherReportDetails weather;
}
