package studijosKTU;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu. @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @author darius.matulis@ktu.lt
 */
public class BstSetKTU<Data extends Comparable<Data>> implements
        SortedSetADT<Data>, Cloneable {

    //Medžio šaknies mazgas
    private BstNode<Data> root = null;
    //Medžio dydis
    private int size = 0;
    //Kintamasis, nurodantis ar pavyko operacija su aibe
    boolean returned = true;
    //Rodyklė į komparatorių. Jei c = null, tada naudojamas 
    //Comparable<T>
    Comparator<Data> c = null;

    //Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<T>
    public BstSetKTU() {
    }
    //Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<T>

    public BstSetKTU(Comparator<Data> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Grąžinamas aibėje esančių elementų kiekis.
     *
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas data.
     *
     * @return Grąžinama true, jei aibėje egzistuoja elementas data.
     */
    @Override
    public boolean contains(Data data) {
        if (data == null) {
            throw new NullPointerException("Null pointer in contains");
        }
        if (root == null) {
            return false;
        }
        return (c == null)
                ? get(data).data.compareTo(data) == 0
                : c.compare(get(data).data, data) == 0;
    }

    /**
     * Aibė papildoma nauju elementu ir grąžinama true.
     *
     * @return Aibė papildoma nauju elementu ir grąžinama true.
     */
    @Override
    public boolean add(Data data) {
        if (data == null) {
            return false;
        }
        root = addRecursive(data, root);
        //Jei elementas egzistuoja aibėje, grąžinama false
        if (!returned) {
            returned = true;
            return false;
        }
        size++;
        return true;
    }

    /**
     * Pašalinamas elementas data iš aibės.
     *
     * @return Gražinama true, pašalinus elementą iš aibės.
     */
    @Override
    public boolean remove(Data data) {
        if (data == null) {
            return false;
        }
        root = removeRecursive(data, root);
        //Jei nerasta ka pasalinti, grazinama false
        if (!returned) {
            returned = true;
            return false;
        }
        size--;
        return true;
    }

//==============================================================================
// Papildomi metodai, naudojami operacijų su aibe realizacijai
// dvejetainiu paieškos medžiu;
//==============================================================================
    private BstNode<Data> addRecursive(Data data, BstNode<Data> n) {
        if (n == null) {
            return n = new BstNode<Data>(data);
        }
        int cmp = (c == null)
                ? data.compareTo(n.data)
                : c.compare(data, n.data);
        if (cmp < 0) {
            n.left = addRecursive(data, n.left);
        } else if (cmp > 0) {
            n.right = addRecursive(data, n.right);
        } else {
            returned = false;
        }
        return n;
    }

    private BstNode<Data> removeRecursive(Data data, BstNode<Data> n) {
        if (n == null) {
            returned = false;
            return n;
        }
        //Medyje ieškomas šalinamas elemento mazgas;
        int cmp = (c == null)
                ? data.compareTo(n.data)
                : c.compare(data, n.data);
        if (cmp < 0) {
            n.left = removeRecursive(data, n.left);
        } else if (cmp > 0) {
            n.right = removeRecursive(data, n.right);
        } else if (n.left != null && n.right != null) {
            /*
             * Atvejis kai šalinamas elemento mazgas turi abu vaikus. Ieškomas
             * didžiausio rakto elemento mazgas kairiajame pomedyje. Galima kita
             * realizacija kai ieškomas mažiausio rakto elemento mazgas
             * dešiniajame pomedyje. Tam yra sukurtas metodas getMin(Data data);
             */
            BstNode<Data> nodeMax = getMax(n.left);
            /*
             * Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             * elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            n.data = nodeMax.data;
            //Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            n.left = removeMax(n.left);
            //Kiti atvejai
        } else {
            n = (n.left != null) ? n.left : n.right;
        }
        return n;
    }

    //Grąžina elementą data turintį mazgą. Naudojamas contains metode;
    BstNode<Data> get(Data data) {
        if (data == null) {
            throw new NullPointerException("Null pointer int get");
        }
        BstNode<Data> n = root;
        BstNode<Data> nParent = null;
        while (n != null) {
            nParent = n;
            int cmp = (c == null)
                    ? data.compareTo(n.data)
                    : c.compare(data, n.data);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return nParent;
    }

    //Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
    BstNode<Data> removeMax(BstNode<Data> n) {
        if (n == null) {
            return n = null;
        } else if (n.right != null) {
            n.right = removeMax(n.right);
            return n;
        } else {
            return n.left;
        }
    }

    //Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
    BstNode<Data> getMax(BstNode<Data> n) {
        return get(n, true);
    }

    //Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
    BstNode<Data> getMin(BstNode<Data> n) {
        return get(n, false);
    }

    private BstNode<Data> get(BstNode<Data> n, boolean findMax) {
        BstNode<Data> parent = null;
        while (n != null) {
            parent = n;
            n = (findMax) ? n.right : n.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Grąžinamas dvejetainio paieškos medžio šaknis
     *
     * @return Grąžinamas dvejetainio paieškos medžio šaknies mazgas.
     */
    BstNode<Data> getRoot() {
        return root;
    }

    /**
     * Keičiama dvejetainio paieškos medžio šaknis
     *
     * @param root šaknies mazgas.
     */
    void setRoot(BstNode<Data> root) {
        this.root = root;
    }

    /**
     * Keičiamas aibės elementų kiekis
     *
     * @param size elementų kiekis.
     */
    void setSize(int size) {
        this.size = size;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        return toStringRecursive(root);
    }

   public int hOfTree(){
       return heightOfTree(root);
   }
    
    int heightOfTree(BstNode<Data> node) {
        if (node == null) {
            return 0;
        } else {
            return 1
                    + Math.max(heightOfTree(node.left),
                    heightOfTree(node.right));
        }
    }

    String toStringRecursive(BstNode<Data> n) {
        if (n == null) {
            return "";
        }
        String result = toStringRecursive(n.left);
        result += n.data.toString() + "\n";
        result += toStringRecursive(n.right);
        return result;
    }

    /**
     * Sukuria ir grąžina medžio kopiją.
     *
     * @return medžio kopijos objektas.
     */
    @Override
    public Object clone() {
        BstSetKTU<Data> cl = null;
        try {
            cl = (BstSetKTU<Data>) super.clone();
            if (root == null) {
                return cl;
            }
            cl.clear();
            cloneRecursive(cl, root);
            cl.size = this.size;
        } catch (CloneNotSupportedException ex) {
        }
        return cl;
    }

    private BstNode<Data> cloneRecursive(BstSetKTU<Data> cl, BstNode<Data> n) {
        if (n == null) {
            return null;
        }
        cl.add(n.data);
        cloneRecursive(cl, n.left);
        cloneRecursive(cl, n.right);
        return n;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @return Grąžinamas aibės poaibis iki elemento data.
     */
    @Override
    public SetADT<Data> headSet(Data data) {
        if (data == null) {
            throw new NullPointerException();
        }
        SetADT<Data> hs = new BstSetKTU();
        if (contains(data)) {
            BstNode<Data> n = get(data);
            headRecursive(hs, n);
        }
        return hs;
    }

    /**
     * 
     * @param hs - headset poaibis
     * @param nMod - visa aibe, kuria naudosim
     * @return 
     */
    private BstNode<Data> headRecursive(SetADT<Data> hs, BstNode<Data> nMod) {
        if (nMod == null) {
            return null;
        }
        hs.add(nMod.data);
        headRecursive(hs, nMod.left);
        headRecursive(hs, nMod.right);
        return nMod;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     *
     * @return Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     */
    @Override
    public SetADT<Data> subSet(Data data1, Data data2) {
        if (data1 == null || data2 == null) {
            throw new NullPointerException();
        }
        SetADT<Data> ss = new BstSetKTU(); //SubSet Poaibis
        if (contains(data1) && contains(data2)) {
            BstNode<Data> n = get(data1);
            subRecursive(ss, n, data2);
        }
        return ss;
    }
    /**
    * 
    * @param sSet - poaibis
    * @param n - nuo kur nagrinesim
    * @param d - iki kur nagrinesim
    * @return 
    */
    private BstNode<Data> subRecursive(SetADT<Data> sSet,
            BstNode<Data> n, Data d) {
        if (n == null) {
            return null;
        }
        if (n.data != d) {
            sSet.add(n.data);
            subRecursive(sSet, n.left, d);
            subRecursive(sSet, n.right, d);
        } else {
            sSet.add(n.data);
        }
        return n;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     *
     * @return Grąžinamas aibės poaibis nuo elemento data.
     */
    @Override
    public SetADT<Data> tailSet(Data data) {
        if (data == null) {
            throw new NullPointerException();
        }
        SetADT<Data> ts = new BstSetKTU(); //TailSet Poaibis
        if (contains(data)) {
            BstNode<Data> n = root;
            subRecursive(ts, n, data);
        }
        return ts;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<Data> iterator() {
        return new IteratorKTU(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<Data> descendingIterator() {
        return new IteratorKTU(false);
    }

//==============================================================================
// Vidinė objektų kolekcijos iteratoriaus klasė.
// Iteratoriai: didėjantis ir mažėjantis.
// Kolekcija iteruojama kiekviena elementa aplankant viena karta vidine (angl.
// inorder) tvarka. Visi aplankyti elementai saugomi steke. Stekas panaudotas iš
// java.util paketo, bet galima susikurti nuosavą.
//==============================================================================
    class IteratorKTU implements Iterator<Data> {

        private Stack<BstNode<Data>> stack = new Stack<BstNode<Data>>();
        //Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka,
        //false - mažėjimo
        private boolean ascending;
        //Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<Data> parent = root;

        IteratorKTU(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public Data next() {
            if (!stack.empty()) {
                BstNode<Data> n = stack.pop();
                parent = (!stack.empty()) ? stack.peek() : root;
                if (ascending) {
                    toStack(n.right);
                } else {
                    toStack(n.left);
                }
                return n.data;
            } else {
                return null;
            }
        }

        @Override
        //TODO
        public void remove() {
            if (stack == null) {
                throw new IllegalStateException();
            } else {
                stack = null;
                parent = null;
            }
        }

        private void toStack(BstNode<Data> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

//==============================================================================
//Vidinė kolekcijos mazgo klasė
//==============================================================================
    class BstNode<Data> {

        //Elementas
        Data data;
        //Rodyklė į kairįjį pomedį
        BstNode<Data> left;
        //Rodyklė į dešinįjį pomedį
        BstNode<Data> right;

        BstNode() {
        }

        BstNode(Data data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
}