package app;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args)
            throws URISyntaxException, IOException, InterruptedException {

        /*
        HttpRequest req = HttpRequest.newBuilder(
                new URI("http://api.openweathermap.org/geo/1.0/direct?q=Tallinn&limit=1&appid=e23d88c1cc9bfd0f5580776f36688fbb"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println(resp.body());

         */
    }
}
