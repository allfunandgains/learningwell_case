package se.robert.app.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.robert.app.api.ApiClient;
import se.robert.app.records.YearData;
import se.robert.app.utilities.AppConfig;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private LinkedList<YearData> currentDataSet;

    public Model(ApiClient client) {

        this.apiClient = client;
        dimensionSizes = new LinkedHashMap<>();
        //countryDimensionIndices = new LinkedHashMap<>();
        // load json data
        loadJsonData();

        // setup dimension data to be used in index calculation
        boolean success = populateDimensionSizes();
        if (!success) {
            System.err.println("Failed to setup dimension data.");
            return;
        }

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

    private boolean populateDimensionSizes() {
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

    private Map<String, Integer> baseSelection() {
        Map<String, Integer> m = new HashMap<>();
        m.put("FREQ", getSpecificDimensionData(root, "FREQ", "A"));
        m.put("UNIT", getSpecificDimensionData(root, "UNIT", "RT"));
        m.put("AGE",  getSpecificDimensionData(root, "AGE", "TOTAL"));
        m.put("REGIS_ES", getSpecificDimensionData(root, "REGIS_ES", "REG_UNE"));
        m.put("LMP_TYPE", getSpecificDimensionData(root, "LMP_TYPE", "TOT2_7"));
        return m;
    }

    private Map<String, Integer> baseSelectionWithCountry(String countryISO) {
        Map<String, Integer> selection = baseSelection();
        selection.put("GEO",  getSpecificDimensionData(root, "GEO", countryISO));
        return selection;
    }

    public void generateDataSet(String countryISO) {
        //TODO: add input validation, show error dialog if ISO is not found.

        currentDataSet = new LinkedList<>();

        Map<String, Integer> selection = baseSelectionWithCountry(countryISO);
        LinkedHashMap<String, Integer> years = getYearsMap();

        years.forEach((key, year) -> {
            List<Integer> sizes = AppConfig.DIMENSION_ORDER.stream()
                    .map(dimensionSizes::get)
                    .toList();

            selection.put("TIME_PERIOD", year);
            selection.put("SEX", 1);

            int maleIndex = flatIndexFor(selection);
            float maleValue = getValue(Integer.toString(maleIndex), root);

            selection.put("SEX", 2);

            int femaleIndex = flatIndexFor(selection);
            float femaleValue = getValue(Integer.toString(femaleIndex), root);

            currentDataSet.add(new YearData(year, maleValue, femaleValue));
        });
    }

    private int flatIndexFor(Map<String, Integer> selection) {
        List<Integer> indices = AppConfig.DIMENSION_ORDER.stream()
                .map(selection::get)
                .toList();

        List<Integer> sizes = AppConfig.DIMENSION_ORDER.stream()
                .map(dimensionSizes::get)
                .toList();

        return calculateFlatIndex(indices, sizes);
    }

    private LinkedHashMap<String, Integer> getYearsMap() {
        LinkedHashMap<String, Integer> years = new LinkedHashMap<>();

        JsonObject yearIndex = root
                .getAsJsonObject("dimension")
                .getAsJsonObject("TIME_PERIOD")
                .getAsJsonObject("category")
                .getAsJsonObject("index");

        yearIndex.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getKey())))
                .forEach(e -> years.put(e.getKey(), e.getValue().getAsInt()));

        return years;
    }

    private int calculateFlatIndex(List<Integer> indices, List<Integer> sizes) {
        int idx = 0;
        for (int k = 0; k < sizes.size(); k++) {
            idx = idx * sizes.get(k) + indices.get(k);
        }
        return idx;
    }

    private float getValue(String key, JsonObject root) {
        JsonElement e = root.getAsJsonObject("value").get(key);
        if (e == null || e.isJsonNull()) {
            return Float.NaN;
        }

        return e.getAsFloat();
    }


}
