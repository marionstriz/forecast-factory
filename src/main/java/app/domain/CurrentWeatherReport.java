package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentWeatherReport {
    private String date;
    private double temperature;
    private int pressure;
    private int humidity;
}
