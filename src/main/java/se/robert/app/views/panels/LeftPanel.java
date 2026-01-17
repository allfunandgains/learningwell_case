package se.robert.app.views.panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * A JPanel container responsible for housing input- and information
 * panels in the left-most part of the GUI.
 *
 * @author Robert Kullman
 */
public class LeftPanel extends JPanel {

    /** Panel used for collecting user input. */
    private final InputPanel inputPanel;

    /** Panel used for displaying informational text, status messages, and chart legends. */
    private final InfoPanel infoPanel;

    /**
     * Constructs the panel and initializes its child panels.
     */
    public LeftPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inputPanel = new InputPanel();
        infoPanel = new InfoPanel();

        this.add(inputPanel);
        this.add(infoPanel);
        this.setBackground(Color.WHITE);
    }

    /**
     * Accessor for the inputPanel member.
     * @return the InputPanel instance.
     */
    public InputPanel getInputPanel() {
        return inputPanel;
    }

    /**
     * Accessor for the infoPanel member.
     * @return the InfoPanel instance.
     */
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
