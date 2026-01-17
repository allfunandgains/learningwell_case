package se.robert.app.views.menus;

import se.robert.app.utilities.AppConfig;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Represents the menu bar of the application.
 *
 * @author Robert Kullman
 */
public class MainMenuBar extends JMenuBar {

    /** File menu. */
    private final JMenu fileMenu;

    /** Help menu. */
    private final JMenu helpMenu;

    /** Menu item for exiting the application. */
    private final JMenuItem exitMenuItem;

    /** Menu item for showing usage instructions. */
    private final JMenuItem instructionsMenuItem;

    /**
     * Constructs the menu bar and sets its menus with pertinent
     * menu items.
     */
    public MainMenuBar() {
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        exitMenuItem = new JMenuItem("Exit");
        instructionsMenuItem = new JMenuItem(AppConfig.INSTRUCTIONS_TITLE);

        fileMenu.add(exitMenuItem);
        helpMenu.add(instructionsMenuItem);

        add(fileMenu);
        add(helpMenu);
    }

    /**
     * Accessor for the exit menu item.
     * @return a {@link JMenuItem} object.
     */
    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    /**
     * Accessor for the instructions menu item.
     * @return a {@link JMenuItem} object.
     */
    public JMenuItem getInstructionsMenuItem() {
        return instructionsMenuItem;
    }
}
