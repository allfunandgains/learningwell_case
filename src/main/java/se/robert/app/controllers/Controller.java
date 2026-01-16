package se.robert.app.controllers;

import se.robert.app.api.ApiClient;
import se.robert.app.models.Model;
import se.robert.app.models.exceptions.ModelException;
import se.robert.app.records.CountryDataSet;
import se.robert.app.utilities.AppConfig;
import se.robert.app.views.View;

import javax.swing.SwingWorker;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * The Controller coordinates communication between the View and the Model in the MVC
 * architecture.
 *
 * @author Robert Kullman
 */
public class Controller {

    private final View view;
    private final Model  model;

    /**
     * Constructs the Controller and initializes the application.
     */
    public Controller() {
        view = new View();
        model = new Model(new ApiClient());
        addActionListeners();
    }

    /**
     * Adds actionListeners to the GUI.
     */
    private void addActionListeners() {
        view.getLeftPanel().getInputPanel().getDisplayButton().addActionListener((e) -> showData());
    }

    /**
     * Performs asynchronous data retrieval based on user input.
     * Data retrieval is delegated to a {@link SwingWorker} thread.
     * Upon completion, data is displayed in the view.
     */
    private void showData() {
        String input = view.getLeftPanel()
                .getInputPanel()
                .getInputField()
                .getText()
                .toUpperCase(Locale.ROOT);

        view.getLeftPanel().getInfoPanel().getInfoLabel().setText("Loading...");

        SwingWorker<CountryDataSet, Void> worker = new SwingWorker<>() {

            @Override
            protected CountryDataSet doInBackground() throws Exception {
                return model.generateDataSet(input);
            }

            @Override
            protected void done() {
                try {
                    CountryDataSet data = get();

                    view.getLeftPanel()
                            .getInfoPanel()
                            .getInfoLabel()
                            .setText(AppConfig.INFO_PANEL_LABEL_TEXT + model.getCurrentCountryName(input.toUpperCase(Locale.ROOT)));

                    view.showData(data.data());

                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();

                    if (cause instanceof ModelException me) {
                        view.showInfoDialog("Exception", me.getMessage());
                    } else {
                        view.showInfoDialog(
                                "Error",
                                "An unexpected error occurred while fetching data."
                        );
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    view.getLeftPanel().getInfoPanel().getInfoLabel().setText("");
                }
            }
        };

        worker.execute();
    }
}
