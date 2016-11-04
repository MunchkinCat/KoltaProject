package atm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class Authenticator {

    private String path;

    public Authenticator() {
        path = FileUtils.getUserDirectoryPath() + "/IdeaProjects/KProject" +
                "/src/atm/accounts.txt";
    }

    public boolean validCard(String cardNum) {
        if (cardNum.length() != 19) {
            return false;
        }

        List<Account> lines = getAccounts();

        String cardCheck;
        for (Account line : lines) {
            cardCheck = line.getCard();
            if (cardNum.equals(cardCheck)) {
                return true;
            }
        }

        return false;
    }

    public boolean validPin(String pin, String card) {
        if (pin.length() != 4) {
            return false;
        }

        List<Account> lines = getAccounts();

        String pinCheck;
        String cardCheck;
        for (Account line : lines) {
            pinCheck = line.getPin();
            cardCheck = line.getCard();
            if (card.equals(cardCheck) && pin.equals(pinCheck)) {
                return true;
            }
        }

        return false;
    }

    private List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        File file = FileUtils.getFile(path);
        String raw = null;
        String[] accountsStrings;
        try {
            raw = FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountsStrings = StringUtils.split(raw, "\n");
        ObjectMapper mapper = new ObjectMapper();
        for (String accountString : accountsStrings) {
            try {
                accounts.add(mapper.readValue(accountString, Account.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }
}
