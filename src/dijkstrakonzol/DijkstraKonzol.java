/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrakonzol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Andras Sarro <>
 */
public class DijkstraKonzol {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static GrafModel gm;
    private static List<Character> csomok;
    private static boolean mehet = false;
    private static boolean betoltes = false;

    public static void main(String[] args) {

        System.out.println("KAPCSOLD BE A CAPSLOCKOT!!!");
        System.out.println("A program a gráf csomópontjaira az angol ABC nagybetűivel hivatkozik, <A, B, C, ...>");
        System.out.println("A csomópontok közötti távolságot / súlyozást EGÉSZ számokban méri!");
        System.out.println("A csomópontok közötti kapcsolatot manuálisan kell megadni!");
        System.out.println("");

        ujGraf();
        if (!betoltes) {
            felElez();
        }

        System.out.println("Ellenőrzöd az adatokat? I / N");
        String valasz = SCANNER.nextLine();

        if (valasz.equals("I")) {
            gm.grafEllenor();
        }

        if (!betoltes) {
            System.out.println("Elmented a gráfot? I / N");
            String mentes = SCANNER.nextLine();

            if (mentes.equals("I")) {
                String fileHelye = "";
                System.out.println("Add meg az elérési utat, a fájl nevével bezárólag! <pl: \"c:/proba/file.dat\"\t Ha üresen hagyod,"
                        + "a c:/grafjaro/graf.dat\" lesz a mentés helye.");
                fileHelye = SCANNER.nextLine();
                if (fileHelye.isEmpty()) {
                    fileHelye = "c:/grafjaro/graf.dat";
                }

                File file = new File(fileHelye);
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(gm);
                } catch (IOException ex) {
                    System.out.println("File hiba!" + ex);
                }
            }

            System.out.println("Betölthetem az adatokat? I / N");
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
            System.out.println("Hány szomszédja van '" + ch + "'-nak, amit még NEM ADTÁL MEG?");
            int szomszedokSzama = SCANNER.nextInt();
            SCANNER.nextLine();
            for (int i = 0; i < szomszedokSzama; i++) {
                System.out.println("Kérem '" + ch + "' " + (i + 1) + ". NEM MEGADOTT szomszédját, és a két csomópont távolsását! <pl: B 2>");
                String s = SCANNER.nextLine();
                char x = s.charAt(0);
                int y = Integer.parseInt("" + s.substring(2));

                gm.elezo(ch, x, y);
            }
        }
    }

    private static void ujGraf() {
        System.out.println("Új gráfot írsz, vagy egy meglévőt töltesz be? I - új gráf / N - betöltés");
        String betolt = SCANNER.nextLine();

        if (betolt.equals("N")) {
            System.out.println("Add meg a betöltendő gráf elérési útját! <pl: \"c:/konyvtar/graf.dat\"");
            String eleres = SCANNER.nextLine();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(eleres))) {
                gm = (GrafModel) ois.readObject();
                ois.close();
                betoltes = true;
            } catch (IOException ex) {
                System.out.println("File hiba!" + ex);;
            } catch (ClassNotFoundException ex) {
                System.out.println("A gráf nem gráf..." + ex);
            }
        } else {
            System.out.print("Hány csomópontja van a gráfnak? Egész számot írj! \nCsomópontok száma:");
            int csucsok = SCANNER.nextInt();
            gm = new GrafModel(csucsok);
        }
        csomok = gm.getNemLatogatott();
    }

    private static void rajt() {
        System.out.println("Kérem a kiindulási csomópont BETŰJÉT! <pl: C>");
        String startCel = SCANNER.nextLine();
        gm.setStart((char) startCel.charAt(0));

        GrafJaro jaro = new GrafJaro(gm);
        jaro.jardBe();
    }

    private static boolean joMan() {
        boolean manJo = false;
        System.out.println("Jó lett? I / N");
        switch (SCANNER.nextLine()) {
            case "I":
                manJo = true;
                break;
            case "N":
                ujGraf();
                break;
        }
        return manJo;
    }
}
