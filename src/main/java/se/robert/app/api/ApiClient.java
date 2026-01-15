package se.robert.app.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Responsible for retrieving data from the web API.
 *
 * @author Robert Kullman
 */
public class ApiClient {

    HttpClient client = HttpClient.newHttpClient();

    public String getData(String url) throws IOException, InterruptedException {

            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new IOException("Bad Request");
            }
            return response.body();
    }


}
