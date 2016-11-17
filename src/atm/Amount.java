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
        return this.insert(newChar, true);
    }

    // Use this one with second param set to false to insert into the 1-cent place.
    public Amount insert(char newChar, boolean skipCents) throws NumberFormatException {
        int newInt = Integer.parseInt(newChar + "");
        String raw = this.amount + "";
        String newAmount = "";
        boolean lastAddedWasDecimal = false;
        if (!skipCents) {
            char previous = ' ';
            for (char c : raw.toCharArray()) {
                lastAddedWasDecimal = false;
                if (previous == '.') {
                    newAmount += c + "" + previous;
                    lastAddedWasDecimal = true;
                } else if (c != '.') {
                    newAmount += c;
                }
                previous = c;
            }
            if (lastAddedWasDecimal) {
                newAmount += "0";
            }
            newAmount += newInt + "";
        } else {
            for (char c : raw.toCharArray()) {
                if (c == '.') {
                    newAmount += newInt + "" + c;
                } else {
                    newAmount += c;
                }
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
        // TODO: Have the toString method of Amount insert commas
        String rep = "$";
        String raw = this.amount + "";
        boolean pastDecimal = false;
        int numPastDecimal = 0;
        for (int i = 0; i < raw.length(); i++) {
            rep += raw.charAt(i);
            if (pastDecimal) {
                numPastDecimal++;
                if (numPastDecimal == 2) {
                    break;
                }
            } else if (raw.charAt(i) == '.') {
                pastDecimal = true;
            }
        }
        if (numPastDecimal < 2) {
            for (int i = 0; i < (2 - numPastDecimal); i++) {
                rep += "0";
            }
        }
        return insertCommas(rep);
    }

    public double toDouble() {
        return this.amount;
    }

    private String insertCommas(String commaless) {
        String dollars = commaless.substring(1, commaless.length() - 3); // Cuts off the '$' and the '.' and cents
        String cents = commaless.substring(commaless.length() - 3);
        String amount = "$";
        for (int i = 0; i < dollars.length(); i++) {
            amount += dollars.charAt(i) + conditionalComma(i, dollars.length());
        }
        return amount + cents;
    }

    private String conditionalComma(int position, int length) {
        int charsLeft = length - position - 1;  // -1 for the char that is inserted
                                                // on the same line that this method
                                                // is called on.
        if (charsLeft % 3 == 0 && charsLeft >= 3) {
            return ",";
        } else {
            return "";
        }
    }

    private static double toDouble(String amount)
            throws NumberFormatException {
        double doubleAmount = Double.parseDouble(amount);
        return doubleAmount;
    }
}
