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

    /**
     * Sends an HTTP GET request to the specified URL and returns the response body.
     * @param url the URL to send the request to
     * @return the response body as a String
     * @throws IOException if a network error occurs or if the server responds
     *                     with a status code of 400 or higher
     * @throws InterruptedException if the calling thread is interrupted while
     *                              waiting for the response
     */
    public String getData(String url) throws IOException, InterruptedException {

            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new IOException("Bad Request");
            }
            return response.body();
    }
}
