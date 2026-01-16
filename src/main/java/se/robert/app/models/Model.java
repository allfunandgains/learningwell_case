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

    /** Client used to retrieve JSON data from the API. */
    private final ApiClient apiClient;

    /** Root JSON object representing the parsed API response. */
    private JsonObject root;

    /**
     * Maps dimension names to their respective sizes.
     * The order corresponds to the API's dimension order.
     */
    private Map<String, Integer> dimensionSizes;

    /** Ordered map of year labels to their internal index values.*/
    private  LinkedHashMap<String, Integer> years;

    /**
     * Constructs the model and sets the api client via dependency injection.
     * @param client the ApiClient to be used for data retrieval.
     */
    public Model(ApiClient client) {
        this.apiClient = client;
    }

    /**
     * Attempts to fetch and parse the data into the root field.
     */
    private void loadJsonData() throws IOException, InterruptedException, ModelException {
        String jsonData = apiClient.getData(AppConfig.API_ADDRESS);
        root = JsonParser.parseString(jsonData).getAsJsonObject();
        dimensionSizes = buildDimensionSizes();
        years = buildYearsMap();
    }

    /**
     * Helper method for retrieving specific dimensions data
     * from the JSON-stat response.
     * @param rootObject The JSON Object containing the data.
     * @param dimension Name of the dimension to access.
     * @param key The key for what specific dimension data to retrieve.
     * @return Integer value for the specific data point.
     */
    private int getSpecificDimensionData(JsonObject rootObject, String dimension, String key) {
        return rootObject.getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(dimension)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject(AppConfig.INDEX_MEMBER_NAME)
                .get(key)
                .getAsInt();
    }

    /**
     * Builds a mapping between dimension identifiers and their sizes.
     * @return an ordered map of dimension names to dimension sizes.
     * @throws ModelException if the root object is null or inconsistent.
     */
    private LinkedHashMap<String, Integer> buildDimensionSizes() throws ModelException {
        if (root == null) {
            throw new ModelException("Root object is null.");
        }

        JsonArray dimensionIDs = root.get(AppConfig.ID_MEMBER_NAME).getAsJsonArray();
        JsonArray sizes = root.get(AppConfig.SIZE_MEMBER_NAME).getAsJsonArray();

        if (dimensionIDs.size() != sizes.size()) {
           throw new ModelException("DimensionIds and dimension sizes lists differ in length.");
        }

        LinkedHashMap<String, Integer> dimensionSizesMap = new LinkedHashMap<>();

        for (int i = 0; i < dimensionIDs.size(); i++) {
            dimensionSizesMap.put(dimensionIDs.get(i).getAsString(), sizes.get(i).getAsInt());
        }
        return dimensionSizesMap;
    }

    /**
     * Creates a base selection containing all fixed dimension values
     * except geography and time period.
     * @return a map of dimension names to index values.
     */
    private Map<String, Integer> baseSelection() {
        Map<String, Integer> m = new HashMap<>();
        m.put(AppConfig.FREQ_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.FREQ_MEMBER_NAME, AppConfig.FREQUENCY_ANNUAL_INDEX_VALUE));
        m.put(AppConfig.UNIT_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.UNIT_MEMBER_NAME, AppConfig.UNIT_RATE_INDEX_VALUE));
        m.put(AppConfig.AGE_MEMBER_NAME,  getSpecificDimensionData(root, AppConfig.AGE_MEMBER_NAME, AppConfig.AGE_INDEX_TOTAL));
        m.put(AppConfig.REGIS_ES_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.REGIS_ES_MEMBER_NAME, AppConfig.REGISTERED_UNEMPLOYED_INDEX_VALUE));
        m.put(AppConfig.LMP_TYPE_MEMBER_NAME, getSpecificDimensionData(root, AppConfig.LMP_TYPE_MEMBER_NAME, AppConfig.LMP_TYPE_TOTAL_INDEX_VALUE));
        return m;
    }

    /**
     * Extends the base selection with a specific country.
     * @param countryISO the ISO country code.
     * @return a populated selection map including geography.
     */
    private Map<String, Integer> baseSelectionWithCountry(String countryISO) {
        Map<String, Integer> selection = baseSelection();
        selection.put(AppConfig.GEO_MEMBER_NAME,  getSpecificDimensionData(root, AppConfig.GEO_MEMBER_NAME, countryISO));
        return selection;
    }

    /**
     * Generates a dataset containing unemployment data for a given country. Each year in the
     * time series contains data for males and females.
     * @param countryISO the ISO code of the country.
     * @return a {@link CountryDataSet} containing yearly data.
     * @throws ModelException id data processing fails.
     */
    public CountryDataSet generateDataSet(String countryISO) throws ModelException {
        if (root == null) {
            try {
                loadJsonData();
            } catch (IOException | InterruptedException | ModelException e) {
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

    /**
     * Calculates the row-major ordering index for a multidimensional selection.
     * @param selection map of dimension member names to indices.
     * @param dimensionSizesMap map of dimension names to sizes.
     * @return a flattened index for the specified selection.
     */
    private int flatIndexFor(Map<String, Integer> selection, Map<String, Integer> dimensionSizesMap) {
        List<Integer> indices = AppConfig.DIMENSION_ORDER.stream()
                .map(selection::get)
                .toList();

        List<Integer> sizes = AppConfig.DIMENSION_ORDER.stream()
                .map(dimensionSizesMap::get)
                .toList();

        return calculateFlatIndex(indices, sizes);
    }

    /**
     * Builds an ordered map of the time series available in the root JSON data object.
     * @return an ordered mapping of years to indices.
     */
    private LinkedHashMap<String, Integer> buildYearsMap() {
        LinkedHashMap<String, Integer> yearsMap = new LinkedHashMap<>();

        JsonObject yearIndex = root
                .getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(AppConfig.TIME_PERIOD_MEMBER_NAME)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject(AppConfig.INDEX_MEMBER_NAME);

        yearIndex.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getKey())))
                .forEach(e -> yearsMap.put(e.getKey(), e.getValue().getAsInt()));

        return yearsMap;
    }

    /**
     * Calculates the flat index from specified indices and sizes.
     * @param indices a list of the indices for each dimension.
     * @param sizes the size of each dimension.
     * @return the computed index.
     */
    private int calculateFlatIndex(List<Integer> indices, List<Integer> sizes) {
        int idx = 0;
        for (int k = 0; k < sizes.size(); k++) {
            idx = idx * sizes.get(k) + indices.get(k);
        }
        return idx;
    }

    /**
     * Retrieves a value from the JSON value array corresponding to the specified index key.
     * @param key the row-major order key.
     * @param rootObject the rootObject JSON object.
     * @return the value, or NaN if missing.
     */
    private float getValue(String key, JsonObject rootObject) {
        JsonElement e = rootObject.getAsJsonObject(AppConfig.VALUE_MEMBER_NAME).get(key);
        if (e == null || e.isJsonNull()) {
            return Float.NaN;
        }
        return e.getAsFloat();
    }

    /**
     * Retrieves the country name corresponding to a specific ISO code.
     * @param countryISO the country ISO code.
     * @return the country name.
     */
    public String getCurrentCountryName(String countryISO) {
        return root
                .getAsJsonObject(AppConfig.DIMENSION_MEMBER_NAME)
                .getAsJsonObject(AppConfig.GEO_MEMBER_NAME)
                .getAsJsonObject(AppConfig.CATEGORY_MEMBER_NAME)
                .getAsJsonObject("label")
                .get(countryISO).getAsString();
    }
}
