package se.robert.app.records;

/**
 * Immutable data transfer object representing values for a single year.
 *  Holds values used to render a bar chart.
 * @param year the year integer value.
 * @param maleValue male percentage value as a float.
 * @param femaleValue female percentage value as a float.
 */
public record YearData(
        int year,
        double maleValue,
        double femaleValue
) {
}
