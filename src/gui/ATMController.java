package gui;

import atm.Account;
import atm.AccountManager;
import atm.Authenticator;
import gui.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    private final int LINE_ONE = 0;
    private final int LINE_TWO = 1;
    private final int LINE_THREE = 2;
    private final int LINE_FOUR = 3;

    private int sourceScreen = 1;

    private String pin = "";
    private String card = "";

    private Account account = null;

    @FXML
    private TextField input_deposit, input_card;

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

        Authenticator auth = new Authenticator();
        if (sourceScreen != 1) {
            configure(1);
        } else if (auth.validCard(input_card.getText())) {
            card = input_card.getText();
            configure(2);
        } else {
            configure(4);
        }
    }

    @FXML
    protected void handleEnterButton(ActionEvent event) {
        switch (sourceScreen) {
            case 1:
                break;
            case 2:
                checkPIN();
                break;
            default:
                break;
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
    protected void handleCancelButton(ActionEvent event) {
        switch (sourceScreen){
            case 1:
                break;
            case 2:
                configure(1);
                break;
            default:
                break;
        }
    }

    @FXML
    protected void handleNumberButton(ActionEvent event) {
        switch (sourceScreen) {
            case 1:
                break;
            case 2:
                try {
                    updatePIN(event.getSource().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
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
        // Also deals with some other global vars.
        switch (screenNum) {
            case 1: // Home screen
                setCardTextActive(true);
                input_card.clear();
                pin = "";
                break;
            case 2: // PIN entry
                button_clear.setOnAction(this::handleClearButton);
                button_cancel.setOnAction(this::handleCancelButton);
                button_enter.setOnAction(this::handleEnterButton);
                activateNumpad();
                break;
            case 3: // PIN error
                break;
            case 4: // Card error
                break;
            case 5: // Transaction selection
                break;
            case 6: // Balance display
                break;
            case 7: // Withdrawal request amount entry
                break;
            case 8: // Insufficient funds
                break;
            case 9: // Dispenser denomination error
                break;
            case 10: // Withdrawal processing error
                break;
            case 11: // Cash dispensed
                break;
            case 12: // Deposit processing error
                break;
            case 13: // Ready for deposit
                break;
            case 14: // Printed balance
                break;
            case 15: // End of transaction
                break;
            default:
                break;
        }

        sourceScreen = screenNum;
    }

    private void updatePIN(String source) throws Exception {
        if (sourceScreen != 2) {
            throw new Exception("Only for use with Screen 2");
        }

        int stringLength = source.length();

        // stringLength - 2 because 1 for the 0-indexing, and another for
        // a single quote at the end of the source string representation
        // The returned character is the number which the key represents
        int number = Character.getNumericValue(source.charAt(stringLength - 2));

        String oldPIN = screen_row4.getText();
        int blankCount = 0;
        for (int i = 0; i < oldPIN.length(); i++) {
            if (oldPIN.charAt(i) == '_') {
                blankCount++;
            }
        }

        if (blankCount > 4 || oldPIN.length() > 4) {
            throw new Exception("ERROR: Screen line 4 contains more than 4 chars");
        } else if (blankCount > 0) {
            switch (blankCount - 1) { // -1 because of the new input
                case 0:
                    screen_row4.setText("****");
                    break;
                case 1:
                    screen_row4.setText("_***");
                    break;
                case 2:
                    screen_row4.setText("__**");
                    break;
                case 3:
                    screen_row4.setText("___*");
                    break;
                default:
                    break;
            }
            pin = number + pin;
        } else {
            screen_row1.setText("MAX PIN LENGTH REACHED");
        }
    }

    private void checkPIN() {
        Authenticator auth = new Authenticator();
        AccountManager am = new AccountManager();
        if (auth.validPin(pin, card)) {
            account = am.getAccount(pin, card);
            configure(5);
        } else {
            screen_row1.setText("INCORRECT PIN");
            pin = "";
            screen_row4.setText("____");
        }
    }

    private void activateNumpad() {
        button_num0.setOnAction(this::handleNumberButton);
        button_num1.setOnAction(this::handleNumberButton);
        button_num2.setOnAction(this::handleNumberButton);
        button_num3.setOnAction(this::handleNumberButton);
        button_num4.setOnAction(this::handleNumberButton);
        button_num5.setOnAction(this::handleNumberButton);
        button_num6.setOnAction(this::handleNumberButton);
        button_num7.setOnAction(this::handleNumberButton);
        button_num8.setOnAction(this::handleNumberButton);
        button_num9.setOnAction(this::handleNumberButton);
    }

    private void resetAllButtons() {
        // DEFAULT VALUES FOR ACTION HANDLER MAPPING
        // Set to non-null if it should be mapped for
        // every screen (i.e. button always active)
        button_cardslot.setOnAction(this::handleCardInsertion);
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
        setCardTextActive(false);
        setDepositTextActive(false);
    }

    private void setCardTextActive(boolean active) {
        input_card.setEditable(active);
        input_card.setDisable(!active);
    }

    private void setDepositTextActive(boolean active) {
        input_deposit.setEditable(active);
        input_deposit.setDisable(!active);
    }
}