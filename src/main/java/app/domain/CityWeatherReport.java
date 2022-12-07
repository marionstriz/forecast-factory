package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonUnwrapped;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityWeatherReport {

    private MainDetails mainDetails;
    @JsonUnwrapped
    private WeatherReport weatherReport;
}
