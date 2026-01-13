package se.robert.app.utilities;

import java.awt.Font;

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

}
