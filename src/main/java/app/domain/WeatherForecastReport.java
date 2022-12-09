package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastReport {
    List<WeatherReport> forecast;

    public void addWeatherReportToList(WeatherReport report){
        if(forecast == null){
            forecast = new ArrayList<>();
        }

        forecast.add(report);
    }
}
