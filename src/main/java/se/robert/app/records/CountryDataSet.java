package se.robert.app.records;

import java.util.LinkedList;

/**
 * A record containing a list of {@link YearData} objects and the
 * corresponding country name.
 * @param data list of {@link YearData} objects containing data
 *             for each year in a time series.
 * @param countryName the name of the country for which the data is relevant.
 */
public record CountryDataSet(
        LinkedList<YearData> data,
        String countryName
) {
}
