package se.robert.app.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.robert.app.api.ApiClient;
import se.robert.app.utilities.AppConfig;

import java.util.LinkedHashMap;

/**
 * The model component of the MVC pattern.
 * Responsible for processing data retrieved from a web API.
 *
 * @author Robert Kullman
 */
public class Model {

    private final ApiClient apiClient;
    private JsonObject root;

    private final LinkedHashMap<String, Integer> dimensionSizes;

    public Model(ApiClient client) {

        this.apiClient = client;
        dimensionSizes = new LinkedHashMap<>();
        // load json data
        loadJsonData();

        // setup dimension data to be used in index calculation
        boolean success = getDimensionSizes();
        if (!success) {
            System.err.println("Failed to setup dimension data.");
            return;
        }

        // get country-specific dimension data

        // calculate

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

    private boolean getDimensionSizes() {
        if (root == null) {
            System.err.println("root is null.");
            return false;
        }

        JsonArray dimensionIDs = root.get("id").getAsJsonArray();
        JsonArray sizes = root.get("size").getAsJsonArray();

        if (dimensionIDs.size() != sizes.size()) {
            System.err.println("dimensionIDs size != sizes.");
            return false;
        }

        for (int i = 0; i < dimensionIDs.size(); i++) {
            dimensionSizes.put(dimensionIDs.get(i).getAsString(), sizes.get(i).getAsInt());
        }
        return true;
    }

}
