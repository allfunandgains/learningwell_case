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
     * @param args Command arguments. Allows for optional ISO code input
     *             if starting the app from CLI.
     */
    public static void main(String[] args) {
        String initialIso = (args.length > 0) ? args[0].trim().toUpperCase() : null;
        SwingUtilities.invokeLater(() -> {Controller controller = new Controller(initialIso);});
    }
}
