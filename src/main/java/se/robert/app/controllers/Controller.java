package se.robert.app.controllers;

import se.robert.app.api.ApiClient;
import se.robert.app.models.Model;
import se.robert.app.models.exceptions.ModelException;
import se.robert.app.records.YearData;
import se.robert.app.views.View;

import java.util.LinkedList;
import java.util.Locale;

public class Controller {

    private final View view;
    private final Model  model;

    public Controller() {
        view = new View();
        model = new Model(new ApiClient());
        addActionListeners();
    }

    private void addActionListeners() {
        view.getLeftPanel().getInputPanel().getDisplayButton().addActionListener((e) -> getUserInput());
    }

    private void getUserInput() {

        String input = view.getLeftPanel().getInputPanel().getInputField().getText();

        try {
            LinkedList<YearData> data = model.generateDataSet(input.toUpperCase(Locale.ROOT));
            view.getLeftPanel().getInfoPanel().getCountryLabel().setText(model.getCurrentCountryName());
            view.showData(data);
        } catch (ModelException e) {
            view.showInfoDialog("Exception", e.getMessage());
        }


    }
}
