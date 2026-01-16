package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple legend panel that displays entry colors for Male and Female.
 *
 * @author Robert Kullman
 */
public class GenderLegendPanel extends JPanel {

    /**
     * Initializes the panel components and adds them to the gender panel.
     */
    public GenderLegendPanel() {
        setOpaque(false);

        JLabel maleMarker = new JLabel("■");
        maleMarker.setForeground(AppConfig.MALE_BAR_COLOR);
        JLabel maleLabel = new JLabel("Male");
        maleMarker.setFont(AppConfig.STANDARD_FONT);
        JLabel femaleMarker = new JLabel("■");
        femaleMarker.setForeground(AppConfig.FEMALE_BAR_COLOR);
        JLabel femaleLabel = new JLabel("Female");
        femaleMarker.setFont(AppConfig.STANDARD_FONT);

        add(maleMarker);
        add(maleLabel);
        add(femaleMarker);
        add(femaleLabel);
    }

}

