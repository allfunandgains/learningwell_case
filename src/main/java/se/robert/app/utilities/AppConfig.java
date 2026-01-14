package se.robert.app.utilities;

import java.awt.Font;
import java.util.List;

/**
 * This class contains configuration data.
 *
 * @author Robert Kullman
 */
public final class AppConfig {

    /**
     * Private constructor to prevent instantiation.
     */
    private AppConfig() {}

    public static final Font STANDARD_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final int INFO_PANEL_WIDTH = 300;
    public static final int INFO_PANEL_HEIGHT = 250;
    public static final int INPUT_PANEL_WIDTH = 300;
    public static final int INPUT_PANEL_HEIGHT = 250;
    public static final int INPUT_FIELD_COLUMNS = 10;

    public static final String API_ADDRESS = "https://webgate.ec.europa.eu/empl/redisstat/api/dissemination/sdmx/2.1/data/lmp_ind_actru?format=json&compressed=false";

    public static final List<String> DIMENSION_ORDER = List.of(
            "FREQ", "UNIT", "AGE", "SEX", "REGIS_ES", "LMP_TYPE", "GEO", "TIME_PERIOD"
    );
}
