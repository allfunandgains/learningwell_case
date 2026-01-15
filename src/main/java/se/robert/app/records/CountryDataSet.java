package se.robert.app.records;

import java.util.LinkedList;

public record CountryDataSet(
        LinkedList<YearData> data,
        String countryName
) {
}
