/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrakonzol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafModel implements Serializable {

    private final int KARAKTER_A = 65;
    private int csucsokSzama;
    private List<Character> nemLatogatott;
    private List<Map> csucsMapList;
    private char start = 'A';

    public GrafModel() {
    }

    public GrafModel(int csucsokSzama) {
        this.csucsokSzama = csucsokSzama;
        this.nemLatogatott = new ArrayList<>();
        csucsMapList = new ArrayList<>();

        for (int i = KARAKTER_A; i < KARAKTER_A + csucsokSzama; i++) {
            nemLatogatott.add((char) i);
        }

        mapInit();

    }

    private void mapInit() {
        for (int j = 0; j < csucsokSzama; j++) {
            csucsMapList.add(new HashMap<Character, Integer>());
            csucsMapList.get(j).put((char) (KARAKTER_A + j), 0);
        }

    }

    public void elezo(char origo, char b, int i) {

        Map<Character, Integer> aktMap = new HashMap<>();
        Map<Character, Integer> masikCsucsMap = new HashMap<>();

        for (Character ch : nemLatogatott) {

            if (ch == origo) {
                aktMap = csucsMapList.get((int) ch - KARAKTER_A);
                if (aktMap.get(b) == null) {
                    aktMap.put(b, i);
                }

                masikCsucsMap = csucsMapList.get((int) b - KARAKTER_A);
                if (masikCsucsMap.get(origo) == null) {
                    masikCsucsMap.put(origo, i);
                }

            }
        }

    }

    public void grafEllenor() {
        for (Map map : csucsMapList) {
            for (Object entry : map.entrySet()) {
                Map.Entry<Character, Integer> beiras = (Map.Entry<Character, Integer>) entry;
                System.out.println(beiras.getKey() + " : " + beiras.getValue());
            }
            System.out.println("---------");
        }
    }

    public List<Character> getNemLatogatott() {
        return nemLatogatott;
    }

    public List<Map> getCsucsMapList() {
        return csucsMapList;
    }

    public char getStart() {
        return start;
    }

    public void setStart(char start) {
        this.start = start;
    }

    public int getKARAKTER_A() {
        return KARAKTER_A;
    }

    public int getCsucsokSzama() {
        return csucsokSzama;
    }

}
