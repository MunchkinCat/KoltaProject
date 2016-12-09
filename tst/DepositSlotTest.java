import atm.Account;
import atm.DepositSlot;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Andrew Shubin on 12/8/16.
 */

public class DepositSlotTest {

    @Test
    public void testDepositSlot() {
        // Arrange
        Account account = new Account("Dave", "Jones", "6666", "6666");
        DepositSlot depositSlot = new DepositSlot(account);

        // Act
        depositSlot.depositCash(100);
        depositSlot.depositCheck(100);
        account.finalizeTransaction();
        Double balance = account.getBalance();

        // Assert
        Assert.assertEquals("Balance incorrect.", 200, balance, 0.001);
    }
}
