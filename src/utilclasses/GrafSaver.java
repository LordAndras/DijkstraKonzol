/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclasses;

import dijkstrakonzol.GrafModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Andras Sarro <>
 */
public class GrafSaver {

    private static final String DEFAULT_SAVE_PATH = "c:/grafjaro/graf.dat";
    private static final Logger LOGGER = Logger.getLogger(GrafSaver.class.getName());

    private GrafSaver() {
    }

    public static void saveGrafWithFilePath(String filePath, GrafModel gm) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gm);
            LOGGER.info("A mentés sikeres!");
        } catch (IOException ex) {
            LOGGER.info("File hiba!" + ex);
        }
    }

    public static void saveGraf(GrafModel gm) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFAULT_SAVE_PATH))) {
            oos.writeObject(gm);
            LOGGER.info("A mentés sikeres!");
        } catch (IOException ex) {
            LOGGER.info("File hiba!" + ex);
        }

    }

}
