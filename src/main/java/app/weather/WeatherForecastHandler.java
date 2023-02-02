package app.weather;

import app.api.WeatherApi;
import app.domain.WeatherReport;
import app.domain.WeatherReportDetails;
import app.dto.ForecastDto;
import app.dto.RangeForecastDto;
import app.dto.WeatherInfoDto;

import java.text.DecimalFormat;
import java.util.*;

public class WeatherForecastHandler {

    private final WeatherApi weatherApi;

    public WeatherForecastHandler() {
        weatherApi = new WeatherApi();
    }

    public WeatherForecastHandler(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }


    public List<WeatherReport> getThreeDayForecastAboutCity(String city) {
        ForecastDto forecastDto = weatherApi.getForecastDtoAboutCity(city);
        return extractThreeDayReportFromForecastDto(forecastDto);
    }

    public List<WeatherReport> extractThreeDayReportFromForecastDto(ForecastDto forecastDto) {
        Map<String, List<WeatherInfoDto>> weatherInfoDtoMap = filterThreeFullDaysWeatherInfosToMapByDate(forecastDto);
        List<WeatherReport> weatherForecast = new ArrayList<>();

        for (Map.Entry<String, List<WeatherInfoDto>> map : weatherInfoDtoMap.entrySet()) {
            WeatherReportDetails details = calculateOneDayReportDetails(map.getValue());
            WeatherReport report = new WeatherReport(map.getKey(), details);
            weatherForecast.add(report);
        }
        if (weatherForecast.size() != 3) {
            throw new IllegalArgumentException("Unable to extract three-day-forecast from input");
        }
        return weatherForecast;
    }


    public WeatherReportDetails calculateOneDayReportDetails(List<WeatherInfoDto> weatherInfoDtos) {
        WeatherInfoDto accumulatedDto = getAccumulatedWeatherInfo(weatherInfoDtos);

        int dtoCount = weatherInfoDtos.size();

        double averageTemp = roundToTwoDecimalPlaces(accumulatedDto.getTemp() / dtoCount);
        int averagePressure = accumulatedDto.getPressure() / dtoCount;
        int averageHumidity = accumulatedDto.getHumidity() / dtoCount;

        return new WeatherReportDetails(averageTemp, averagePressure, averageHumidity);
    }

    private double roundToTwoDecimalPlaces(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(d));
    }

    private WeatherInfoDto getAccumulatedWeatherInfo(List<WeatherInfoDto> weatherInfoDtos) {
        return weatherInfoDtos.stream().reduce((a, b) -> {
            a.setTemp(a.getTemp() + b.getTemp());
            a.setHumidity(a.getHumidity() + b.getHumidity());
            a.setPressure(a.getPressure() + b.getPressure());
            return a;
        }).orElseThrow();
    }

    public Map<String, List<WeatherInfoDto>> filterThreeFullDaysWeatherInfosToMapByDate(ForecastDto dto){
        Map<String, List<WeatherInfoDto>> map = new LinkedHashMap<>();

        boolean newDay = false;
        for (RangeForecastDto rangeForecastDto : dto.getRangeForecastDtos()) {
            WeatherInfoDto weatherInfoDto = rangeForecastDto.getWeatherInfoDto();
            String date = getDateInCorrectStringFormat(rangeForecastDto.getDate());

            if (IsFirstForecastOfDay(rangeForecastDto)) {
                if (map.size() == 3) {
                    break;
                }
                newDay = true;
            }
            if (newDay) {
                map.computeIfAbsent(date, k -> new ArrayList<>()).add(weatherInfoDto);
            }
        }
        return map;
    }

    public String getDateInCorrectStringFormat(String date){
        String[] dateAndTime = date.split(" ");
        String[] dateParts = dateAndTime[0].split("-");

        return String.format("%s-%s-%s", dateParts[2], dateParts[1],dateParts[0]);
    }
    private boolean IsFirstForecastOfDay(RangeForecastDto dto) {
        String forecastTime = dto.getDate().split(" ")[1];
        return forecastTime.equals("00:00:00");
    }
}
