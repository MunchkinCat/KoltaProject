import atm.InvalidPinException;
import atm.InvalidCardException;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Andrew Shubin on 12/8/16.
 */

public class CustomExceptionTest {

    @Test
    public void testPinException() {
        // Arrange
        InvalidPinException e = new InvalidPinException("TEST");

        // Act
        String message = e.getMessage();

        // Assert
        Assert.assertEquals("Exception not correctly created.", "TEST", message);
    }

    @Test
    public void testCardException() {
        // Arrange
        InvalidCardException e = new InvalidCardException("TEST");

        // Act
        String message = e.getMessage();

        // Assert
        Assert.assertEquals("Exception not correctly created.", "TEST", message);
    }
}
