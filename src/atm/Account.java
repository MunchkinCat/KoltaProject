package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class Account {

    private double balance;
    private double withdrawAmount;
    private double depositAmount;
    private String firstName;
    private String lastName;
    private String pin;
    private String card;

    public Account() {
        balance = 0;
        withdrawAmount = 0;
        depositAmount = 0;
        firstName = "";
        lastName = "";
        pin = "";
        card = "";
    }

    public Account(String firstName, String lastName, String pin, String card) {
        withdrawAmount = 0;
        depositAmount = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.card = card;
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public double getTotalWithdrawAmount() {
        return withdrawAmount;
    }

    public String getPin() {
        return pin;
    }

    public String getCard() {
        return card;
    }

    public boolean withdraw(double amount) {
        withdrawAmount += amount;
        return isSufficientFunds();
    }

    public boolean deposit(double amount) {
        depositAmount += amount;
        return isSufficientFunds();
    }

    // Returns true if transaction was finalized successfully
    public boolean finalizeTransaction() {
        if (isSufficientFunds()) {
            balance = balance - withdrawAmount + depositAmount;
            resetWithdrawDepositTracker();
            return true;
        } else {
            resetWithdrawDepositTracker();
            return false;
        }
    }

    // Checks if finalizing will leave a non-negative balance.
    private boolean isSufficientFunds() {
        double tentativeBalance = balance - withdrawAmount + depositAmount;
        return tentativeBalance >= 0;
    }

    private void resetWithdrawDepositTracker() {
        withdrawAmount = 0;
        depositAmount = 0;
    }
}
