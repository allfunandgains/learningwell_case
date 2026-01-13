package se.robert.app.views.panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;

public class LeftPanel extends JPanel {

    InputPanel inputPanel;
    InfoPanel infoPanel;

    public LeftPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inputPanel = new InputPanel();
        infoPanel = new InfoPanel();

        this.add(inputPanel);
        this.add(infoPanel);
        this.setBackground(Color.WHITE);
    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
