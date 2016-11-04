package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class DepositSlot {

    Account account;

    public DepositSlot(Account account) {
        this.account = account;
    }

    public void depositCheck(double amount, int checkNum) {
        account.deposit(amount);
    }

    public void depositCash(double amount) {
        account.deposit(amount);
    }
}
