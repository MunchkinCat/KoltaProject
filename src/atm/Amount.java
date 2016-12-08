package atm;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        long dollars = (long) (Math.floor(this.amount * 100) / 100);
        int cents = getCentsAsInt(this.amount, dollars);
        String newAmount;
        if (!skipCents) {
            String strCents = Integer.toString(cents);
            char high = strCents.charAt(0);
            char low = strCents.charAt(1);
            newAmount = dollars + "" + high + "." + low + "" + newInt;
        } else {
            newAmount = (dollars * 10 + newInt) + "." + cents;
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
        return Amount.toString(this.amount);
    }

    public double toDouble() {
        return this.amount;
    }

    public static String toString(double amount) {
        String rep = "$";
        String raw = String.format("%f", amount);
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

    private static String insertCommas(String commaless) {
        String dollars = commaless.substring(1, commaless.length() - 3); // Cuts off the '$' and the '.' and cents
        String cents = commaless.substring(commaless.length() - 3);
        String amount = "$";
        for (int i = 0; i < dollars.length(); i++) {
            amount += dollars.charAt(i) + conditionalComma(i, dollars.length());
        }
        return amount + cents;
    }

    private static String conditionalComma(int position, int length) {
        int charsLeft = length - position - 1;  // -1 for the char that is inserted
        // on the same line that this method
        // is called on.
        if (charsLeft % 3 == 0 && charsLeft >= 3) {
            return ",";
        } else {
            return "";
        }
    }

    private static int getCentsAsInt(double amount, long dollars) {
        int cents;
        BigDecimal bigAmount = new BigDecimal(amount);
        BigDecimal bigDollars = new BigDecimal(dollars);
        BigDecimal rawCents = bigAmount.subtract(bigDollars);
        rawCents = rawCents.multiply(new BigDecimal("100"));
        rawCents = rawCents.setScale(0, RoundingMode.HALF_UP);
        cents = rawCents.intValueExact();
        return cents;
    }

    private static double toDouble(String amount)
            throws NumberFormatException {
        double doubleAmount = Double.parseDouble(amount);
        return doubleAmount;
    }
}