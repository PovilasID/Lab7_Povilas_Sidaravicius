package GUI;

import GUI.KsSwing.MyException;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import data.Place;
import data.PlaceGenerator;
import lab7_povilas_sidaravicius.GreitaveikosTyrimas;

import studijosKTU.*;

/**
 * @author darius.matulis@ktu.lt
 */
//Lab7 panelis
public class Lab7Panel extends JPanel implements ActionListener {

    private ResourceBundle rb;

//                              Lab7Panel (BorderLayout)
//  |---------------------------------Center--------------------------------|
//  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~splResults~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
//  |  |  |-----------------------------------|  |--------------------|  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |            scrollTree             |  |    scrollOutput    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |                                   |  |                    |  |  |
//  |  |  |-----------------------------------|  |--------------------|  |  |
//  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |                                                  | 
//  |---------------------------------South---------------------------------|
//  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~scrollSouth~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
//  |  |                                                                 |  |
//  |  |             |-----------||-----------||------------|            |  | 
//  |  |             | panParam1 || panParam2 || panButtons |            |  |
//  |  |             |           ||           ||            |            |  |
//  |  |             |-----------||-----------||------------|            |  |
//  |  |                                                                 |  |
//  |  |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|  |
//  |-----------------------------------------------------------------------| 
    public Lab7Panel(ResourceBundle rb) {
        this.rb = rb;
        initComponents();
    }

    private void initComponents() {
        //======================================================================
        // Sudaromas rezultatų išvedimo JSplitPane klasės objektas, kuriame
        // talpinami du JTextArea klasės objektai
        //======================================================================
        splResults.setLeftComponent(scrollTree);
        splResults.setRightComponent(scrollOutput);
        splResults.setDividerLocation(570);
        splResults.setResizeWeight(0.7);
        splResults.setDividerSize(5);
        splResults.setPreferredSize(new Dimension(1000, 400));
        //Kad prijungiant tekstą prie JTextArea vaizdas visada nušoktų į apačią
        scrollTree.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                taTree.select(taTree.getCaretPosition()
                        * taTree.getFont().getSize(), 0);
            }
        });
        scrollOutput.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                taOutput.select(taOutput.getCaretPosition()
                        * taOutput.getFont().getSize(), 0);
            }
        });
        //======================================================================
        // Formuojama pirmoji parametrų lentelė (gelsva).
        //======================================================================
        panParam1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 6, 3, 4);
        //Išlygiavimas į kairę
        c.anchor = GridBagConstraints.WEST;
        //Komponentų išplėtimas iki maksimalaus celės dydžio
        c.fill = GridBagConstraints.BOTH;
        //Pirmas stulpelis
        c.gridx = 0;
        for (String s : rb.getStringArray("lblParams1")) {
            panParam1.add(new JLabel(s), c);
        }
        //Antras stulpelis
        c.gridx = 1;
        for (String s : rb.getStringArray("cmbTreeTypes")) {
            cmbTreeType.addItem(s);
        }
        cmbTreeType.addActionListener(this);
        panParam1.add(cmbTreeType, c);
        for (String s : rb.getStringArray("cmbTreeSymbols")) {
            cmbTreeSymbols.addItem(s);
        }
        panParam1.add(cmbTreeSymbols, c);
        tfDelimiter.setHorizontalAlignment(JTextField.CENTER);
        panParam1.add(tfDelimiter, c);
        //Vėl pirmas stulpelis, tačiau plotis - 2 celės
        c.gridx = 0;
        c.gridwidth = 2;
        tfInput.setEditable(false);
        tfInput.setBackground(Color.lightGray);
        panParam1.add(tfInput, c);
        //======================================================================
        // Formuojama antroji parametrų lentelė (oranžinė). Naudojame klasę MyPanels.
        //======================================================================
        //8 - JTextField'ų plotis
        panParam2 = new MyPanels(rb.getStringArray("lblParams2"),
                rb.getStringArray("tfParams2"), 8);
        //======================================================================
        // Formuojamas mygtukų tinklelis (mėlynas). Naudojame klasę MyPanels.
        //======================================================================
        //4 eilutes, stulpeliu - neribotai
        panButtons = new MyPanels(rb.getStringArray("btnLabels"), 4, 0);
        for (JButton btn : panButtons.getButtons()) {
            btn.addActionListener(this);
        }
        enableButtons(false);
        //======================================================================
        // Formuojamas bendras parametrų panelis (tamsiai pilkas).
        //======================================================================
        panSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panSouth.add(panParam1);
        panSouth.add(panParam2);
        panSouth.add(panButtons);
        //======================================================================
        // Formuojamas Lab7 panelis
        //======================================================================        
        setLayout(new BorderLayout());
        //..centre ir pietuose talpiname objektus..
        add(splResults, BorderLayout.CENTER);
        add(scrollSouth, BorderLayout.SOUTH);
        appearance();
    }

    private void appearance() {
        //Komponentų borderiai
        int counter = 0;
        for (JComponent comp : new JComponent[]{scrollTree, scrollOutput,
                    scrollSouth}) {
            TitledBorder tb = new TitledBorder(rb.getStringArray("lblBorders")[counter++]);
            tb.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
            comp.setBorder(tb);
        }
        panParam1.setBackground(new Color(255, 255, 153));//Gelsva
        panParam2.setBackground(Color.ORANGE);
        panParam2.getTfOfTable()[2].setEditable(false);
        panParam2.getTfOfTable()[2].setForeground(Color.red);
        panParam2.getTfOfTable()[4].setEditable(true);
        panButtons.setBackground(new Color(112, 162, 200)); //Blyškiai mėlyna
        panSouth.setBackground(Color.GRAY);
        taTree.setFont(Font.decode("courier new-12"));
        // Swing'as Linux Ubuntu Su Gnome rodo teisingai su šiuo fontu:
        // taTree.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        taOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        taTree.setEditable(false);
        taOutput.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            System.gc();
            System.gc();
            System.gc();
            taOutput.setBackground(Color.white);
            taTree.setBackground(Color.white);
            Object source = ae.getSource();
            if (source.equals(panButtons.getButtons()[0])) {
                treeGeneration(null);
            }
            if (source.equals(panButtons.getButtons()[1])) {
                treeIteration();
            }
            if (source.equals(panButtons.getButtons()[2])) {
                treeAdd();
            }
            if (source.equals(panButtons.getButtons()[3])) {
                treeEfficiency();
            }
            if (source.equals(panButtons.getButtons()[4])) {
                treeRemove();
            }
            if (source.equals(panButtons.getButtons()[5])) {
                hOfTree();
            }
            if (source.equals(panButtons.getButtons()[6])) {
                getHeadSet();
            }
            if (source.equals(panButtons.getButtons()[7])) {
                getTailSet();
            }
            if (source.equals(panButtons.getButtons()[8])) {
                getSubSet();
            }
            if (source.equals(panButtons.getButtons()[9])) {
                enableButtons(false);
            }
            if (source.equals(cmbTreeType)) {
                enableButtons(false);
            }
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                KsSwing.ounerr(taOutput, rb.getStringArray("errMsgs2")[e.getCode()] + ": " + e.getMessage());
                if (e.getCode() == 3) {
                    panParam2.getTfOfTable()[0].setBackground(Color.red);
                    panParam2.getTfOfTable()[1].setBackground(Color.red);
                }
            } else if (e.getCode() == 4) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[2]);
            } else {
                KsSwing.ounerr(taOutput, e.getMessage());
            }
        } catch (NullPointerException e) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[11]);
            e.printStackTrace(System.out);
        } catch (IllegalArgumentException e) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[11]);
            e.printStackTrace(System.out);
        } catch (UnsupportedOperationException e) {
            KsSwing.ounerr(taOutput, e.getMessage());
        } catch (Exception e) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[11]);
            e.printStackTrace(System.out);
        }
    }

    void treeGeneration(String fName) throws MyException {
        //Nuskaitomi uždavinio parametrai
        getParameters();
        //Sukuriamas aibės objektas, priklausomai nuo medžio pasirinkimo
        //cmbTreeType objekte
        switch (cmbTreeType.getSelectedIndex()) {
            case 0:
                placeSet = new BstSetKTUx(new Place());
                break;
            case 1:
                placeSet = new AvlSetKTUx(new Place());
                break;
            default:
                enableButtons(false);
                throw new MyException(rb.getStringArray("msgs")[0]);
        }
        //Jei failas nenaudojamas
        if (fName == null) {
            autoArray = PlaceGenerator.generateAndMix(sizeOfGenSet, sizeOfInitialSubSet, coef);
            panParam2.getTfOfTable()[2].setText(sizeOfLeftSubSet + "");
        } else { //Jei skaitoma is failo
            placeSet.load(fName);
            autoArray = new Place[placeSet.size()];
            int i = 0;
            for (Object o : placeSet.toArray()) {
                autoArray[i++] = (Place) o;
            }
            //Skaitant iš failo išmaišoma standartiniu Collections.shuffle metodu.
            Collections.shuffle(Arrays.asList(autoArray), new Random());
        } //Išmaišyto masyvo elementai surašomi i aibę
        placeSet.clear();
        for (Place a : autoArray) {
            placeSet.add(a);
        }
        //Išvedami rezultatai
        //Nustatoma, kad eilutės pradžioje neskaičiuotų išvedamų eilučių skaičiaus
        KsSwing.setFormatStartOfLine(false);
        KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter),
                rb.getStringArray("msgs")[4]);
        //Nustatoma, kad eilutės pradžioje skaičiuotų išvedamų eilučių skaičių
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, placeSet, rb.getStringArray("msgs")[5]);
        enableButtons(true);
    }

    private void treeAdd() throws MyException {
        Place bk = PlaceGenerator.imtiIsBazes();
        placeSet.add(bk);
        panParam2.getTfOfTable()[2].setText(--sizeOfLeftSubSet + "");
        KsSwing.setFormatStartOfLine(false);
        KsSwing.oun(taTree, bk, rb.getStringArray("msgs")[6]);
        KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
        KsSwing.setFormatStartOfLine(true);
        KsSwing.oun(taOutput, placeSet, rb.getStringArray("msgs")[6]);
    }

    private void getChildren() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            int father = Integer.parseInt(panParam2.getParametersOfTable()[5]);
            //int father = 5;
            System.out.println("Integeris "+father);
            if (placeSet.size() < father) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
            } else {
                Place place = (Place) placeSet.toArray()[father];
                SetADT<Place> placeSet2 = new BstSetKTUx(new Place());
                placeSet2 = placeSet.tailSet(place);
                KsSwing.setFormatStartOfLine(false);
                KsSwing.oun(taTree, place, rb.getStringArray("msgs")[16]);
                KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
                KsSwing.setFormatStartOfLine(true);
                if (placeSet2.isEmpty()) {
                    KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
                } else {
                    KsSwing.oun(taOutput, placeSet2, rb.getStringArray("msgs")[16]);
                }
            }
        }
    }

    private void getHeadSet() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            SetADT<Place> placeSet2 = new BstSetKTUx(new Place());
            int nr = new Random().nextInt(placeSet.size());
            Place place = (Place) placeSet.toArray()[nr];
            placeSet2 = placeSet.headSet(place);
            KsSwing.setFormatStartOfLine(false);
            KsSwing.oun(taTree, place, rb.getStringArray("msgs")[13]);
            KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
            KsSwing.setFormatStartOfLine(true);
            if (placeSet2.isEmpty()) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
            } else {
                KsSwing.oun(taOutput, placeSet2, rb.getStringArray("msgs")[13]);
            }
        }
    }

    private void getSubSet() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            SetADT<Place> placeSet2 = new BstSetKTUx(new Place());
            int nr = new Random().nextInt(placeSet.size());
            Place place = (Place) placeSet.toArray()[nr];
            Integer gap = Integer.parseInt(panParam2.getParametersOfTable()[4].replace("-", "x"));
            Place place2 = (Place) placeSet.toArray()[nr + gap];
            placeSet2 = placeSet.subSet(place, place2);
            KsSwing.setFormatStartOfLine(false);
            KsSwing.oun(taTree, place, rb.getStringArray("msgs")[15]);
            KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
            KsSwing.setFormatStartOfLine(true);
            if (placeSet2.isEmpty()) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
            } else {
                KsSwing.oun(taOutput, placeSet2, rb.getStringArray("msgs")[15]);
            }
        }
    }

    private void getTailSet() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            SetADT<Place> placeSet2 = new BstSetKTUx(new Place());
            int nr = new Random().nextInt(placeSet.size());
            Place place = (Place) placeSet.toArray()[nr];
            placeSet2 = placeSet.tailSet(place);
            KsSwing.setFormatStartOfLine(false);
            KsSwing.oun(taTree, place, rb.getStringArray("msgs")[14]);
            KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
            KsSwing.setFormatStartOfLine(true);
            if (placeSet2.isEmpty()) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
            } else {
                KsSwing.oun(taOutput, placeSet2, rb.getStringArray("msgs")[14]);
            }
        }
    }

    private void treeRemove() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            int nr = new Random().nextInt(placeSet.size());
            Place place = (Place) placeSet.toArray()[nr];
            placeSet.remove(place);
            KsSwing.setFormatStartOfLine(false);
            KsSwing.oun(taTree, place, rb.getStringArray("msgs")[8]);
            KsSwing.oun(taTree, placeSet.toVisualizedString(treeDrawType, delimiter));
            KsSwing.setFormatStartOfLine(true);
            if (placeSet.isEmpty()) {
                KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
            } else {
                KsSwing.oun(taOutput, placeSet, rb.getStringArray("msgs")[8]);
            }
        }
    }

    private void hOfTree() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            KsSwing.oun(taOutput, placeSet.hOfTree(), rb.getStringArray("msgs")[12]);
        }
    }

    private void treeIteration() {
        if (placeSet.isEmpty()) {
            KsSwing.ounerr(taOutput, rb.getStringArray("msgs")[10]);
        } else {
            KsSwing.oun(taOutput, placeSet, rb.getStringArray("msgs")[9]);
        }
    }

    private void treeEfficiency() throws MyException {
        KsSwing.oun(taOutput, "", rb.getStringArray("msgs")[1]);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GreitaveikosTyrimas gt = new GreitaveikosTyrimas(taOutput, panButtons.getButtons(), rb);
                gt.start();
            }
        });
    }

    private void getParameters() throws MyException {
        //Truputėlis kosmetikos..
        for (int i = 0; i < 3; i++) {
            panParam2.getTfOfTable()[i].setBackground(Color.WHITE);
        }
        //Nuskaitomos parametrų reiksmės. Jei konvertuojant is String
        //įvyksta klaida, sugeneruojama NumberFormatException situacija. Tam, kad
        //atskirti kuriame JTextfield'e ivyko klaida, panaudojama nuosava
        //situacija MyException
        int i = 0;
        try {
            //Pakeitimas (replace) tam, kad sukelti situaciją esant
            //neigiamam skaičiui            
            sizeOfGenSet = Integer.parseInt(panParam2.getParametersOfTable()[i].replace("-", "x"));
            sizeOfInitialSubSet = Integer.parseInt(panParam2.getParametersOfTable()[++i].replace("-", "x"));
            sizeOfLeftSubSet = sizeOfGenSet - sizeOfInitialSubSet;
            ++i;
            coef = Double.parseDouble(panParam2.getParametersOfTable()[++i].replace("-", "x"));
        } catch (NumberFormatException e) {
            //Galima ir taip: pagauti ir vėl mesti
            throw new MyException(panParam2.getParametersOfTable()[i], i);
        }
        //Nuskaitomas medžio elemento simbolis ir celės teksto kirtiklis
        treeDrawType = (String) cmbTreeSymbols.getSelectedItem();
        delimiter = tfDelimiter.getText();
    }

    private void enableButtons(boolean enable) {
        for (int i : new int[]{1, 2, 4, 5, 6}) {
            panButtons.getButtons()[i].setEnabled(enable);
        }
    }

    public JTextArea getTaOutput() {
        return taOutput;
    }
    private JTextArea taTree = new JTextArea();
    private JScrollPane scrollTree = new JScrollPane(taTree);
    private JTextArea taOutput = new JTextArea();
    private JScrollPane scrollOutput = new JScrollPane(taOutput);
    private JSplitPane splResults = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JTextField tfDelimiter = new JTextField("", 8);
    private JTextField tfInput = new JTextField(15);
    private JComboBox cmbTreeSymbols = new JComboBox();
    private JComboBox cmbTreeType = new JComboBox();
    private JPanel panSouth = new JPanel();
    private JScrollPane scrollSouth = new JScrollPane(panSouth);
    private JPanel panParam1 = new JPanel();
    private MyPanels panParam2, panButtons;
    private static Place[] autoArray;
    private static SortedSetADTx<Place> placeSet;
    private int sizeOfInitialSubSet, sizeOfGenSet, sizeOfLeftSubSet;
    private double coef;
    private String treeDrawType, delimiter;
}