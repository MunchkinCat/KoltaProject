package gui.controller;

import javafx.event.ActionEvent;

/**
 * Created by egrstudent on 11/9/16.
 */
public class ScreenButtonHandler {

    private final ActionEvent EVENT;

    public ScreenButtonHandler(ATMController controller, ActionEvent event) {
        this.EVENT = event;
    }
}
