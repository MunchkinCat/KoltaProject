package atm;

/**
 * Created by Andrew Shubin on 11/10/16.
 */
public class Amount {

    private double amount;

    public Amount() {
        this(0);
    }

    public Amount(String amount)
            throws NumberFormatException {
        this(toDouble(amount));
    }

    public Amount(double amount) {
        this.amount = amount;
    }

    // Inserts passed-in char just to the left of the decimal.
    public Amount insert(char newChar) throws NumberFormatException {
        int newInt = Integer.parseInt(newChar + "");
        String raw = this.amount + "";
        String newAmount = "";
        for (char c : raw.toCharArray()) {
            if (c == '.') {
                newAmount += newInt + "" + c + "";
            } else {
                newAmount += c;
            }
        }
        this.amount = Double.parseDouble(newAmount);
        return this;
    }

    public void set(Amount amount) {
        set(amount.toDouble());
    }

    public void set(double amount) {
        this.amount = amount;
    }

    public Amount add(Amount amount) {
        add(amount.toDouble());
        return this;
    }

    public Amount add(double amount) {
        this.amount += amount;
        return this;
    }

    public Amount sub(Amount amount) {
        sub(amount.toDouble());
        return this;
    }

    public Amount sub(double amount) {
        this.amount -= amount;
        return this;
    }

    public String toString() {
        String rep = "$";
        String raw = this.amount + "";
        boolean pastDecimal = false;
        int numPastDecimal = 0;
        for (int i = 0; i < raw.length(); i++) {
            rep += raw.charAt(i);
            if (raw.charAt(i) == '.') {
                pastDecimal = true;
            } else if (pastDecimal) {
                numPastDecimal++;
                if (numPastDecimal == 2) {
                    break;
                }
            }
        }
        return rep;
    }

    public double toDouble() {
        return this.amount;
    }

    private static double toDouble(String amount)
            throws NumberFormatException {
        double doubleAmount = Double.parseDouble(amount);
        return doubleAmount;
    }
}
