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

    }

    /**
     * Attempts to fetch and parse the data into the root field.
     */
    private void loadJsonData() {
        String jsonData = apiClient.getData(AppConfig.API_ADDRESS);
        root = JsonParser.parseString(jsonData).getAsJsonObject();
    }

    /**
     * Returns the value corresponding to a certain index key.
     * @param key The index key string.
     * @return Integer value representing the specific data point.
     */
    private float getValue(String key) {
        return root
                .getAsJsonObject("value")
                .get(key).getAsFloat();
    }

    /**
     * Helper method for retrieving specific dimensions data
     * from the JSON-stat response.
     * @param root The JSON Object containing the data.
     * @param dimension Name of the dimension to access.
     * @param key The key for what specific dimension data to retrieve.
     * @return Integer value for the specific data point.
     */
    private int getSpecificDimensionData(JsonObject root, String dimension, String key) {
        return root.getAsJsonObject("dimension")
                .getAsJsonObject(dimension)
                .getAsJsonObject("category")
                .getAsJsonObject("index")
                .get(key)
                .getAsInt();
    }

}
