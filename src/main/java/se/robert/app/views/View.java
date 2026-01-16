package se.robert.app.views;

import se.robert.app.records.YearData;
import se.robert.app.utilities.AppConfig;
import se.robert.app.views.panels.LeftPanel;
import se.robert.app.views.panels.RightPanel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.util.LinkedList;

/**
 * Represents the main view of the application.
 * Responsible for rendering the user interface and forwarding
 * user interactions to the controller.
 *
 * @author Robert Kullman
 */
public class View extends JFrame {

    LeftPanel leftPanel;
    RightPanel rightPanel;

    /**
     * Constructor for the View class.
     */
    public View() {
        initUI();
    }

    /**
     * Responsible for initializing the GUI.
     */
    private void initUI() {
        this.setTitle(AppConfig.VIEW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);


        this.setLayout(new FlowLayout());

        leftPanel = new LeftPanel();
        rightPanel = new RightPanel();

        this.add(leftPanel);
        this.add(rightPanel);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * Accessor for the leftPanel member.
     * @return the LeftPanel object contained in this class.
     */
    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

    /**
     * Updates the chart with new data and shows a legend in the information panel.
     * @param data List of {@link YearData} objects.
     */
    public void showData(LinkedList<YearData> data) {
        leftPanel.getInfoPanel().showLegendPanel();
        rightPanel.setData(data);
    }

    /**
     * Shows an information dialog with the specified title and message.
     * @param title the title
     * @param message the message
     */
    public void showInfoDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
