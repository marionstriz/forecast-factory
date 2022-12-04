package app.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfoDto {
    private double temp;
    private int pressure;
    private int humidity;
}
