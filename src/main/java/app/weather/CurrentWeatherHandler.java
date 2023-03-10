package app.weather;

import app.api.WeatherApi;
import app.domain.*;
import app.dto.CoordinatesDto;
import app.dto.CurrentWeatherDto;
import app.dto.WeatherInfoDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeatherHandler {

    private final WeatherApi weatherApi;

    public CurrentWeatherHandler() {
        weatherApi = new WeatherApi();
    }

    public CurrentWeatherHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public CityWeatherReport getCityWeatherReport(String city) {
        CurrentWeatherDto dto = weatherApi.getCurrentWeatherDtoAboutCity(city);
        MainDetails mainDetails = getMainDetailsFromDto(dto);
        CurrentWeather weatherReport = getCurrentWeatherFromDto(dto);
        return new CityWeatherReport(mainDetails, weatherReport);
    }

    private CurrentWeather getCurrentWeatherFromDto(CurrentWeatherDto dto){
        String dateString = getDateFromMillisInStringFormat(dto.getUtcMillis());
        WeatherInfoDto weatherInfoDto = dto.getWeatherInfoDto();

        return new CurrentWeather(
                dateString,
                new WeatherReportDetails(
                        weatherInfoDto.getTemp(),
                        weatherInfoDto.getPressure(),
                        weatherInfoDto.getHumidity()));
    }

    private MainDetails getMainDetailsFromDto(CurrentWeatherDto dto){
        CoordinatesDto coordsDto = dto.getCoordinates();
        String coords = getStringFormatCoordinates(coordsDto.getLat(), coordsDto.getLon());

        return new MainDetails(dto.getCity(), coords, "Celsius");
    }

    public String getDateFromMillisInStringFormat(long millis) {
        Date date = new Date (millis*1000);
        String pattern = "dd-MM-yyyy";
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    public String getStringFormatCoordinates(double lat, double lon) {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180){
            throw new IllegalArgumentException("Invalid coordinates");
        }
        return String.format("%.2f,%.2f", lat, lon);
    }
}
