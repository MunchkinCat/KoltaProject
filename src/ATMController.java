import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    @FXML
    private Text actiontarget;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        actiontarget.setText("Sign in button pressed");
    }
}