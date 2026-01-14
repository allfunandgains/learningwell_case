package se.robert.app.controllers;

import se.robert.app.api.ApiClient;
import se.robert.app.models.Model;
import se.robert.app.views.View;

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
         System.out.println(input);
         model.generateDataSet(input);
    }
}
