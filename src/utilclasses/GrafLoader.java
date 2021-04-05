/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclasses;

import dijkstrakonzol.GrafModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Andras Sarro <>
 */
public class GrafLoader {

    private static final Logger LOGGER = Logger.getLogger(GrafLoader.class.getName());

    public static GrafModel loadGrafFromSource(String filePath) {
        GrafModel loadedGrafModel = new GrafModel();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            loadedGrafModel = (GrafModel) ois.readObject();
            ois.close();
            LOGGER.info("A betöltés sikerült!");
        } catch (IOException ex) {
            LOGGER.info("File hiba!" + ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.info("A gráf nem gráf..." + ex);
        }

        return loadedGrafModel;
    }
}
