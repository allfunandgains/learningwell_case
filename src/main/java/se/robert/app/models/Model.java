package se.robert.app.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import se.robert.app.api.ApiClient;
import se.robert.app.models.exceptions.ModelException;
import se.robert.app.records.CountryDataSet;
import se.robert.app.records.YearData;
import se.robert.app.utilities.AppConfig;

import java.io.IOException;
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

    public Model(ApiClient client) {
        this.apiClient = client;
    }

    /**
     * Attempts to fetch and parse the data into the root field.
     */
    private void loadJsonData() throws IOException, InterruptedException {
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
                .getAsJsonObject(AppConfig.VALUE_MEMBER_NAME)
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
        return root.getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(dimension)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject(AppConfig.INDEX_MEMBER_NAME)
                .get(key)
                .getAsInt();
    }

    private LinkedHashMap<String, Integer> getDimensionSizes() throws ModelException {
        if (root == null) {
            throw new ModelException("Root object is null");
        }

        JsonArray dimensionIDs = root.get(AppConfig.ID_MEMBER_NAME).getAsJsonArray();
        JsonArray sizes = root.get(AppConfig.SIZE_MEMBER_NAME).getAsJsonArray();

        if (dimensionIDs.size() != sizes.size()) {
           throw new ModelException("DimensionIds and dimension sizes lists differ in length.");
        }

        LinkedHashMap<String, Integer> dimensionSizes = new LinkedHashMap<>();

        for (int i = 0; i < dimensionIDs.size(); i++) {
            dimensionSizes.put(dimensionIDs.get(i).getAsString(), sizes.get(i).getAsInt());
        }
        return dimensionSizes;
    }

    private Map<String, Integer> baseSelection() {
        Map<String, Integer> m = new HashMap<>();
        m.put(AppConfig.FREQ_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.FREQ_MEMBER_NAME, AppConfig.FREQUENCY_ANNUAL_INDEX_VALUE));
        m.put(AppConfig.UNIT_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.UNIT_MEMBER_NAME, AppConfig.UNIT_RATE_INDEX_VALUE));
        m.put(AppConfig.AGE_MEMBER_NAME,  getSpecificDimensionData(root, AppConfig.AGE_MEMBER_NAME, AppConfig.AGE_INDEX_TOTAL));
        m.put(AppConfig.REGIS_ES_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.REGIS_ES_MEMBER_NAME, AppConfig.REGISTERED_UNEMPLOYED_INDEX_VALUE));
        m.put(AppConfig.LMP_TYPE_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.LMP_TYPE_MEMBER_NAME, AppConfig.LMP_TYPE_TOTAL_INDEX_VALUE));
        return m;
    }

    private Map<String, Integer> baseSelectionWithCountry(String countryISO) {
        Map<String, Integer> selection = baseSelection();
        selection.put(AppConfig.GEO_MEMBER_NAME,  getSpecificDimensionData(root, AppConfig.GEO_MEMBER_NAME, countryISO));
        return selection;
    }

    public CountryDataSet generateDataSet(String countryISO) throws ModelException {
        if (root == null) {
            try {
                loadJsonData();
            } catch (IOException | InterruptedException e) {
                throw new ModelException("An error occurred while downloading data.");
            }
        }
        JsonObject geoIndex = root
                .getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(AppConfig.GEO_MEMBER_NAME)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject(AppConfig.INDEX_MEMBER_NAME);

        if (!geoIndex.has(countryISO)) {
            throw new ModelException("The specified ISO code was not found.");
        }


        LinkedList<YearData> dataList = new LinkedList<>();

        Map<String, Integer> selection = baseSelectionWithCountry(countryISO);
        Map<String, Integer> dimensionSizes = getDimensionSizes();
        LinkedHashMap<String, Integer> years = getYearsMap();

        years.forEach((key, year) -> {
            selection.put(AppConfig.TIME_PERIOD_KEY, year);
            selection.put(AppConfig.SEX_MEMBER_NAME, AppConfig.SEX_MALE_VALUE);

            int maleIndex = flatIndexFor(selection, dimensionSizes);
            float maleValue = getValue(Integer.toString(maleIndex), root);

            selection.put(AppConfig.SEX_MEMBER_NAME, AppConfig.SEX_FEMALE_VALUE);

            int femaleIndex = flatIndexFor(selection, dimensionSizes);
            float femaleValue = getValue(Integer.toString(femaleIndex), root);

            dataList.add(new YearData(Integer.parseInt(key), maleValue, femaleValue));
        });
        return new CountryDataSet(dataList, getCurrentCountryName(countryISO));
    }

    private int flatIndexFor(Map<String, Integer> selection, Map<String, Integer> dimensionSizes) {
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
                .getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(AppConfig.TIME_PERIOD_MEMBER_NAME)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject(AppConfig.INDEX_MEMBER_NAME);

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
        JsonElement e = root.getAsJsonObject(AppConfig.VALUE_MEMBER_NAME).get(key);
        if (e == null || e.isJsonNull()) {
            return Float.NaN;
        }
        return e.getAsFloat();
    }

    public String getCurrentCountryName(String countryISO) {
        return root
                .getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(AppConfig.GEO_MEMBER_NAME)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject("label")
                .get(countryISO).getAsString();
    }
}
