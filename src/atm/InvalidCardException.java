package atm;

/**
 * Created by Andrew Shubin on 11/4/16.
 */
public class InvalidCardException extends Exception{

    public InvalidCardException(String message) {
        super(message);
    }
}
