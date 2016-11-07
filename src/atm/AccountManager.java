package atm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Shubin on 11/4/16.
 */
public class AccountManager {

    private String path;

    public AccountManager() {
        path = FileUtils.getUserDirectoryPath() + "/IdeaProjects/KoltaProject" +
                "/src/atm/accounts.txt";
    }

    public Account getAccount(String pin, String card) {
        List<Account> accounts = new ArrayList<>();
        File file = FileUtils.getFile(path);
        String raw = null;
        String[] accountsStrings;
        try {
            raw = FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountsStrings = StringUtils.split(raw, detectNewline());
        ObjectMapper mapper = new ObjectMapper();
        for (String accountString : accountsStrings) {
            try {
                accounts.add(mapper.readValue(accountString, Account.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Account desiredAccount = null;
        for (Account testAccount : accounts) {
            if (testAccount.getCard().equals(card)
                    && testAccount.getPin().equals(pin)) {
                desiredAccount = testAccount;
            }
        }

        return desiredAccount;
    }

    public void update(Account account) {
        List<Account> accounts = new ArrayList<>();
        File file = FileUtils.getFile(path);
        String raw = null;
        String[] accountsStrings;
        try {
            raw = FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        accountsStrings = StringUtils.split(raw, detectNewline());
        ObjectMapper mapper = new ObjectMapper();
        for (String accountString : accountsStrings) {
            try {
                accounts.add(mapper.readValue(accountString, Account.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Account old = null;
        for (Account testAccount : accounts) {
            if (testAccount.getCard().equals(account.getCard())
                    && testAccount.getPin().equals(account.getPin())) {
                old = testAccount;
            }
        }

        accounts.remove(old);
        String newAccountsFile = "";
        String newline = detectNewline();
        try {
            for (Account acc : accounts) {
                newAccountsFile += mapper.writeValueAsString(acc) + newline;
            }
            FileUtils.writeStringToFile(file, newAccountsFile, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String detectNewline() {
        File file = FileUtils.getFile(path);
        String raw = null;
        try {
            raw = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < raw.length(); i++) {
            if (raw.charAt(i) == '\n' && i != 0) {
                if (raw.charAt(i - 1) == '\r') {
                    return "\r\n";
                } else {
                    return "\n";
                }
            }
        }
        return "\n";
    }
}
