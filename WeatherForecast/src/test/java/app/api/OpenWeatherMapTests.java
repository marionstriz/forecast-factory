package app.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OpenWeatherMapTests {

    public static final String APPID = "e23d88c1cc9bfd0f5580776f36688fbb";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5";

    @Test
    public void givenNoApiKey_ToWeatherApiEndpoint_ShouldReturn401(){
        given()
                .when()
                .get(BASE_URL + "/weather")
                .then()
                .statusCode(401);
    }

    @Test
    public void givenApiKeyWithNoCity_ToWeatherApiEndpoint_ShouldReturn400AndGeocodeMessage(){
        given()
                .queryParam("appid", APPID)
                .when()
                .get(BASE_URL + "/weather")
                .then()
                .statusCode(400)
                .body("message", equalTo("Nothing to geocode"));
    }
}
