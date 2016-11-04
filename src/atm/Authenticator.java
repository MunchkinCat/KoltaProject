package atm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class Authenticator {

    private String path;

    public Authenticator() {
        path = FileUtils.getUserDirectoryPath() + "/IdeaProjects/KProject" +
                "/src/atm/cards.txt";
    }

    public boolean validCard(String cardNum) {
        if (cardNum.length() != 19) {
            return false;
        }

        String[] lines = getCardsData();

        String[] pin_card;
        for (String line : lines) {
            pin_card = StringUtils.split(line, " ");
            if (cardNum.equals(pin_card[1])) {
                return true;
            }
        }

        return false;
    }

    public boolean validPin(String pin, String cardNum) {
        if (cardNum.length() != 4) {
            return false;
        }

        String[] lines = getCardsData();

        String[] pin_card;
        for (String line : lines) {
            pin_card = StringUtils.split(line, " ");
            if (cardNum.equals(pin_card[1]) && pin.equals(pin_card[0])) {
                return true;
            }
        }

        return false;
    }

    private String[] getCardsData() {
        File file = FileUtils.getFile(path);
        String raw = null;
        try {
            raw = FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.split(raw, "\n");
    }
}
