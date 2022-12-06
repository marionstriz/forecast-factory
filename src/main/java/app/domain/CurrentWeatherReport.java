package app.domain;

import lombok.Data;

@Data
public class CurrentWeatherReport {
    private String date;
    private double temperature;
    private int pressure;
    private int humidity;
}
