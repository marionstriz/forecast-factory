package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityWeatherReport {

    private MainDetails mainDetails;
    private CurrentWeatherReport currentWeatherReport;
}
