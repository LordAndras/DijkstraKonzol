/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrakonzol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andras Sarro <>
 */
public class DijkstraKonzol {

    private static Scanner sc = new Scanner(System.in);
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
        String valasz = sc.nextLine();

        if (valasz.equals("I")) {
            gm.grafEllenor();
        }

        if (!betoltes) {
            System.out.println("Elmented a gráfot? I / N");
            String mentes = sc.nextLine();

            if (mentes.equals("I")) {
                String fileHelye = "";
                System.out.println("Add meg az elérési utat, a fájl nevével bezárólag! <pl: \"c:/proba/file.dat\"\t Ha üresen hagyod,"
                        + "a c:/grafjaro/graf.dat\" lesz a mentés helye.");
                fileHelye = sc.nextLine();
                if (fileHelye.isEmpty()) {
                    fileHelye = "c:/grafjaro/graf.dat";
                }

                File file = new File(fileHelye);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(gm);
                    oos.close();
                } catch (IOException ex) {
                    System.out.println("File hiba!" + ex);;
                }
            }
        }

        System.out.println("Betölthetem az adatokat? I / N");
        String kezdet = sc.nextLine();
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

//        gm.elezo('A', 'B', 6);
//        gm.elezo('A', 'D', 1);
//        gm.elezo('B', 'D', 2);
//        gm.elezo('B', 'E', 4);
//        gm.elezo('B', 'C', 5);
//        gm.elezo('C', 'E', 5);
//        gm.elezo('D', 'E', 1);
//        System.out.println(csucsLista.get(0).get('B'));
    }

    private static void felElez() {
        for (Character ch : csomok) {
            System.out.println("Hány szomszédja van '" + ch + "'-nak, amit még NEM ADTÁL MEG?");
            int szomszedokSzama = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < szomszedokSzama; i++) {
                System.out.println("Kérem '" + ch + "' " + (i + 1) + ". NEM MEGADOTT szomszédját, és a két csomópont távolsását! <pl: B 2>");
                String s = sc.nextLine();
                char x = s.charAt(0);
                int y = Integer.parseInt("" + s.substring(2));

                gm.elezo(ch, x, y);
            }
        }
    }

    private static void ujGraf() {
        System.out.println("Új gráfot írsz, vagy egy meglévőt töltesz be? I - új gráf / N - betöltés");
        String betolt = sc.nextLine();

        if (betolt.equals("N")) {
            System.out.println("Add meg a betöltendő gráf elérési útját! <pl: \"c:/konyvtar/graf.dat\"");
            String eleres = sc.nextLine();

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(eleres));
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
            int csucsok = sc.nextInt();
            gm = new GrafModel(csucsok);
        }
        csomok = gm.getNemLatogatott();
    }

    private static void rajt() {
        System.out.println("Kérem a kiindulási csomópont BETŰJÉT! <pl: C>");
        String startCel = sc.nextLine();
        gm.setStart((char) startCel.charAt(0));

        GrafJaro jaro = new GrafJaro(gm);
        jaro.jardBe();
    }

    private static boolean joMan() {
        boolean manJo = false;
        System.out.println("Jó lett? I / N");
        switch (sc.nextLine()) {
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
