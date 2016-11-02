import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.awt.*;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    @FXML
    private TextArea atm_display;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        atm_display.setText("Hey, a thing!");
    }
}