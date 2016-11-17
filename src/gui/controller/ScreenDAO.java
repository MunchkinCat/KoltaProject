package gui.controller;

/**
 * Created by Andrew Shubin on 11/9/16.
 */
public class ScreenDAO {

    private String[][] screens;

    public ScreenDAO() {
        screens = new String[15][4];

        // SCREEN 1
        screens[0][0] = "";
        screens[0][1] = "Welcome!";
        screens[0][2] = "Please insert your";
        screens[0][3] = "ATM card:";

        // SCREEN 2
        screens[1][0] = "";
        screens[1][1] = "";
        screens[1][2] = "Please enter your PIN:";
        screens[1][3] = "____";

        // SCREEN 3
        screens[2][0] = "";
        screens[2][1] = "";
        screens[2][2] = "Your PIN is incorrect.";
        screens[2][3] = "Please try again.";

        // SCREEN 4
        screens[3][0] = "";
        screens[3][1] = "";
        screens[3][2] = "Invalid ATM card.";
        screens[3][3] = "Please remove card.";

        // SCREEN 5
        screens[4][0] = "Select transaction:";
        screens[4][1] = "BALANCE >>";
        screens[4][2] = "DEPOSIT >>";
        screens[4][3] = "WITHDRAWAL >>";

        // SCREEN 6
        screens[5][0] = "";
        screens[5][1] = "Balance is";
        screens[5][2] = "$dddd.cc";
        screens[5][3] = "";

        // SCREEN 7
        screens[6][0] = "Enter amount:";
        screens[6][1] = "(Withdrawal amounts must";
        screens[6][2] = "be multiples of $10)";
        screens[6][3] = "$0.00";

        // SCREEN 8
        screens[7][0] = "";
        screens[7][1] = "Insufficient funds!";
        screens[7][2] = "Please enter a";
        screens[7][3] = "new amount.";

        // SCREEN 9
        screens[8][0] = "";
        screens[8][1] = "ERROR:";
        screens[8][2] = "Machine can only";
        screens[8][3] = "dispense $10 bills.";

        // SCREEN 10
        screens[9][0] = "Temporarily unable to";
        screens[9][1] = "process withdrawals.";
        screens[9][2] = "Another transaction?";
        screens[9][3] = "<< YES  ||  NO >>";

        // SCREEN 11
        screens[10][0] = "";
        screens[10][1] = "Your balance is being";
        screens[10][2] = "updated. Please take";
        screens[10][3] = "cash from dispenser.";

        // SCREEN 12
        screens[11][0] = "Temporarily unable to";
        screens[11][1] = "process deposits.";
        screens[11][2] = "Another transaction?";
        screens[11][3] = "<< YES  ||  NO >>";

        // SCREEN 13
        screens[12][0] = "";
        screens[12][1] = "";
        screens[12][2] = "Please insert deposit";
        screens[12][3] = "into deposit slot.";

        // SCREEN 14
        screens[13][0] = "Your new balance is";
        screens[13][1] = "$dddd.cc";
        screens[13][2] = "Another transaction?";
        screens[13][3] = "<< YES  ||  NO >>";

        // SCREEN 15
        screens[14][0] = "";
        screens[14][1] = "Please take your";
        screens[14][2] = "receipt and ATM card.";
        screens[14][3] = "THANK YOU!";
    }

    public String get(int screen, int line) {
        return screens[screen - 1][line - 1]; // -1 because of 0-indexing
    }
}
