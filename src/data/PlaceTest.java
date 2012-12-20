/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import studijosKTU.AvlSetKTUx;
import studijosKTU.BstSetKTU;
import studijosKTU.BstSetKTUx;
import studijosKTU.Ks;
import studijosKTU.SetADT;
import studijosKTU.SortedSetADTx;

/**
 *
 * @author PovilasSid
 */
public class PlaceTest {
    
    static Place[] placeBase;
    static SortedSetADTx<Place> pSerie = new BstSetKTUx(new Place(), Place.usingSparks);
    static Random ag = new Random();  // Atsitiktinių generatorius

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        seriesTest();
    }
    
    static SortedSetADTx generateSeries(int kiekis, int generN) {
        //Atsitiktinių generatorius
        String[][] am = { // galimų vietu duomenu masyvas
            {"Plentinis", "Juodas", "Zalias", "Pilkas", "Baltas"},
            {"Turistinis", "Juodas", "Zalias", "Pilkas", "Melynas", "Baltas"},
            {"Kalnu", "Juodas", "Zalias", "Baltas", "Pilkas"},
            {"Hibridinis", "Juodas", "Zalias", "Pilkas"},
            {"Miesto", "Juodas", "Zalias", "Pilkas", "Baltas"},
            {"Kitas", "Juodas", "Zalias", "Pilkas"}
        };
        placeBase = new Place[generN];
        ag.setSeed(1957);
        double randomLat;
        double randomLon;
        for (int i = 0; i < generN; i++) {
            int ma = ag.nextInt(am.length);        // indeksas  0..
            int mo = ag.nextInt(am[ma].length - 1) + 1;// tipo indeksas 1..
            randomLat = PlaceGenerator.MIN_LAT + (PlaceGenerator.MAX_LAT - PlaceGenerator.MIN_LAT) * ag.nextDouble();
            randomLon = PlaceGenerator.MIN_LON + (PlaceGenerator.MAX_LON - PlaceGenerator.MIN_LON) * ag.nextDouble();
            placeBase[i] = new Place(am[ma][0], randomLat, randomLon, ag.nextInt(1500 - 10 + 1) + 10, am[ma][mo]);
        }
        Collections.shuffle(Arrays.asList(placeBase));
        pSerie.clear();
        for (int i = 0; i < kiekis; i++) {
            pSerie.add(placeBase[i]);
        }
        return pSerie;
    }

    public static void seriesTest() {
        Place p1 = new Place("KTU Statybos rumai", 54.90596, 23.956235, 5, "akadem");
        Place p2 = new Place("KTU SC", 54.904923, 23.957029, 8, "akadem");
        Place p3 = new Place("KTU Elektronikos rumai", 54.903964, 23.958064, 10, "akadem");
        Place p4 = new Place("KTU Dizaino fukultetas", 54.901068, 23.960451, 6, "akadem");
        Place p5 = new Place("KTU centriniai rūmai", 54.898986, 23.91284, 7, "akadem");
        Place p6 = new Place("PC Akropolis", 54.891582, 23.919145, 6, "pc");
        Place p7 = new Place("Žalgerio arena", 54.890308, 23.914456, 8, "arena");
        Place p8 = new Place("Kauno sporto halė", 54.896111, 23.935833, 7, "arena");
        Place p9 = new Place("PC Savas", 54.92258, 23.963379, 6, "pc");
        Place p10 = new Place("PC Dainava", 54.917736, 23.96677, 4, "pc");

        Place[] placeArray = {p9, p7, p8, p5, p1, p6};


        Ks.oun("Vietų Aibė:");
        SortedSetADTx<Place> placeSerie = new BstSetKTUx(new Place());
        for (Place a : placeArray) {
            placeSerie.add(a);
        }
        Ks.oun(placeSerie.toVisualizedString("Apskritimas", ""));

        SortedSetADTx<Place> placeSerie2 =
                (SortedSetADTx<Place>) placeSerie.clone();

        placeSerie2.add(p2);
        placeSerie2.add(p3);
        placeSerie2.add(p4);
        Ks.oun("Papildyta vietų aibės kopija:");
        Ks.oun(placeSerie2.toVisualizedString("Apskritimas", ""));

        Ks.oun("Originalas:");
        Ks.ounn(placeSerie.toVisualizedString("Kvadratas", ""));

        Ks.oun("Vietų aibė su iteratoriumi:");
        Ks.oun("");
        for (Place p : placeSerie) {
            Ks.oun(p);
        }
        Ks.oun("");
        Ks.oun("Vietų aibė AVL-medyje:");
        SortedSetADTx<Place> placeSerie3 = new AvlSetKTUx(new Place());
        for (Place a : placeArray) {
            placeSerie3.add(a);
        }
        Ks.ounn(placeSerie3.toVisualizedString("Kvadratas", ""));

        Ks.oun("Vietų aibė su iteratoriumi:");
        Ks.oun("");
        for (Place p : placeSerie3) {
            Ks.oun(p);
        }

        Ks.oun("");
        Ks.oun("Vietų aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = placeSerie3.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        //Išvalome ir suformuojame aibes skaitydami iš failo
        placeSerie.clear();
        placeSerie3.clear();

        Ks.oun("");
        Ks.oun("Vietų aibė DP-medyje:");
        placeSerie.load("ban.txt");
        Ks.ounn(placeSerie.toVisualizedString("Apskritimas", ""));
        Ks.oun("Išsiaiškinkite, kodėl medis augo tik į vieną pusę.");

        Ks.oun("");
        Ks.oun("Vietų aibė AVL-medyje:");
        placeSerie3.load("ban.txt");
        Ks.ounn(placeSerie3.toVisualizedString("Apskritimas", ""));

        SetADT<String> placeSerie4 = new BstSetKTU<String>();
        placeSerie4 = PlaceList.placeTypes(placeArray);
        Ks.oun("Pasikartojan tysčios vietų tipai:\n" + placeSerie4.toString());
    }
}
