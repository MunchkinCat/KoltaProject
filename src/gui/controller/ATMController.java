package gui.controller;

import atm.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by egrstudent on 11/2/16.
 */
public class ATMController {

    private static final String LOCK_MESSAGE = "ACCOUNT LOCKED";

    private int sourceScreen = 1;

    private String pin = "";
    private int pinFailCount = 0;
    private String card = "";
    private Amount transactionAmount = new Amount();

    private boolean deposit = false;

    private CashDispenser atm = new CashDispenser(10000);

    private Account account = null;
    private ScreenDAO screenDAO = null;

    @FXML
    private TextField input_deposit, input_card;

    @FXML
    private Label screen_row1, screen_row2,
            screen_row3, screen_row4, label_receipt;

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
        NumpadHandler numpad = new NumpadHandler(this, event);
        switch (sourceScreen) {
            case 2:
                Account tempAccount = numpad.checkPin();
                if (tempAccount != null) {
                    account = tempAccount;
                    configure(5);
                } else {
                    pinFailCount++;
                    configure(3);
                    screen_row2.setText("Attempt(s) left: " + (3 - pinFailCount));
                    if (pinFailCount >= 3) {
                        AccountDAO accountDAO = new AccountDAO();
                        accountDAO.lock(input_card.getText());
                        screen_row4.setText(LOCK_MESSAGE);
                    }
                }
                break;
            case 3:
                if (screen_row4.getText() == LOCK_MESSAGE) {
                    configure(1);
                } else {
                    configure(2);
                }
                break;
            case 6:
                configure(5);
                break;
            case 7:
                if (!deposit) {
                    try {
                        configure(numpad.checkWithdrawal());
                    } catch (Exception e) {
                        configure(8);
                        screen_row1.setText("CASH DISPENSER ERROR:");
                    }
                } else {
                    account.deposit(transactionAmount.toDouble());
                    configure(14);
                }
                break;
            case 8:
                if (!deposit) {
                    configure(7);
                } else {
                    configure(13);
                }
                break;
            case 9:
                configure(7);
                break;
            default:
                break;
        }
    }

    @FXML
    protected void handleClearButton(ActionEvent event) {
        switch (sourceScreen) {
            case 2:
                configure(2);
                break;
            case 3:
                if (screen_row4.getText() == LOCK_MESSAGE) {
                    configure(1);
                } else {
                    configure(2);
                }
                break;
            case 6:
                configure(5);
                break;
            case 7:
                configure(7);
                break;
            case 8:
                if (!deposit) {
                    configure(7);
                } else {
                    configure(13);
                }
                break;
            case 9:
                configure(7);
                break;
            case 13:
                configure(5);
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
            case 3:
                if (screen_row4.getText() == LOCK_MESSAGE) {
                    configure(1);
                } else {
                    configure(2);
                }
                break;
            case 4:
                configure(1);
                break;
            case 6:
                configure(5);
                break;
            case 7:
                configure(5);
                break;
            case 8:
                configure(5);
                break;
            case 9:
                configure(5);
                break;
            case 13:
                configure(5);
                break;
            default:
                break;
        }
    }

    @FXML
    protected void handleNumberButton(ActionEvent event) {
        NumpadHandler numpad = new NumpadHandler(this, event);
        switch (sourceScreen) {
            case 1:
                break;
            case 2:
                String[] newPin = null;
                try {
                    newPin = numpad.updatePin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (newPin != null) {
                    screen_row4.setText(newPin[0]); // updated hash
                    pin = newPin[1]; // updated pin number
                } else {
                    screen_row1.setText("MAX PIN LENGTH REACHED");
                }
                break;
            case 7:
                if (!deposit) {    // i.e. this is a withdrawal
                    transactionAmount = numpad.updateWithdrawal();
                } else {           // i.e. this is a deposit
                    transactionAmount = numpad.updateDeposit();
                }
                screen_row4.setText(transactionAmount.toString());
                break;
            default:
                break;
        }
    }

    @FXML
    protected void handleDepositButton(ActionEvent event) {
        if (sourceScreen == 13) {
            try {
                transactionAmount = new Amount(input_deposit.getText());
                if (transactionAmount.toDouble() < 0) {
                    screen_row1.setText("ERROR: Enter a positive amount.");
                } else {
                    configure(7);
                }
            } catch (NumberFormatException e) {
                screen_row1.setText("ERROR: Enter a number.");
                input_deposit.clear();
            }
        }
    }

    @FXML
    protected void handleDispenserButton(ActionEvent event) {
        if (sourceScreen == 11) {
            configure(14);
        }
    }

    @FXML
    protected void handleScreenButton(ActionEvent event) {
        ScreenButtonHandler handler = new ScreenButtonHandler(this, event);
        ScreenOption pressed;
        boolean error = (RandomUtils.nextInt(0, 5) == 0); // 1 in 5 chance of an error
        switch (sourceScreen) {
            case 5: // right 2, 3, and 4 for transaction-selection
                pressed = handler.selectTransaction();
                switch (pressed) {
                    case BALANCE:
                        configure(6);
                        break;
                    case DEPOSIT:
                        if (!error) {
                            configure(13);
                        } else {
                            configure(12);
                        }
                        break;
                    case WITHDRAWAL:
                        if (!error) {
                            configure(7);
                        } else {
                            configure(10);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 10: // left and right 4
            case 12: // for "Another Transaction?"
            case 14: // "<< YES  ||  NO >>"
                pressed = handler.newTransaction();
                switch (pressed) {
                    case YES:
                        configure(5);
                        break;
                    case NO:
                        configure(15);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @FXML
    protected void handleReceiptButton(ActionEvent event) {
        label_receipt.setText("");
    }

    protected boolean isScreenButton(Button button, Side side, int line) {
        if(side == Side.LEFT) {
            switch (line) {
                case 1:
                    return button == button_screen_left1;
                case 2:
                    return button == button_screen_left2;
                case 3:
                    return button == button_screen_left3;
                case 4:
                    return button == button_screen_left4;
            }
        } else if (side == Side.RIGHT) {
            switch (line) {
                case 1:
                    return button == button_screen_right1;
                case 2:
                    return button == button_screen_right2;
                case 3:
                    return button == button_screen_right3;
                case 4:
                    return button == button_screen_right4;
            }
        }
        return false;
    }

    protected int getSourceScreen() {
        return this.sourceScreen;
    }

    protected String getPin() {
        return this.pin;
    }

    protected String getCard() {
        return this.card;
    }

    protected double getTransactionAmount() {
        return this.transactionAmount.toDouble();
    }

    protected Account getAccount() {
        return this.account;
    }

    protected String getScreen(int line) {
        switch (line) {
            case 1:
                return screen_row1.getText();
            case 2:
                return screen_row2.getText();
            case 3:
                return screen_row3.getText();
            case 4:
                return screen_row4.getText();
            default:
                return null;
        }
    }

    protected CashDispenser getAtm() {
        return this.atm;
    }

    private void configure(int screenNum) {
        if (screenDAO == null) {
            screenDAO = new ScreenDAO();
        }
        screen_row1.setText(screenDAO.get(screenNum, 1));
        screen_row2.setText(screenDAO.get(screenNum, 2));
        screen_row3.setText(screenDAO.get(screenNum, 3));
        screen_row4.setText(screenDAO.get(screenNum, 4));

        resetAllButtons();


        Amount balance = null;
        if (screenNum >= 5) {
            balance = new Amount(account.tentative());
        }

        // Configures button actions according to screen.
        // Also deals with some other global vars.
        switch (screenNum) {
            case 1: // Home screen
                setCardTextActive(true);
                setDepositTextActive(false);
                button_cardslot.setOnAction(this::handleCardInsertion);
                input_card.clear();
                pin = "";
                card = "";
                transactionAmount.set(0);
                account = null;
                pinFailCount = 0;
                break;
            case 2: // PIN entry
                pin = "";
                button_clear.setOnAction(this::handleClearButton);
                button_cancel.setOnAction(this::handleCancelButton);
                button_enter.setOnAction(this::handleEnterButton);
                activateNumpad();
                break;
            case 3: // PIN error
                button_clear.setOnAction(this::handleClearButton);
                button_cancel.setOnAction(this::handleCancelButton);
                button_enter.setOnAction(this::handleEnterButton);
                break;
            case 4: // Card error
                button_cardslot.setOnAction(this::handleCardInsertion);
                break;
            case 5: // Transaction selection
                deposit = false;
                button_screen_right2.setOnAction(this::handleScreenButton);
                button_screen_right3.setOnAction(this::handleScreenButton);
                button_screen_right4.setOnAction(this::handleScreenButton);
                break;
            case 6: // Balance display
                button_cancel.setOnAction(this::handleCancelButton);
                button_enter.setOnAction(this::handleEnterButton);
                button_clear.setOnAction(this::handleClearButton);
                screen_row3.setText(balance.toString());
                break;
            case 7: // Request amount entry
                if (!deposit) {
                    transactionAmount.set(0);
                }
                button_clear.setOnAction(this::handleClearButton);
                button_enter.setOnAction(this::handleEnterButton);
                button_cancel.setOnAction(this::handleCancelButton);
                if (deposit) {
                    screen_row2.setText("(Amount detected in");
                    screen_row3.setText("slot is " + transactionAmount.toString() + ")");
                }
                activateNumpad();
                break;
            case 8: // Insufficient funds
                button_clear.setOnAction(this::handleClearButton);
                button_enter.setOnAction(this::handleEnterButton);
                button_cancel.setOnAction(this::handleCancelButton);
                break;
            case 9: // Dispenser denomination error
                button_clear.setOnAction(this::handleClearButton);
                button_enter.setOnAction(this::handleEnterButton);
                button_cancel.setOnAction(this::handleCancelButton);
                break;
            case 10: // Withdrawal processing error
                button_screen_left4.setOnAction(this::handleScreenButton);
                button_screen_right4.setOnAction(this::handleScreenButton);
                break;
            case 11: // Cash dispensed
                button_dispenser.setOnAction(this::handleDispenserButton);
                break;
            case 12: // Deposit processing error
                button_screen_left4.setOnAction(this::handleScreenButton);
                button_screen_right4.setOnAction(this::handleScreenButton);
                break;
            case 13: // Ready for deposit
                deposit = true;
                transactionAmount.set(0);
                button_deposit.setOnAction(this::handleDepositButton);
                button_clear.setOnAction(this::handleClearButton);
                button_cancel.setOnAction(this::handleCancelButton);
                setDepositTextActive(true);
                break;
            case 14: // Printed balance
                button_screen_left4.setOnAction(this::handleScreenButton);
                button_screen_right4.setOnAction(this::handleScreenButton);
                screen_row2.setText(balance.toString());
                break;
            case 15: // End of transaction
                label_receipt.setText(ReceiptPrinter.printReceipt(account));
                button_cardslot.setOnAction(this::handleCardInsertion);
                break;
            default:
                break;
        }

        sourceScreen = screenNum;
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
        button_cardslot.setOnAction(null);
        button_printer.setOnAction(this::handleReceiptButton);
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