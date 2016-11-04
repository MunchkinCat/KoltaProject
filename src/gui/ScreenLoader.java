package gui;

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
        String[] screens = StringUtils.splitPreserveAllTokens(screenRaw, '\n');
        String[] screen = StringUtils.splitPreserveAllTokens(screens[index], ',');

        if (screen.length == 4) {
            return screen;
        } else {
            throw new Exception("Number of lines not equal to 4.");
        }
    }
}
