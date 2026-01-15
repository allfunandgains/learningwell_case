package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;

public class InfoPanel extends JPanel {

    JLabel infoLabel;

    public InfoPanel() {
        infoLabel = new JLabel();
        infoLabel.setFont(AppConfig.STANDARD_FONT);
        this.setPreferredSize(new Dimension(AppConfig.INFO_PANEL_WIDTH,AppConfig.INFO_PANEL_HEIGHT));

        this.add(infoLabel);
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }
}
