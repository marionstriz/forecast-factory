package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityWeatherReport {

    private MainDetails mainDetails;
    private CurrentWeatherReport currentWeatherReport;
}
