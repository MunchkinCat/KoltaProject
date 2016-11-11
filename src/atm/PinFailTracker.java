package atm;

/**
 * Created by egrstudent on 11/11/16.
 */
public class PinFailTracker {

    private int failCount;

    public PinFailTracker() {
        this.failCount = 0;
    }

    public void fail() {
        this.failCount++;
    }

    public int check() {
        return this.failCount;
    }
}
