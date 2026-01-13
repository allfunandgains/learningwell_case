package se.robert.app.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.robert.app.api.ApiClient;
import se.robert.app.utilities.AppConfig;

/**
 * The model component of the MVC pattern.
 * Responsible for processing data retrieved from a web API.
 *
 * @author Robert Kullman
 */
public class Model {

    private final ApiClient apiClient;
    private JsonObject root;

    public Model(ApiClient client) {

        this.apiClient = client;

        loadJsonData();
        printUpdated();

    }

    private void loadJsonData() {
        String jsonData = apiClient.getData(AppConfig.API_ADDRESS);
        root = JsonParser.parseString(jsonData).getAsJsonObject();
    }

    // test
    private void printUpdated() {
        String testString = root
                .get("updated").getAsString();
        System.out.println(testString);
    }

}
