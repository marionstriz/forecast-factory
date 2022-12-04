package app.api;

import app.dto.CurrentWeatherDto;

public class WeatherApi {

    public CurrentWeatherDto getCurrentWeatherDtoAboutCity(String city) {
        return new CurrentWeatherDto();
    }
}
