import atm.CardSlot;
import atm.InvalidPinException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Andrew Shubin on 12/8/16.
 */

public class CardSlotTest {

    @Test
    public void testCardSlot() {
        // Arrange
        CardSlot cardSlot = new CardSlot();
        String pin = "1111";
        String card = "1111";
        String cardActual = null;
        String pinActual = null;
        boolean exceptionThrown = false;

        // Act
        try {
            cardSlot.insertCard(card);
            cardSlot.enterPin(pin);
            cardActual = cardSlot.getCard();
            pinActual = cardSlot.getPin();
            cardSlot.removeCard();
        } catch (Exception e) {
            exceptionThrown = true;
        }

        // Assert
        Assert.assertFalse("Card validation threw an exception.", exceptionThrown);
        Assert.assertEquals("PIN doesn't match.", pin, pinActual);
        Assert.assertEquals("Card doesn't match.", card, cardActual);
        cardSlot.removeCard();
        Assert.assertNull("PIN not reset.", cardSlot.getPin());
        Assert.assertNull("Card not reset.", cardSlot.getCard());

    }

    @Test
    public void testBadCard() {
        // Arrange
        CardSlot cardSlot = new CardSlot();
        String card = "sdfsldkfj";
        boolean exceptionThrown = false;

        // Act
        try {
            cardSlot.insertCard(card);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        // Assert
        Assert.assertTrue("Exception not thrown.", exceptionThrown);
    }

    @Test
    public void testBadPin() {
        // Arrange
        CardSlot cardSlot = new CardSlot();
        String card = "1111";
        String pin = "lskdfj";
        boolean exceptionThrown = false;
        boolean unexpectedException = false;

        // Act
        try {
            cardSlot.insertCard(card);
            cardSlot.enterPin(pin);
        } catch (InvalidPinException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            unexpectedException = true;
        }

        // Assert
        Assert.assertTrue("Exception not thrown.", exceptionThrown);
        Assert.assertFalse("Unexpected validation exception.", unexpectedException);
    }
}
