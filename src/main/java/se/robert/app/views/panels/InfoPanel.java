package se.robert.app.views.panels;

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
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        countryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        this.setPreferredSize(new Dimension(300,250));

        this.add(infoLabel);
        this.add(countryLabel);
    }

    public JLabel getCountryLabel() {
        return countryLabel;
    }
}
