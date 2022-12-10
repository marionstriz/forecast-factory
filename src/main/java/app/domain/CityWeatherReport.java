package app.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CityWeatherReport {

    @NonNull
    private MainDetails mainDetails;
    @NonNull
    private WeatherReport currentWeatherReport;
    List<WeatherReport> forecastReport;
}
