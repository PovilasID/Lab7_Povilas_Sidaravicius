package studijosKTU;

public interface SortedSetADTx<Data> extends SortedSetADT<Data> {

    public void add(String dataString);

    public void load(String fName);

    public String toVisualizedString(String treeDrawType, String dataCodeDelimiter);

    public Object clone();
    
     public int hOfTree();
         /**
     * Grąžinamas aibės poaibis iki elemento data.
     * @return Grąžinamas aibės poaibis iki elemento data.
     */
    SetADT<Data> headSet(Data data);

    /**
     * Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     * @return Grąžinamas aibės poaibis nuo elemento data1 iki data2.
     */
    SetADT<Data> subSet(Data data1, Data data2);

    /**
     * Grąžinamas aibės poaibis iki elemento data.
     * @return Grąžinamas aibės poaibis nuo elemento data.
     */
    SetADT<Data> tailSet(Data data);
}