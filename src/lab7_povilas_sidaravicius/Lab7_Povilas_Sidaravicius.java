/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7_povilas_sidaravicius;

import GUI.MainWindow;
import data.PlaceTest;
import java.util.Locale;
/**
 *
 * @author PovilasSid
 */
public class Lab7_Povilas_Sidaravicius {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        PlaceTest.seriesTest();
        MainWindow.createAndShowGUI();
    }
}
