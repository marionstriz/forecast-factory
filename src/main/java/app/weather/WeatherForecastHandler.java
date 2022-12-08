package app.weather;

import app.api.WeatherApi;
import app.domain.WeatherForecastReport;
import app.domain.WeatherReport;
import app.dto.ForecastDto;
import app.dto.WeatherInfoDto;

import java.util.List;
import java.util.Map;

public class WeatherForecastHandler {

    private final WeatherApi weatherApi;

    public WeatherForecastHandler() {
        weatherApi = new WeatherApi();
    }

    public WeatherForecastHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }


    public WeatherForecastReport getWeatherForecastReport(String city) {
        return null;
    }

    public WeatherReport calculateOneDayReport(List<WeatherInfoDto> weatherInfoDtos){
        return null;
    }

    public Map<String, List<WeatherInfoDto>> filterWeatherInfoDtosToMapByDate(ForecastDto dto){
        return null;
    }

    public String getDateInCorrectStringFormat(String date){
        return "";
    }
}
