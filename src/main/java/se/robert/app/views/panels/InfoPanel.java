package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * A JPanel used to display information and a chart legend to the user.
 */
public class InfoPanel extends JPanel {

    /** JLabel for displaying information to the user. */
    private final JLabel infoLabel;

    /**
     * Constructs the panel and set up the GUI components.
     */
    public InfoPanel() {
        infoLabel = new JLabel();
        infoLabel.setFont(AppConfig.STANDARD_FONT);
        this.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH, AppConfig.INFO_PANEL_HEIGHT));

        this.add(infoLabel);
    }

    /**
     * Accessor for the infoLabel field.
     * @return the infoLabel instance.
     */
    public JLabel getInfoLabel() {
        return infoLabel;
    }

    /**
     * Creates and adds a GenderLegendPanel object to this JPanel.
     */
    public void showLegendPanel() {
        GenderLegendPanel genderLegendPanel = new GenderLegendPanel();
        this.add(genderLegendPanel);
    }
}
