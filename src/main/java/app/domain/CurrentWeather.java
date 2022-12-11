package app.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class CurrentWeather extends WeatherReport {

    public CurrentWeather(String date, WeatherReportDetails weather) {
        super(date, weather);
    }

    @Override
    @JsonUnwrapped
    public WeatherReportDetails getWeather() {
        return super.getWeather();
    }
}
