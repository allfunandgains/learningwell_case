package se.robert.app.controllers;

import se.robert.app.api.ApiClient;
import se.robert.app.models.Model;
import se.robert.app.models.exceptions.ModelException;
import se.robert.app.records.CountryDataSet;
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

    /** The main view instance. */
    private final View view;

    /** The model instance. */
    private final Model  model;

    /**
     * Constructs the Controller and initializes the application.
     * @param initialIso optional ISO country code entered as CLI
     *                   argument. If present, the corresponding
     *                   country data is loaded automatically.
     */
    public Controller(String initialIso) {
        view = new View();
        model = new Model(new ApiClient());
        addActionListeners();

        if (initialIso != null) {
            view.getLeftPanel().getInputPanel().getInputField().setText(initialIso);
            showData();
        }
    }

    /**
     * Adds actionListeners to the GUI.
     */
    private void addActionListeners() {
        view.getLeftPanel().getInputPanel().getDisplayButton().addActionListener((e) -> showData());
        view.getMainMenuBar().getExitMenuItem().addActionListener((e) -> System.exit(0));
        view.getMainMenuBar().getInstructionsMenuItem().addActionListener((e) -> view.showInstructionsDialog());
    }

    /**
     * Performs asynchronous data retrieval based on user input.
     * Data retrieval is delegated to a {@link SwingWorker} thread.
     * Upon completion, data is displayed in the view.
     */
    private void showData() {
        view.clearData();
        String input = view.getLeftPanel()
                .getInputPanel()
                .getInputField()
                .getText()
                .toUpperCase(Locale.ROOT);

        view.getLeftPanel().getInfoPanel().hideLegendPanel();
        view.getLeftPanel().getInfoPanel().setLoadingText();

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
                            .setInfoText(data.countryName());

                    view.showData(data.data());

                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();

                    if (cause instanceof ModelException me) {
                        view.showInfoDialog("Exception", me.getMessage());
                        view.getLeftPanel().getInfoPanel().clearInfoText();
                    } else {
                        view.showInfoDialog(
                                "Error",
                                "An unexpected error occurred while fetching data."
                        );
                        view.getLeftPanel().getInfoPanel().clearInfoText();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    view.getLeftPanel().getInfoPanel().clearInfoText();
                }
            }
        };

        worker.execute();
    }
}
