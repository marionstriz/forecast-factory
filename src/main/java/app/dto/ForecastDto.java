package app.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {
    List<RangeForecastDto> rangeForecastDtos;
}
