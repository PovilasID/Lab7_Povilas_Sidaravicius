package studijosKTU;

import java.util.Comparator;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija AVL-medžiu. @užduotis
 * Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @author darius.matulis@ktu.lt
 */
public class AvlSetKTU<Data extends Comparable<Data>> extends BstSetKTU<Data>
        implements SortedSetADT<Data> {

    private AVLNode<Data> n;

    public AvlSetKTU() {
    }

    public AvlSetKTU(Comparator<Data> c) {
        super(c);
        this.c = c;
    }

   public int hOfTree(){
       return heightOfTree(n);
   }
    
    int heightOfTree(AVLNode<Data> node) {
        AVLNode<Data> root = (AVLNode<Data>) getRoot();
        return root.height;
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
        setRoot(addRecursive(data, (AVLNode<Data>) getRoot()));
        if (!returned) {
            returned = true;
            return false;
        }
        setSize(size() + 1);
        return true;
    }

//==============================================================================
// Privatus rekursinis metodas naudojamas add metode;
//==============================================================================
    private AVLNode<Data> addRecursive(Data data, AVLNode<Data> n) {
        if (n == null) {
            return n = new AVLNode<Data>(data);
        }
        int cmp = (c == null)
                ? data.compareTo(n.data)
                : c.compare(data, n.data);
        if (cmp < 0) {
            n.setLeft(addRecursive(data, n.getLeft()));
            if ((height(n.getLeft()) - height(n.getRight())) == 2) {
                int cmp2 = (c == null)
                        ? data.compareTo(n.left.data)
                        : c.compare(data, n.left.data);
                n = (cmp2 < 0) ? rightRotation(n) : doubleRightRotation(n);
            }
        } else if (cmp > 0) {
            n.right = addRecursive(data, n.getRight());
            if ((height(n.getRight()) - height(n.getLeft())) == 2) {
                int cmp2 = (c == null)
                        ? n.right.data.compareTo(data)
                        : c.compare(n.right.data, data);
                n = (cmp2 < 0) ? leftRotation(n) : doubleLeftRotation(n);
            }
        } else {
            returned = false;
        }
        n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;
        return n;
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
        setRoot(removeRecursive(data, (AVLNode<Data>) getRoot()));

        if (!returned) {
            returned = true;
            return false;
        }
        setSize(size() - 1);
        return true;
    }

    private AVLNode<Data> removeRecursive(Data data, AVLNode<Data> n) {
        
        if (n == null) {
            returned = false;
            return n;
        } else if (data.compareTo(n.data) < 0){
            n.left = removeRecursive(data, n.getLeft());

        if (height(n.getRight()) - height(n.getLeft()) == 2) {
                if (height(n.getRight().getRight())>= height(n.getRight().getLeft())) {
                    n = rightRotation(n);
                } else {
                    n = doubleRightRotation(n);
                }
            }
        
        n.height = heightOfTree(n);
        } else if (data.compareTo(n.data) > 0){
            n.right = removeRecursive(data, n.getRight());
            
            if (height(n.getLeft()) - height(n.getRight()) == 2) {
                if (height(n.getLeft().getLeft()) >= height(n.getLeft().getRight())) {
                    n = leftRotation(n);
                } else {
                    n = doubleLeftRotation(n);
                }
            }
            n.height = heightOfTree(n);
        } else if (n.right == null){
            AVLNode<Data> tmpReturn = n.getLeft();
            n = null;
            return tmpReturn;
        } else if (n.left == null){
            AVLNode<Data> tmpReturn = n.getRight();
            n = null;
            return tmpReturn;
        } else {
            AVLNode<Data> tmpReturn = findMax(n.getLeft());
            removeRecursive(tmpReturn.data, n); 
            n.data = tmpReturn.data;
        }
        return n;
        

    }
    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AVLNode<Data> findMax(AVLNode<Data> t) {
        if (t == null) {
            return t;
        }

        while (t.right != null) {
            t = t.getRight();
        }
        return t;
    }

//==============================================================================
// Papildomi privatūs metodai, naudojami operacijų su aibe realizacijai
// AVL-medžiu;
//==============================================================================
//==============================================================================
//        n2
//       /                n1
//      n1      ==>      /  \
//     /                n3  n2
//    n3
//==============================================================================
    private AVLNode<Data> rightRotation(AVLNode<Data> n2) {
        AVLNode<Data> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<Data> leftRotation(AVLNode<Data> n1) {
        AVLNode<Data> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

//==============================================================================
//        n3               n3
//       /                /                n2
//      n1      ==>      n2      ==>      /  \
//       \              /                n1  n3
//        n2           n1
//============================================================================== 
    private AVLNode<Data> doubleRightRotation(AVLNode<Data> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<Data> doubleLeftRotation(AVLNode<Data> n1) {
        n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<Data> n) {
        return (n == null) ? -1 : n.height;
    }

//==============================================================================
//Vidinė kolekcijos mazgo klasė
//==============================================================================
    class AVLNode<Data> extends BstNode<Data> {

        int height;

        AVLNode(Data data) {
            super(data);
            this.height = 0;
        }

        public void setLeft(AVLNode<Data> left) {
            super.left = (BstNode) left;
        }

        public AVLNode<Data> getLeft() {
            return (AVLNode<Data>) left;
        }

        public void setRight(AVLNode<Data> right) {
            super.right = (BstNode) right;
        }

        public AVLNode<Data> getRight() {
            return (AVLNode<Data>) right;
        }
    }
}