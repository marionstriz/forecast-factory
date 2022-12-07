package app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainDetails {

    private String city;
    private String coordinates;
    private String temperatureUnit;
}
