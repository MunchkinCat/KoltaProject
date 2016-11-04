package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class Account {

    private double balance;
    private long PAN; // not sure what this is for
    private double[] withdraw_deposit; // keeps track of how much has been
            // withdrawn in this transaction (index 0) and how much deposited
            // in the same (index 1)
    private String firstName;
    private String lastName;
    private int pin; // why?

    public Account(String firstName, String lastName, int pin) {
        this(firstName, lastName, pin, 0);
    }

    public Account(String firstName, String lastName, int pin, long PAN) {
        withdraw_deposit = new double[2];
        withdraw_deposit[0] = 0;
        withdraw_deposit[1] = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.PAN = PAN;
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
        return withdraw_deposit[1];
    }

    public double getWithdrawAmount() {
        return withdraw_deposit[0];
    }

    public void withdraw(double amount) {
        balance -= amount;
        withdraw_deposit[0] += amount;
    }

    public void deposit(double amount) {
        balance += amount;
        withdraw_deposit[1] += amount;
    }

    public boolean isSufficientFunds(double amount) {
        return balance >= amount;
    }
}
