package chapter4;

import java.nio.BufferUnderflowException;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/13
 * Time:21:52
 */
public class AvlTree<Anytype extends Comparable<? super Anytype>> {
    private AvlNode<Anytype> root;
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Construct the tree.
     */
    public AvlTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(Anytype x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
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
    private AvlNode<Anytype> remove(Anytype x, AvlNode<Anytype> t) {
        if (t == null) {
            return t;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public Anytype findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public Anytype findMax() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains(Anytype x) {
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (root == null) {
            System.out.println("Empty tree.");
        } else {
            printTree(root);
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree(AvlNode<Anytype> t) {
        if (t != null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(Anytype x, AvlNode<Anytype> t) {
        while (t != null) {
            int compareResult = x.compareTo(t.element);
            if (compareResult < 0) {
                t = t.left;
            } else if (compareResult > 0) {
                t = t.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<Anytype> findMax(AvlNode<Anytype> t) {
        if (t == null) {
            return t;
        }
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<Anytype> findMin(AvlNode<Anytype> t) {
        if (t == null) {
            return t;
        }
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<Anytype> insert(Anytype x, AvlNode<Anytype> t) {
        if (t == null) {
            return new AvlNode<Anytype>(x);
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else {
            ;//重复的元素不用插入
        }
        return balance(t);
    }

    // Assume t is either balanced or within one of being balanced
    private AvlNode<Anytype> balance(AvlNode<Anytype> t) {
        if (t == null) {
            return t;
        }
        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<Anytype> doubleWithRightChild(AvlNode<Anytype> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<Anytype> rotateWithRightChild(AvlNode<Anytype> k1) {
        AvlNode<Anytype> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left),height(k1.right))+1;
        k2.height = Math.max(height(k2.right),k1.height)+1;
        return k2;
    }
    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<Anytype> doubleWithLeftChild(AvlNode<Anytype> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }
    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<Anytype> rotateWithLeftChild(AvlNode<Anytype> k2) {
        AvlNode<Anytype> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k1.left),height(k2.left))+1;
        k1.height = Math.max(height(k1.left),k2.height)+1;
        return k1;
    }

    public void checkBalance() {
        checkBalance(root);
    }

    private int checkBalance(AvlNode<Anytype> t) {
        if (t == null) {
            return -1;
        }
        if (t != null) {
            int hl = checkBalance(t.left);
            int hr = checkBalance(t.right);
            if (Math.abs(height(t.left) - height(t.right)) > 1 || hl != height(t.left) || hr != height(t.right)) {
                System.out.println("PAPAPAPA!!!Slap Face.");
            }
        }
        return height(t);
    }
    /**
     * Return the height of node t, or -1, if null.
     */
    private int height(AvlNode<Anytype> t) {
        return t==null?-1:t.height;
    }


    private static class AvlNode<Anytype> {
        //Constructors
        AvlNode(Anytype theElement) {
            this(theElement, null, null);
        }

        AvlNode(Anytype theElement, AvlNode<Anytype> lt, AvlNode<Anytype> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        Anytype element;
        AvlNode<Anytype> left;
        AvlNode<Anytype> right;
        int height;
    }

    public static void main(String[] args) {
        AvlTree<Integer> t = new AvlTree<>();
        final int SMALL = 40;
        final int NUMS = 10000;
        final int GAP = 37;
        System.out.println("check....");
        for (int i = GAP; i != 0; i=(i+GAP)%NUMS) {
            //System.out.println("insert:"+i);
            t.insert(i);
            if(NUMS < SMALL){
                t.checkBalance();
            }
        }

        for (int i = 1; i < NUMS; i+=2) {
            System.out.println("remove"+i);
            t.remove(i);
            if (NUMS<SMALL){
                t.checkBalance();
            }
        }
        if (NUMS<SMALL){
            t.printTree();
        }
        if(t.findMin()!=2||t.findMax()!=NUMS-2){
            System.out.println("Remove error");
        }
        for (int i = 2; i < NUMS; i+=2) {
            if (!t.contains(i)){
                System.out.println("Find error");
            }
        }

        for (int i = 1; i < NUMS; i+=2) {
            if (t.contains(i)){
                System.out.println("Find error2");
            }
        }

    }
}
