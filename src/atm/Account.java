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

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public String getPin() {
        return pin;
    }

    public String getCard() {
        return card;
    }

    public void withdraw(double amount) {
        balance -= amount;
        withdrawAmount += amount;
    }

    public void deposit(double amount) {
        balance += amount;
        depositAmount += amount;
    }

    public void resetWithdrawDepositTracker() {
        withdrawAmount = 0;
        depositAmount = 0;
    }

    public boolean isSufficientFunds(double amount) {
        return balance >= amount;
    }
}
