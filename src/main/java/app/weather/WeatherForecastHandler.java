package app.weather;

import app.domain.WeatherForecastReport;
import app.domain.WeatherReport;
import app.dto.ForecastDto;
import app.dto.WeatherInfoDto;

import java.util.List;
import java.util.Map;

public class WeatherForecastHandler {

    public WeatherForecastReport getWeatherForecastReport(String city) {
        return null;
    }

    public WeatherReport calculateOneDayReport(List<WeatherInfoDto> weatherInfoDtos){
        return null;
    }

    public Map<String, WeatherInfoDto> filterWeatherInfoDtosToMapByDate(ForecastDto dto){
        return null;
    }

    public String getDateInCorrectStringFormat(String date){
        return "";
    }
}
