/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrakonzol;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;
import utilclasses.GrafSaver;

/**
 *
 * @author Andras Sarro <>
 */
public class DijkstraKonzol {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = Logger.getLogger(DijkstraKonzol.class.getName());
    private static GrafModel gm;
    private static List<Character> csomok;
    private static boolean mehet = false;
    private static boolean betoltes = false;

    public static void main(String[] args) {

        LOGGER.info("KAPCSOLD BE A CAPSLOCKOT!!!");
        LOGGER.info("A program a gráf csomópontjaira az angol ABC nagybetűivel hivatkozik, <A, B, C, ...>");
        LOGGER.info("A csomópontok közötti távolságot / súlyozást EGÉSZ számokban méri!");
        LOGGER.info("A csomópontok közötti kapcsolatot manuálisan kell megadni!");
        LOGGER.info("");

        ujGraf();
        if (!betoltes) {
            felElez();
        }

        LOGGER.info("Ellenőrzöd az adatokat? I / N");
        String valasz = SCANNER.nextLine();

        if (valasz.equals("I")) {
            gm.grafEllenor();
        }

        if (!betoltes) {
            LOGGER.info("Elmented a gráfot? I / N");
            String willSave = SCANNER.nextLine();

            if (willSave.equals("I")) {
                String filePath = "";
                LOGGER.info("Add meg az elérési utat, a fájl nevével bezárólag! <pl: \"c:/proba/file.dat\"\t Ha ÜRESEN hagyod,"
                        + "a c:/grafjaro/graf.dat\" lesz a mentés helye.");
                filePath = SCANNER.nextLine();
                if (filePath.isEmpty()) {
                    GrafSaver.saveGraf(gm);
                } else {
                    GrafSaver.saveGrafWithFilePath(filePath, gm);
                }
            }

            LOGGER.info("Betölthetem az adatokat? I / N");
            String kezdet = SCANNER.nextLine();
            if (kezdet.equals("I")) {
                rajt();
            } else {
                while (!mehet) {
                    ujGraf();
                    felElez();
                    mehet = joMan();
                }
                rajt();

            }

        }

    }

    private static void felElez() {
        for (Character ch : csomok) {
            LOGGER.info("Hány szomszédja van '" + ch + "'-nak, amit még NEM ADTÁL MEG?");
            int szomszedokSzama = SCANNER.nextInt();
            SCANNER.nextLine();
            for (int i = 0; i < szomszedokSzama; i++) {
                LOGGER.info("Kérem '" + ch + "' " + (i + 1) + ". NEM MEGADOTT szomszédját, és a két csomópont távolsását! <pl: B 2>");
                String s = SCANNER.nextLine();
                char x = s.charAt(0);
                int y = Integer.parseInt("" + s.substring(2));

                gm.elezo(ch, x, y);
            }
        }
    }

    private static void ujGraf() {
        LOGGER.info("Új gráfot írsz, vagy egy meglévőt töltesz be? I - új gráf / N - betöltés");
        String betolt = SCANNER.nextLine();

        if (betolt.equals("N")) {
            LOGGER.info("Add meg a betöltendő gráf elérési útját! <pl: \"c:/konyvtar/graf.dat\"");
            String eleres = SCANNER.nextLine();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(eleres))) {
                gm = (GrafModel) ois.readObject();
                betoltes = true;
            } catch (IOException ex) {
                LOGGER.info("File hiba!" + ex);
            } catch (ClassNotFoundException ex) {
                LOGGER.info("A gráf nem gráf..." + ex);
            }
        } else {
            LOGGER.info("Hány csomópontja van a gráfnak? Egész számot írj! \nCsomópontok száma:");
            int csucsok = SCANNER.nextInt();
            gm = new GrafModel(csucsok);
        }
        csomok = gm.getNemLatogatott();
    }

    private static void rajt() {
        LOGGER.info("Kérem a kiindulási csomópont BETŰJÉT! <pl: C>");
        String startCel = SCANNER.nextLine();
        gm.setStart((char) startCel.charAt(0));

        GrafJaro jaro = new GrafJaro(gm);
        jaro.jardBe();
    }

    private static boolean joMan() {
        LOGGER.info("Jó lett? I / N");
        String valasz = SCANNER.nextLine();
        return valasz.equals("I");

    }
}
