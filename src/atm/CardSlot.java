package atm;


/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class CardSlot {

    private String pin;
    private String card;

    public CardSlot() {
        pin = null;
        card = null;
    }

    public String getPin() {
        return this.pin;
    }

    public String getCard() {
        return this.card;
    }

    public void insertCard(String card) throws InvalidCardException {
        Authenticator auth = new Authenticator();
        if (auth.validCard(card)) {
            this.card = card;
        } else {
            throw new InvalidCardException("Not a valid card");
        }
    }

    public void enterPin(String pin) throws InvalidPinException {
        Authenticator auth = new Authenticator();
        if (auth.validPin(pin, this.card)) {
            this.pin = pin;
        } else {
            throw new InvalidPinException("Pin does not match card");
        }
    }

    public void removeCard() {
        pin = null;
        card = null;
    }
}
