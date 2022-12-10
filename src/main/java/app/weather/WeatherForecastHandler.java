package app.weather;

import app.api.WeatherApi;
import app.domain.WeatherReport;
import app.domain.WeatherReportDetails;
import app.dto.ForecastDto;
import app.dto.RangeForecastDto;
import app.dto.WeatherInfoDto;

import java.util.*;

public class WeatherForecastHandler {

    private final WeatherApi weatherApi;

    public WeatherForecastHandler() {
        weatherApi = new WeatherApi();
    }

    public WeatherForecastHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }


    public List<WeatherReport> getWeatherForecastReport(String city) {
        ForecastDto forecastDto = weatherApi.getForecastDtoAboutCity(city);
        Map<String, List<WeatherInfoDto>> weatherInfoDtoMap = filterWeatherInfoDtosToMapByDate(forecastDto);

        return extractThreeDayReportFromMap(weatherInfoDtoMap);
    }

    private List<WeatherReport> extractThreeDayReportFromMap(Map<String, List<WeatherInfoDto>> weatherInfoDtoMap) {
        List<WeatherReport> forecast = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<String, List<WeatherInfoDto>> map : weatherInfoDtoMap.entrySet()) {
            if(counter > 0 && counter <= 3){
                WeatherReportDetails details = calculateOneDayReportDetails(map.getValue());
                WeatherReport report = new WeatherReport(map.getKey(), details);
                forecast.add(report);
                counter++;
                continue;
            }
            counter++;
        }
        return forecast;
    }


    public WeatherReportDetails calculateOneDayReportDetails(List<WeatherInfoDto> weatherInfoDtos){
        double averageTemp = weatherInfoDtos
                            .stream().mapToDouble(WeatherInfoDto::getTemp)
                            .average().getAsDouble();

        int averagePressure = (int) weatherInfoDtos
                             .stream().mapToInt(WeatherInfoDto::getPressure)
                             .average().getAsDouble();

        int averageHumidity = (int) weatherInfoDtos
                             .stream().mapToInt(WeatherInfoDto::getHumidity)
                             .average()
                             .getAsDouble();

        return new WeatherReportDetails(averageTemp, averagePressure, averageHumidity);
    }

    public LinkedHashMap<String, List<WeatherInfoDto>> filterWeatherInfoDtosToMapByDate(ForecastDto dto){
        LinkedHashMap<String, List<WeatherInfoDto>> map = new LinkedHashMap<>();
        for (RangeForecastDto rangeForecastDto : dto.getRangeForecastDtos()) {
            WeatherInfoDto weatherInfoDto = rangeForecastDto.getWeatherInfoDto();
            map.computeIfAbsent(getDateInCorrectStringFormat(rangeForecastDto.getDate())
                    , k -> new ArrayList<>()).add(weatherInfoDto);
        }
        System.out.println(map.keySet());
        return map;
    }

    public String getDateInCorrectStringFormat(String date){
        String[] initialDateParts = date.split(" ");
        String[] dateParts = initialDateParts[0].split("-");

        return String.format("%s-%s-%s", dateParts[2], dateParts[1],dateParts[0]);
    }
}
