package gui;

import com.sun.istack.internal.NotNull;
import org.apache.commons.io.*;
import org.apache.commons.lang3.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Andrew Shubin on 11/3/16.
 */
public class ScreenLoader {

    private String path;

    public ScreenLoader() {

        path = FileUtils.getUserDirectoryPath() + "/IdeaProjects/KoltaProject" +
                "/src/gui/screens.txt";
    }

    public String[] getScreen(int index) throws Exception {

        if (index == 0) {
            throw new IndexOutOfBoundsException("0 index not allowed");
        }

        // Loads screen on line (num-1) of path file.
        File file = FileUtils.getFile(path);
        String screenRaw = null;
        try {
            screenRaw = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] screens = StringUtils.splitPreserveAllTokens(screenRaw, detectNewline());
        String[] screen = StringUtils.splitPreserveAllTokens(screens[index], ',');

        if (screen.length == 4) {
            return screen;
        } else {
            throw new Exception("Number of lines not equal to 4.");
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
