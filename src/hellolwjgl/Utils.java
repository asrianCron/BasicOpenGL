/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellolwjgl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asrianCron
 */
public class Utils {

    public static String loadEntireFile(String path) {

        Path filePath = Paths.get(path);

        String temp;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader;
        if (Files.exists(filePath)) {
            try {
                reader = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath)));
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp).append("\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return buffer.toString();
        } else {
            System.out.println("FILE NOT FOUND");
            return null;
        }
    }

    public static List<String> loadFile(String path) {
        Path filePath = Paths.get(path);
        List<String> fileData = null;
        if (Files.exists(filePath)) {
            try {
                fileData = Files.readAllLines(filePath);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return fileData;
        } else {
            System.out.println("FILE NOT FOUND");
            return null;
        }
    }

}
