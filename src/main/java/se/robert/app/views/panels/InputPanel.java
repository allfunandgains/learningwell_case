package se.robert.app.views.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {

    JTextField inputField;
    JLabel inputLabel;
    JButton displayButton;

    public InputPanel() {
        this.setPreferredSize(new java.awt.Dimension(300, 250));
        this.setLayout(new java.awt.FlowLayout());

        inputField = new javax.swing.JTextField( 10);
        inputField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));



        inputLabel = new javax.swing.JLabel("Specify ISO code: ");
        inputLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));

        inputLabel.setLabelFor(inputField);
        displayButton = new javax.swing.JButton("Display chart");
        displayButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));


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
