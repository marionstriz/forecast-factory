package app.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public interface WeatherReportMixin {
    @JsonUnwrapped
    WeatherReportDetails getDetails();
}
