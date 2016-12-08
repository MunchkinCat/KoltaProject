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
                + "Withdrawn:\n" + Amount.toString(account.getWithdrawalAmount()) + "\n"
                + "Deposited:\n" + Amount.toString(account.getDepositAmount()) + "\n"
                + "Balance:\n" + Amount.toString(account.tentative());
        account.finalizeTransaction();
        return receipt;
    }
}
