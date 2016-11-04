import gui.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    private final int LINE_ONE = 0;
    private final int LINE_TWO = 1;
    private final int LINE_THREE = 2;
    private final int LINE_FOUR = 3;

    private int sourceScreen = 1;

    @FXML
    private Label screen_row1, screen_row2,
                     screen_row3, screen_row4;

    @FXML
    private Button button_cardslot, button_printer, button_deposit,
                   button_dispenser, button_enter, button_clear, button_cancel;

    @FXML
    private Button button_screen_left1, button_screen_left2, button_screen_left3, button_screen_left4,
                   button_screen_right1, button_screen_right2, button_screen_right3, button_screen_right4;

    @FXML
    private Button button_num1, button_num2, button_num3,
                   button_num4, button_num5, button_num6,
                   button_num7, button_num8, button_num9,
                   button_num0;

    @FXML
    protected void handleCardInsertion(ActionEvent event) {

        // If card inserted on welcome screen, proceed.
        // Else, consider as force-removing card,
        // and terminate session, returning to welcome.
        if (sourceScreen == 1) {
            configure(2);
        } else {
            configure(1);
        }
    }

    @FXML
    protected void handleClearButton(ActionEvent event) {
        switch (sourceScreen) {
            case 1:
                break;
            case 2:
                configure(2);
                break;
            default:
                break;
        }
    }

    @FXML
    protected  void handleCancelButton(ActionEvent event){
        switch (sourceScreen){
            case 1:
                break;
            case 2:
                configure(1);
                break;
        }
    }

    private void configure(int screenNum) {

        ScreenLoader loader = new ScreenLoader();
        String[] screen = null;
        try {
            screen = loader.getScreen(screenNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        screen_row1.setText(screen[LINE_ONE]);
        screen_row2.setText(screen[LINE_TWO]);
        screen_row3.setText(screen[LINE_THREE]);
        screen_row4.setText(screen[LINE_FOUR]);

        resetAllButtons();

        // Configures button actions according to screen.
        // Also
        switch (screenNum) {
            case 1:
                button_cardslot.setOnAction(this::handleCardInsertion);
                break;
            case 2:
                button_cardslot.setOnAction(this::handleCardInsertion);
                button_clear.setOnAction(this::handleClearButton);
                button_clear.setOnAction(this::handleCancelButton);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            default:
                break;
        }

        sourceScreen = screenNum;
    }

    private void resetAllButtons() {
        button_cardslot.setOnAction(null);
        button_printer.setOnAction(null);
        button_deposit.setOnAction(null);
        button_dispenser.setOnAction(null);
        button_enter.setOnAction(null);
        button_clear.setOnAction(null);
        button_cancel.setOnAction(null);
        button_screen_left1.setOnAction(null);
        button_screen_left2.setOnAction(null);
        button_screen_left3.setOnAction(null);
        button_screen_left4.setOnAction(null);
        button_screen_right1.setOnAction(null);
        button_screen_right2.setOnAction(null);
        button_screen_right3.setOnAction(null);
        button_screen_right4.setOnAction(null);
        button_num1.setOnAction(null);
        button_num2.setOnAction(null);
        button_num3.setOnAction(null);
        button_num4.setOnAction(null);
        button_num5.setOnAction(null);
        button_num6.setOnAction(null);
        button_num7.setOnAction(null);
        button_num8.setOnAction(null);
        button_num9.setOnAction(null);
        button_num0.setOnAction(null);
    }
}