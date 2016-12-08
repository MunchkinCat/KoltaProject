package atm;

/**
 * Created by Andrew Shubin on 12/7/16.
 */
public class TEMPTESTTHING {

    public static Amount test;

    public static void main(String[] args) {
        test = new Amount(9.99);
        System.out.println(test.toString());
        for (int i = 0; i < 20; i++) {
            insert(5);
        }
    }

    private static void insert(int a) {
        if (a > 9 || a < 0) {
            System.out.println("ERROR: insert value wrong length...");
            System.exit(0);
        }
        test.insert(Character.forDigit(a, 10), true);
        System.out.println(test.toString());
    }
}
