package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class ReceiptPrinter {

//    public final static void printReceipt(String pin, int transactionID) {
//        // this method signature does not make sense
//    }

    public static String printReceipt(Account account) {
        String receipt = "THANK YOU\n"
                + account.getLastName() + ", " + account.getFirstName()
                + "\n------------------\n"
                + "Withdrawn: " + account.getWithdrawAmount() + "\n"
                + "Deposited: " + account.getDepositAmount() + "\n"
                + "Balance: " + account.getBalance();
        return receipt;
    }
}
