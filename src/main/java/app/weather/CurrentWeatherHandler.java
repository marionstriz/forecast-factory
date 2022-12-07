package app.weather;

import app.api.WeatherApi;
import app.domain.CityWeatherReport;
import app.domain.CurrentWeatherReport;
import app.domain.MainDetails;
import app.dto.CoordinatesDto;
import app.dto.CurrentWeatherDto;
import app.dto.WeatherInfoDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class CurrentWeatherHandler {

    private WeatherApi weatherApi;

    public CurrentWeatherHandler() {
        weatherApi = new WeatherApi();
    }

    public CurrentWeatherHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public CityWeatherReport getCityWeatherReport(String city) {
        CurrentWeatherDto dto = weatherApi.getCurrentWeatherDtoAboutCity(city);
        MainDetails mainDetails = getMainDetailsFromDto(dto);
        CurrentWeatherReport currentWeatherReport = getCurrentWeatherFromDto(dto);
        CityWeatherReport cityWeatherReport = new CityWeatherReport(mainDetails, currentWeatherReport);
        return cityWeatherReport;
    }

    private CurrentWeatherReport getCurrentWeatherFromDto(CurrentWeatherDto dto){
        Date date = Date.from(Instant.now());
        String dateString = getDateInStringFormat(date);
        WeatherInfoDto weatherInfoDto = dto.getWeatherInfoDto();
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport(
                dateString,
                weatherInfoDto.getTemp(),
                weatherInfoDto.getPressure(),
                weatherInfoDto.getHumidity());

        return currentWeatherReport;
    }

    private MainDetails getMainDetailsFromDto(CurrentWeatherDto dto){
        CoordinatesDto coordsDto = dto.getCoordinates();
        String coords = getStringFormatCoordinates(coordsDto.getLat(), coordsDto.getLon());
        MainDetails mainDetails = new MainDetails(dto.getCity(), coords, "Celsius");

        return mainDetails;
    }

    public String getDateInStringFormat(Date date) {
        if (date == null){
            throw new IllegalArgumentException("Date cannot be null");
        }
        String pattern = "dd-MM-yyyy";
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public String getStringFormatCoordinates(double lat, double lon) {
        return String.format("%.2f,%.2f", lat, lon);
    }
}
