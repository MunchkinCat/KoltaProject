import atm.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Shubin on 11/4/16.
 *
 * This class is for generating the JSON Accounts that
 * the rest of the program will use for simulation.
 */

public class GenerateAccounts {

    private static String path = FileUtils.getUserDirectoryPath() + "/IdeaProjects/KoltaProject" +
            "/src/atm/accounts.txt";

    public static void main(String[] args) {
        File file = FileUtils.getFile(path);
        Account a = new Account("Pete", "Smith", "1111", "1111111111111111111");
        Account b = new Account("Jane", "Smith", "0000", "0000000000000000000");
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(a) + "\n";
            json += mapper.writeValueAsString(b) + "\n";
            FileUtils.writeStringToFile(file, json, "UTF-8", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

