package se.robert.app.views;

import javax.swing.JFrame;

/**
 * Represents the main view of the application.
 * Responsible for rendering the user interface and forwarding
 * user interactions to the controller.
 *
 * @author Robert Kullman
 */
public class View extends JFrame {

    /**
     * Contructor for the View class.
     */
    public View() {
        initUI();
    }

    /**
     * Responsible for initializing the GUI.
     */
    private void initUI() {
        this.setTitle("View");
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
