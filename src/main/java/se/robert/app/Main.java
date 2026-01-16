package se.robert.app;

import se.robert.app.controllers.Controller;

import javax.swing.SwingUtilities;

/**
 * The application entry point, responsible for initializing the Controller class.
 * @author Robert Kullman
 */
public final class Main {

    /**
     * Private constructor to prevent instantiation.
     */
    private Main() { }

    /**
     * Main method of the application, serving as its entry point.
     * @param args Command arguments. Not relevant for this solution.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
