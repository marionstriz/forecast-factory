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
    private static final String API_KEY = "YOUR-API-KEY-HERE";

    private final Client client;

    public WeatherApi() {
        client = createClient();
    }

    public CurrentWeatherDto getCurrentWeatherDtoAboutCity(String city) {
        cityValidator(city);

        String resourceURL = BASE_URL + "/weather";

        ClientResponse response = client.resource(resourceURL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .get(ClientResponse.class);

        checkIfResponseIs404(city, response);

        return response.getEntity(CurrentWeatherDto.class);
    }

    private static void checkIfResponseIs404(String city, ClientResponse response) {
        if (response.getStatus() == 404){
            throw new IllegalArgumentException(city + " is not a real city");
        }
    }

    private static Client createClient() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return create(config);
    }

    public ForecastDto getForecastDtoAboutCity(String city) {
        cityValidator(city);

        String resourceURL = BASE_URL + "/forecast";

        ClientResponse response = client.resource(resourceURL)
                .queryParam("q", city)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .get(ClientResponse.class);

        checkIfResponseIs404(city, response);

        return response.getEntity(ForecastDto.class);
    }
    private static void cityValidator(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City name cannot be empty or null");
        }
    }
}
