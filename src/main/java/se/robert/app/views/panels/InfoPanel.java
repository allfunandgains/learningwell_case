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

    private GenderLegendPanel genderLegendPanel;

    /**
     * Constructs the panel and set up the GUI components.
     */
    public InfoPanel() {
        infoLabel = new JLabel();
        infoLabel.setFont(AppConfig.STANDARD_FONT);
        infoLabel.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH, AppConfig.INFO_PANEL_HEIGHT));
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
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
        if (genderLegendPanel == null) {
            genderLegendPanel = new GenderLegendPanel();
            this.add(genderLegendPanel);
        }
        genderLegendPanel.setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * Hides the legend panel.
     */
    public void hideLegendPanel() {
        if (genderLegendPanel != null) {
            genderLegendPanel.setVisible(false);
        }
    }

    /**
     * Sets the info text.
     * @param countryName the country name.
     */
    public void setInfoText(String countryName) {
        infoLabel.setText(
                "<html>"
                        + AppConfig.INFO_PANEL_LABEL_TEXT
                        + countryName
                        + "</html>"
        );
    }

    /**
     * Sets the text of the infoLabel field to indicate loading.
     */
    public void setLoadingText() {
        infoLabel.setText("Loading...");
    }

    /**
     * Clears the infoLabel field text.
     */
    public void clearInfoText() {
        infoLabel.setText("");
    }
}
