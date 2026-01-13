package se.robert.app;

import se.robert.app.controllers.Controller;

import javax.swing.SwingUtilities;

/**
 * The application entry point, responsible for initializing the Controller class.
 * @author Robert Kullman
 */
public class Main {
    static void main() {
        SwingUtilities.invokeLater(Controller::new);
    }
}
