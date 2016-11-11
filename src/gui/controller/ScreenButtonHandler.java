package gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * Created by egrstudent on 11/9/16.
 */
public class ScreenButtonHandler {

    private final ActionEvent EVENT;
    private final ATMController CONTROLLER;

    public ScreenButtonHandler(ATMController controller, ActionEvent event) {
        this.EVENT = event;
        this.CONTROLLER = controller;
    }

    public ScreenOption newTransaction() {
        if (CONTROLLER.isScreenButton((Button) EVENT.getSource(), Side.LEFT, 4)) {
            return ScreenOption.YES;
        } else if (CONTROLLER.isScreenButton((Button) EVENT.getSource(), Side.RIGHT, 4)) {
            return ScreenOption.NO;
        } else {
            return null;
        }
    }

    public ScreenOption selectTransaction() {
        if (CONTROLLER.isScreenButton((Button) EVENT.getSource(), Side.RIGHT, 2)) {
            return ScreenOption.BALANCE;
        } else if (CONTROLLER.isScreenButton((Button) EVENT.getSource(), Side.RIGHT, 3)) {
            return ScreenOption.DEPOSIT;
        } else if (CONTROLLER.isScreenButton((Button) EVENT.getSource(), Side.RIGHT, 4)) {
            return ScreenOption.WITHDRAWAL;
        } else {
            return null;
        }
    }
}