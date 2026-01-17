package se.robert.app.views;

import se.robert.app.records.YearData;
import se.robert.app.utilities.AppConfig;
import se.robert.app.views.menus.MainMenuBar;
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

    /** The left GUI panel. */
    private LeftPanel leftPanel;

    /** The right GUI panel. */
    private RightPanel rightPanel;

    /** The menu bar. */
    private MainMenuBar mainMenuBar;

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
        mainMenuBar = new MainMenuBar();

        this.add(leftPanel);
        this.add(rightPanel);
        this.setJMenuBar(mainMenuBar);

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

    /**
     * Clears the chart area in the diagram panel.
     */
    public void clearData() {
        rightPanel.clearChart();
    }

    /**
     * Accessor for the main menu bar.
     * @return a JMenuBar object.
     */
    public MainMenuBar getMainMenuBar() {
        return mainMenuBar;
    }

    /**
     * Shows a dialog with app usage instructions.
     */
    public void showInstructionsDialog() {
        JOptionPane.showMessageDialog(this, AppConfig.INSTRUCTIONS_TEXT, AppConfig.INSTRUCTIONS_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }
}
