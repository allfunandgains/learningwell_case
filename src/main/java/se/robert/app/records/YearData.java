package se.robert.app.records;

/**
 * Immutable data transfer object representing values for a single year.
 * Holds values used to render a bar chart.
 *
 * @author Robert Kullman
 */
public record YearData(
        int year,
        double maleValue,
        double femaleValue
) {
}
