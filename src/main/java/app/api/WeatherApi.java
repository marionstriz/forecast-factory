package app.api;

import app.dto.CurrentWeatherDto;
import app.dto.ForecastDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final String API_KEY = "e23d88c1cc9bfd0f5580776f36688fbb";

    public CurrentWeatherDto getCurrentWeatherDtoAboutCity(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City name cannot be empty or null");
        }
        String resourceURL = BASE_URL + "/weather";

        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(FEATURE_POJO_MAPPING, true);
        Client client = create(config);

        ClientResponse response = client.resource(resourceURL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .get(ClientResponse.class);

        if (response.getStatus() == 404){
            throw new IllegalArgumentException("Not a real city >:( : " + city);
        }

        return response.getEntity(CurrentWeatherDto.class);
    }

    public ForecastDto getForecastDtoAboutCity(String city) {
        return new ForecastDto();
    }
}
