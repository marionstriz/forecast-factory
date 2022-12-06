package app.domain;

import lombok.Data;

@Data
public class CityWeatherReport {

    private MainDetails mainDetails;
    private CurrentWeatherReport currentWeatherReport;
}
