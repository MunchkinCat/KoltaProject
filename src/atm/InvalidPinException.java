package atm;

/**
 * Created by Andrew Shubin on 11/4/16.
 */
public class InvalidPinException extends Exception {

    public InvalidPinException(String message) {
        super(message);
    }
}
