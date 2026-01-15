package se.robert.app.utilities;

import java.awt.Color;
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
    public static final Font YEAR_LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 11);

    // Data retrieval identifiers
    public static final String VIEW_TITLE = "Activation of registered unemployed";
    public static final String DIMENSION_MEMBER_NAME = "dimension";
    public static final String TIME_PERIOD_MEMBER_NAME = "TIME_PERIOD";
    public static final String CATEGORY_MEMBER_NAME = "category";
    public static final String INDEX_MEMBER_NAME = "index";
    public static final String VALUE_MEMBER_NAME = "value";
    public static final String TIME_PERIOD_KEY = "TIME_PERIOD";
    public static final String SEX_KEY = "SEX";
    public static final int SEX_MALE_VALUE = 1;
    public static final int SEX_FEMALE_VALUE = 2;
    public static final String ID_MEMBER_NAME = "id";
    public static final String SIZE_MEMBER_NAME = "size";
    public static final String FREQ_MEMBER_NAME = "FREQ";
    public static final String UNIT_MEMBER_NAME = "UNIT";
    public static final String AGE_MEMBER_NAME = "AGE";
    public static final String REGIS_ES_MEMBER_NAME = "REGIS_ES";
    public static final String LMP_TYPE_MEMBER_NAME = "LMP_TYPE";
    public static final String GEO_MEMBER_NAME = "GEO";
    public static final String FREQUENCY_ANNUAL_INDEX_VALUE = "A";
    public static final String UNIT_RATE_INDEX_VALUE = "RT";
    public static final String AGE_INDEX_TOTAL = "TOTAL";
    public static final String REGISTERED_UNEMPLOYED_INDEX_VALUE = "REG_UNE";
    public static final String LMP_TYPE_TOTAL_INDEX_VALUE = "TOT2_7";


    // GUI
    public static final Color MALE_BAR_COLOR = new Color(80, 140, 220);
    public static final Color FEMALE_BAR_COLOR = new Color(220, 120, 160);
    public static final int YEAR_LABEL_HEIGHT = 15;
    public static final int PADDING_TOP = 8;
    public static final int PADDING_BOTTOM = 8;

    public static final int YEAR_BAR_PANEL_DISTANCE = 16;

    public static final int INFO_PANEL_WIDTH = 300;
    public static final int INFO_PANEL_HEIGHT = 250;
    public static final int INPUT_PANEL_WIDTH = 300;
    public static final int INPUT_PANEL_HEIGHT = 250;
    public static final int INPUT_FIELD_COLUMNS = 10;
    public static final double PERCENT_MAX = 100.0;

    public static final String API_ADDRESS = "https://webgate.ec.europa.eu/empl/redisstat/api/dissemination/sdmx/2.1/data/lmp_ind_actru?format=json&compressed=false";

    public static final List<String> DIMENSION_ORDER = List.of(
            "FREQ", "UNIT", "AGE", "SEX", "REGIS_ES", "LMP_TYPE", "GEO", "TIME_PERIOD"
    );
}
