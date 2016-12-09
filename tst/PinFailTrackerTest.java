import atm.PinFailTracker;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by Andrew Shubin on 12/8/16.
 */
public class PinFailTrackerTest {

    @Test
    public void testTracker() {
        // Arrange
        PinFailTracker tracker = new PinFailTracker();

        // Act
        tracker.fail();
        tracker.fail();
        tracker.fail();
        int count = tracker.check();

        // Assert
        Assert.assertEquals("Tracker has the wrong count.", 3, count);
    }
}
