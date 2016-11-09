package gui.controller;

import atm.Account;
import atm.AccountManager;
import atm.Authenticator;
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

    private String pin;
    private String card;

    public NumpadHandler(ATMController controller, ActionEvent event) {
        this.SOURCE_SCREEN = controller.getSourceScreen();
        this.EVENT = event;
        this.pin = controller.getPin();
        this.card = controller.getCard();
//        this.LINE1 = controller.getScreen(1);
//        this.LINE2 = controller.getScreen(2);
//        this.LINE3 = controller.getScreen(3);
        this.LINE4 = controller.getScreen(4);
    }

    public Account checkPin() {
        Authenticator auth = new Authenticator();
        AccountManager manager = new AccountManager();
        if (auth.validPin(pin, card)) {
            return manager.getAccount(pin, card);
        } else {
            return null; // if the pin is invalid
        }
    }

    // Returns [0] new hash and [1] new pin (NULL if max pin length reached)
    public String[] updatePin(String sourceButton) throws Exception {
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

    public void checkDeposit() {

    }

    public String updateDeposit() {
        return null;
    }
}
