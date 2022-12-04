package app.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherDto {

    @JsonProperty("name")
    private String city;
    @JsonProperty("coord")
    private CoordinatesDto coordinates;
    @JsonProperty("main")
    private WeatherInfoDto weatherInfoDto;
}
