package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;

public class InfoPanel extends JPanel {

    JLabel infoLabel;
    JLabel countryLabel;

    public InfoPanel() {
        infoLabel = new JLabel("Displaying data for:");
        countryLabel = new JLabel("No country selected.");
        infoLabel.setFont(AppConfig.STANDARD_FONT);
        countryLabel.setFont(AppConfig.STANDARD_FONT);
        this.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH,AppConfig.INFO_PANEL_HEIGHT));

        this.add(infoLabel);
        this.add(countryLabel);
    }

    public JLabel getCountryLabel() {
        return countryLabel;
    }
}
