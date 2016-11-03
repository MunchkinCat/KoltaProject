import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    @FXML
    private Text screen_line1, screen_line2,
                     screen_line3, screen_line4;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        screen_line1.setText("Hello this is a test...");
    }
}