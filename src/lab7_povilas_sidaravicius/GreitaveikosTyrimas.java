/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7_povilas_sidaravicius;
import GUI.*;
import GUI.KsSwing.MyException;
import data.Place;
import data.PlaceGenerator;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JTextArea;
import studijosKTU.*;

/**
 *
 * @author PovilasSid
 */

public class GreitaveikosTyrimas extends Thread {
    
    private static ResourceBundle rb;
    static JTextArea taOutput;
    static JButton[] btns;
    static String[] tyrimųVardai = {"addBstRec", "addBstIte", "addAvlRec", "removeBst"};
    static int[] tiriamiKiekiai = {10000, 20000, 40000, 80000};
    static SortedSetADTx<Place> aSeries = new BstSetKTUx(new Place(), Place.usingSparks);
    static SortedSetADTx<Place> aSeries2 = new BstSetKTUx2(new Place());
    static SortedSetADTx<Place> aSeries3 = new AvlSetKTUx(new Place());
    
    public GreitaveikosTyrimas(JTextArea taOutput, JButton[] btns, ResourceBundle rb) {
        GreitaveikosTyrimas.taOutput = taOutput;
        GreitaveikosTyrimas.btns = btns;
        GreitaveikosTyrimas.rb = rb;
    }

    @Override
    public void run() {
        for (JButton btn : btns) {
            btn.setEnabled(false);
        }
        SisteminisTyrimas();
        for (JButton btn : btns) {
            btn.setEnabled(true);
        }
    }

    public void SisteminisTyrimas() {
        try {
            Timekeeper tk = new Timekeeper(tiriamiKiekiai, taOutput);
            for (int k : tiriamiKiekiai) {
                Place[] autoMas = PlaceGenerator.generateAndMix(3 * k, k, 1.0);
                aSeries.clear();
                aSeries2.clear();
                aSeries3.clear();
                tk.startAfterPause();
                tk.start();
                for (Place a : autoMas) {
                    aSeries.add(a);
                }
                tk.finish(tyrimųVardai[0]);
                for (Place a : autoMas) {
                    aSeries2.add(a);
                }
                tk.finish(tyrimųVardai[1]);
                for (Place a : autoMas) {
                    aSeries3.add(a);
                }
                tk.finish(tyrimųVardai[2]);
                for (Place a : autoMas) {
                    aSeries.remove(a);
                }
                tk.finish(tyrimųVardai[3]);
                tk.seriesFinish();
            }
        } catch (MyException e) {
           if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsSwing.ounerr(taOutput, rb.getStringArray("errMsgs2")[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[2]);
            } else {
                KsSwing.ounerr(taOutput, e.getMessage());
            }
        }
    }
}
