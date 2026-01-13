package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {

    JTextField inputField;
    JLabel inputLabel;
    JButton displayButton;

    public InputPanel() {
        this.setPreferredSize(new java.awt.Dimension(AppConfig.INPUT_PANEL_WIDTH, AppConfig.INPUT_PANEL_HEIGHT));
        this.setLayout(new java.awt.FlowLayout());

        inputField = new javax.swing.JTextField( AppConfig.INPUT_FIELD_COLUMNS);
        inputField.setFont(AppConfig.STANDARD_FONT);



        inputLabel = new javax.swing.JLabel("Specify ISO code: ");
        inputLabel.setFont(AppConfig.STANDARD_FONT);

        inputLabel.setLabelFor(inputField);
        displayButton = new javax.swing.JButton("Display chart");
        displayButton.setFont(AppConfig.STANDARD_FONT);


        this.add(inputLabel);
        this.add(inputField);
        this.add(displayButton);

        this.setVisible(true);
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getDisplayButton() {
        return displayButton;
    }
}
