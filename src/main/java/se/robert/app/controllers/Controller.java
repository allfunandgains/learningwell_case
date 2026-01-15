package se.robert.app.controllers;

import se.robert.app.api.ApiClient;
import se.robert.app.models.Model;
import se.robert.app.models.exceptions.ModelException;
import se.robert.app.records.YearData;
import se.robert.app.views.View;

import javax.swing.SwingWorker;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Controller {

    private final View view;
    private final Model  model;

    public Controller() {
        view = new View();
        model = new Model(new ApiClient());
        addActionListeners();
    }

    private void addActionListeners() {
        view.getLeftPanel().getInputPanel().getDisplayButton().addActionListener((e) -> showData());
    }

    private void showData() {
        String input = view.getLeftPanel()
                .getInputPanel()
                .getInputField()
                .getText()
                .toUpperCase(Locale.ROOT);

        view.getLeftPanel().getInfoPanel().getCountryLabel().setText("Loading...");

        SwingWorker<LinkedList<YearData>, Void> worker = new SwingWorker<>() {

            @Override
            protected LinkedList<YearData> doInBackground() throws Exception {
                return model.generateDataSet(input);
            }

            @Override
            protected void done() {
                try {
                    LinkedList<YearData> data = get();

                    view.getLeftPanel()
                            .getInfoPanel()
                            .getCountryLabel()
                            .setText(model.getCurrentCountryName());

                    view.showData(data);

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
                } finally {
                    view.getLeftPanel().getInfoPanel().getCountryLabel().setText("");
                }
            }
        };

        worker.execute();
    }
}
