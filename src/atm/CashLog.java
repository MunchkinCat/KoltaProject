package atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class CashLog {

    private List<Double> log;

    public CashLog() {
        log = new ArrayList<>();
    }

    public void logCashInput(double amount) {
        log.add(amount);
    }

    public void logCashOutput(double amount) {
        log.add(amount * (-1));
    }
}
