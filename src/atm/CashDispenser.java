package atm;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class CashDispenser {

    private double cash;
    private CashLog log;

    public CashDispenser(double cash) {
        this.cash = cash;
        log = new CashLog();
        log.logCashInput(cash);
    }

    public double getCashAvailable() {
        return cash;
    }

    public void addCash(double amount) {
        cash += amount;
        log.logCashInput(amount);
    }

    // Throws exception if there is not enough cash left
    public void removeCash(double amount) throws Exception {
        if (cash >= amount) {
            cash -= amount;
            log.logCashOutput(amount);
        } else {
            throw new Exception("Not enough cash");
        }
    }
}
