package se.robert.app.controllers;

import se.robert.app.models.Model;
import se.robert.app.views.View;

public class Controller {

    private final View view;
    private final Model  model;

    public Controller() {
        view = new View();
        model = new Model();
    }
}
