package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A JPanel responsible for retrieving user input and triggering data display events.
 *
 * @author Robert Kullman
 */
public class InputPanel extends JPanel {

    /** Text field used for user input. */
    private final JTextField inputField;

    /** Button used to trigger chart or data display. */
    private final JButton displayButton;

    /**
     * Constructs the panel and initializes UI components.
     */
    public InputPanel() {
        this.setPreferredSize(new java.awt.Dimension(AppConfig.INPUT_PANEL_WIDTH, AppConfig.INPUT_PANEL_HEIGHT));
        this.setLayout(new java.awt.FlowLayout());

        inputField = new javax.swing.JTextField(AppConfig.INPUT_FIELD_COLUMNS);
        inputField.setFont(AppConfig.STANDARD_FONT);

        JLabel inputLabel = new JLabel(AppConfig.INPUT_LABEL_TEXT);
        inputLabel.setFont(AppConfig.STANDARD_FONT);
        inputLabel.setLabelFor(inputField);

        displayButton = new javax.swing.JButton(AppConfig.DISPLAY_CHART_BUTTON_TEXT);
        displayButton.setFont(AppConfig.STANDARD_FONT);

        this.add(inputLabel);
        this.add(inputField);
        this.add(displayButton);

        this.setVisible(true);
    }

    /**
     * Accessor for the inputField member.
     * @return the inputField JTextField instance.
     */
    public JTextField getInputField() {
        return inputField;
    }

    /**
     * Accessor for the displayButton member.
     * @return the inputField JButton instance.
     */
    public JButton getDisplayButton() {
        return displayButton;
    }
}
