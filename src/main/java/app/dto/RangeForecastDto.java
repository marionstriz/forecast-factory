package app.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RangeForecastDto {
    @JsonProperty("dt_txt")
    private String date;
    @JsonProperty("main")
    private WeatherInfoDto weatherInfoDto;
}
