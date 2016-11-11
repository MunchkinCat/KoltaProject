package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class Account {

    private double balance;
    private double withdrawalAmount;
    private double depositAmount;
    private String firstName;
    private String lastName;
    private String pin;
    private String card;
    private boolean locked;

    public Account() {
        this("", "", "", "");
    }

    public Account(String firstName, String lastName, String pin, String card) {
        this.withdrawalAmount = 0;
        this.depositAmount = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.card = card;
        this.balance = 0;
        this.locked = false;
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

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public String getPin() {
        if (!locked) {
            return pin;
        } else {
            return null;
        }
    }

    public String getCard() {
        if (!locked) {
            return card;
        } else {
            return null;
        }
    }

    public void setLocked() {
        this.locked = true;
    }

    public double tentative() {
        return balance - withdrawalAmount + depositAmount;
    }

    public boolean withdraw(double amount) {
        withdrawalAmount += amount;
        return isSufficientFunds();
    }

    public boolean deposit(double amount) {
        depositAmount += amount;
        return isSufficientFunds();
    }

    // Returns true if transaction was finalized successfully
    public boolean finalizeTransaction() {
        if (isSufficientFunds()) {
            balance = balance - withdrawalAmount + depositAmount;
            resetWithdrawDepositTracker();
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.update(this);
            return true;
        } else {
            resetWithdrawDepositTracker();
            return false;
        }
    }

    // Checks if finalizing will leave a non-negative balance.
    private boolean isSufficientFunds() {
        double tentativeBalance = balance - withdrawalAmount + depositAmount;
        return tentativeBalance >= 0;
    }

    private void resetWithdrawDepositTracker() {
        withdrawalAmount = 0;
        depositAmount = 0;
    }
}
