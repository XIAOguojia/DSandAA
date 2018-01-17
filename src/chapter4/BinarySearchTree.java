package chapter4;

import java.nio.BufferUnderflowException;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/5
 * Time:17:27
 */

public class BinarySearchTree<Anytype extends Comparable<? super Anytype>> {
    //Test program

    public static void main(String[] args) {

        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        final int NUMS = 4000;
        final int GAP = 37;
        int count = 0;
        System.out.println("Checking....");
        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            t.insert(i);
            if (i < 37){
                System.out.print(i+"  ");
            }
            count++;
        }
        System.out.println("count"+count);
//        for (int i = 1; i < NUMS; i += 2) {
//            t.remove(i);
//        }
//        if (NUMS < 40){
//            t.printTree();
//        }
//        if(t.findMin() != 2 ||t.findMax() != NUMS-2){
//            System.out.println("findMin or findMax error!");
//        }
//        for (int i = 2; i < NUMS; i+=2) {
//                if(!t.contains(i)){
//                    System.out.println("Find error1!");
//                }
//        }
//        for (int i = 1; i < NUMS; i+=2) {
//            if (t.contains(i)){
//                System.out.println("Find error2!");
//            }
//        }

    }

    private static class BinaryNode<Anytype> {
        //Constructors
        BinaryNode(Anytype theElement) {
            this(theElement, null, null);
        }

        BinaryNode(Anytype theElement, BinaryNode<Anytype> lt, BinaryNode<Anytype> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        Anytype element;
        BinaryNode<Anytype> left;
        BinaryNode<Anytype> right;
    }

    private BinaryNode<Anytype> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(Anytype x) {
        return contains(x, root);
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node tha roots the subtree.
     * @return true if the item is found;false otherwise.
     */
    private boolean contains(Anytype x, BinaryNode<Anytype> t) {
        if (t == null) {
            return false;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            return contains(x, t.left);
        } else if (compareResult > 0) {
            return contains(x, t.right);
        } else {
            return true;
        }
    }

    public Anytype findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMin(root).element;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<Anytype> findMin(BinaryNode<Anytype> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    public Anytype findMax() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMax(root).element;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<Anytype> findMax(BinaryNode<Anytype> t) {
        if (t == null) {
            return null;
        }
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    public void insert(Anytype x) {
        root = insert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<Anytype> insert(Anytype x, BinaryNode<Anytype> t) {
        if (t == null) {
            return new BinaryNode<Anytype>(x, null, null);
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else {
            ;//Duplicate
        }
        return t;
    }

    public void remove(Anytype x) {
        root = remove(x, root);
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<Anytype> remove(Anytype x, BinaryNode<Anytype> t) {
        if (t == null) {
            return t;   //Item not found;do nothing
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {    //Two children
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        } else {
            printTree(root);
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<Anytype> t) {
        //Depth first,left first.
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Internal method to compute height of a subtree.
     *
     * @param t the node that roots the subtree.
     */
    private int height(BinaryNode<Anytype> t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(height(t.left), height(t.right));
        }
    }


}
