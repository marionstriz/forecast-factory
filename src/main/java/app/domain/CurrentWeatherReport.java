package app.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CurrentWeatherReport {
    private Date date;
    private double temperature;
    private int pressure;
    private int humidity;
}
