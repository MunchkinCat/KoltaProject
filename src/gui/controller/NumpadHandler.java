package gui.controller;

import atm.*;
import javafx.event.ActionEvent;

/**
 * Created by egrstudent on 11/9/16.
 */
public class NumpadHandler {

    private final int SOURCE_SCREEN;
//    private final String LINE1;
//    private final String LINE2;
//    private final String LINE3;
    private final String LINE4;
    private final ActionEvent EVENT;
    private final Account account;

    private String pin;
    private String card;
    private Amount withdrawal;

    private CashDispenser atm;

    public NumpadHandler(ATMController controller, ActionEvent event) {
        this.SOURCE_SCREEN = controller.getSourceScreen();
        this.EVENT = event;
        this.pin = controller.getPin();
        this.card = controller.getCard();
        this.withdrawal = new Amount(controller.getWithdrawal());
        this.account = controller.getAccount();
        this.atm = controller.getAtm();
//        this.LINE1 = controller.getScreen(1);
//        this.LINE2 = controller.getScreen(2);
//        this.LINE3 = controller.getScreen(3);
        this.LINE4 = controller.getScreen(4);
    }

    // Checks if the pin matches the account number.
    public Account checkPin() {
        Authenticator auth = new Authenticator();
        AccountDAO manager = new AccountDAO();
        if (auth.validPin(pin, card)) {
            return manager.getAccount(pin, card);
        } else {
            return null; // if the pin is invalid
        }
    }

    // Returns [0] new hash and [1] new pin (NULL if max pin length reached)
    public String[] updatePin() throws Exception {
        String sourceButton = EVENT.getSource().toString();
        String[] hash_pin = new String[2];

        if (SOURCE_SCREEN != 2) {
            throw new Exception("\"NumpadHandler.updatePin\" "
                    + "only for use with Screen 2.");
        }

        int stringLength = sourceButton.length();

        /* NOTE FOR NEXT SECTION OF CODE:
         * stringLength - 2 used because of (1) the zero-indexing and
         * (2) the single quote character that is at the end of the
         * String representation of the source button passed in.
         * The accessed character is the number which the key represents.
         */
        int number = Character.getNumericValue(sourceButton.charAt(stringLength - 2));

        int blankCount = 0;
        // LINE4 for Pin processing holds the old pin hash representation
        for (int i = 0; i < LINE4.length(); i++) {
            if (LINE4.charAt(i) == '_') {
                blankCount++;
            }
        }

        if (blankCount > 4 || LINE4.length() > 4) {
            throw new Exception("ERROR: Screen line 4 contains more than 4 chars");
        } else if (blankCount > 0) {
            switch (blankCount - 1) { // -1 because of the new input
                case 0:
                    hash_pin[0] = "****";
                    break;
                case 1:
                    hash_pin[0] = "_***";
                    break;
                case 2:
                    hash_pin[0] = "__**";
                    break;
                case 3:
                    hash_pin[0] = "___*";
                    break;
                case 4:
                    hash_pin[0] = "____";
                    break;
                default:
                    break;
            }
            hash_pin[1] = number + pin;
            return hash_pin;
        } else {
            return null; // max pin length reached
        }
    }

    // Checks that the withdrawal string represents
    // a valid withdrawal amount.
    // Returns what screen to configure.
    // Throws Exception if ATM does not have enough cash for withdrawal.
    public int checkWithdrawal() throws Exception {
        Amount currentBalance = new Amount(account.tentative());
        if (withdrawal.toDouble() <= currentBalance.toDouble()
                && withdrawal.toDouble() % 10 == 0) {
            try {
                atm.removeCash(withdrawal.toDouble());
                account.withdraw(withdrawal.toDouble());
            } catch (Exception e) {
                throw e;
            } finally {
                withdrawal.set(0);
            }
            return 11;
        } else if (withdrawal.toDouble() > currentBalance.toDouble()) {
            withdrawal.set(0);
            return 8;
        } else {
            withdrawal.set(0);
            return 9;
        }
    }

    // Returns the updated withdrawal string.
    public Amount updateWithdrawal() {
        String sourceButton = EVENT.getSource().toString();
        /* NOTE FOR NEXT SECTION OF CODE:
         * stringLength - 2 used because of (1) the zero-indexing and
         * (2) the single quote character that is at the end of the
         * String representation of the source button passed in.
         * The accessed character is the number which the key represents.
         */
        try {
            withdrawal.insert(sourceButton.charAt(sourceButton.length() - 2));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return withdrawal;
    }
}
