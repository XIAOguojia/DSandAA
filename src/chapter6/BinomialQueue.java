package chapter6;

import java.nio.BufferUnderflowException;
import java.util.PriorityQueue;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/15
 * Time:21:45
 */
public class BinomialQueue<Anytype extends Comparable<? super Anytype>> {
    private static final int DEFAULT_TREES = 1;
    //items in priority queue
    private int currentSize;
    //An array of tree roots
    private Node<Anytype>[] theTrees;

    /**
     * Construct the binomial queue.
     */
    public BinomialQueue() {
        theTrees = new Node[DEFAULT_TREES];
        makeEmpty();
    }


    /**
     * Construct with a single item.
     */
    public BinomialQueue(Anytype item) {
        currentSize = 1;
        theTrees = new Node[1];
        theTrees[0] = new Node<>(item, null, null);
    }


    private void expandTheTrees(int newNumTrees) {
        Node<Anytype>[] old = theTrees;
        int oldNumTrees = theTrees.length;
        theTrees = new Node[newNumTrees];
        for (int i = 0; i < Math.min(oldNumTrees, newNumTrees); i++) {
            theTrees[i] = old[i];
        }
        for (int i = oldNumTrees; i < newNumTrees; i++) {
            theTrees[i] = null;
        }
    }

    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     *
     * @param rhs the other binomial queue.
     */
    public void merge(BinomialQueue<Anytype> rhs) {
        if (this == rhs) {
            return;         //Avoid aliasing problem
        }
        currentSize += rhs.currentSize;
        if (currentSize > capacity()) {
            int newNumTrees = Math.max(theTrees.length, rhs.theTrees.length) + 1;
            expandTheTrees(newNumTrees);
        }

        Node<Anytype> carry = null;
        for (int i = 0, j = 1; j <= currentSize; i++, j *= 2) {
            Node<Anytype> t1 = theTrees[i];
            Node<Anytype> t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;
            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;
            switch (whichCase) {
                //No trees
                case 0:
                    //Only this
                case 1:
                    break;
                //Only rhs
                case 2:
                    theTrees[i] = t2;
                    rhs.theTrees[i] = null;
                    break;
                //Only carry
                case 4:
                    theTrees[i] = carry;
                    carry = null;
                    break;
                //this and rhs
                case 3:
                    carry = combineTrees(t1, t2);
                    theTrees[i] = rhs.theTrees[i] = null;
                    break;
                //this and carry
                case 5:
                    carry = combineTrees(carry, t1);
                    theTrees[i] = null;
                    break;
                //rhs and carry
                case 6:
                    carry = combineTrees(carry, t2);
                    rhs.theTrees[i] = null;
                    break;
                //All trees
                case 7:
                    theTrees[i] = carry;
                    carry = combineTrees(t1, t2);
                    rhs.theTrees[i] = null;
                    break;
                default:
            }
        }

        for (int k = 0; k < rhs.theTrees.length; k++) {
            rhs.theTrees[k] = null;
        }
        rhs.currentSize = 0;
    }

    /**
     * Return the result of merging equal-sized t1 and t2.
     */
    private Node<Anytype> combineTrees(Node<Anytype> t1, Node<Anytype> t2) {
        if (t1.element.compareTo(t2.element) > 0) {
            return combineTrees(t2, t1);
        }
        t2.nextSibling = t1.leftChild;
        t1.leftChild = t2;
        return t1;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * This implementation is not optimized for O(1) performance.
     *
     * @param x the item to insert.
     */
    public void insert(Anytype x) {
        merge(new BinomialQueue<>(x));
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public Anytype findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return theTrees[findMinIndex()].element;
    }

    private int findMinIndex() {
        int i;
        int minIndex;
        for (i = 0; theTrees[i] == null; i++) {
            ;
        }
        for (minIndex = i; i < theTrees.length; i++) {
            if (theTrees[i] != null && theTrees[i].element.compareTo(theTrees[minIndex].element) < 0) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public Anytype deleteMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        int minIndex = findMinIndex();
        Anytype minItem = theTrees[minIndex].element;
        Node<Anytype> deleteTree = theTrees[minIndex].leftChild;

        //Construct H''
        BinomialQueue<Anytype> deleteQueue = new BinomialQueue<>();
        deleteQueue.expandTheTrees(minIndex);
        deleteQueue.currentSize = (1 << minIndex) - 1;
        for (int j = minIndex - 1; j >= 0; j--) {
            deleteQueue.theTrees[j] = deleteTree;
            deleteTree = deleteTree.nextSibling;
            deleteQueue.theTrees[j].nextSibling = null;
        }

        //Construct H'
        theTrees[minIndex] = null;
        currentSize -= deleteQueue.currentSize + 1;
        merge(deleteQueue);
        return minItem;
    }


    /**
     * Return the capacity.
     */
    private int capacity() {
        return (1 << theTrees.length) - 1;
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < theTrees.length; i++) {
            theTrees[i] = null;
        }
    }

    private static class Node<Anytype> {
        //Constructor
        Node(Anytype theElement) {
            this(theElement, null, null);
        }

        Node(Anytype theElement, Node<Anytype> lt, Node<Anytype> nt) {
            element = theElement;
            leftChild = lt;
            nextSibling = nt;
        }

        //The data in the node
        Anytype element;
        //Left child
        Node<Anytype> leftChild;
        //Right child
        Node<Anytype> nextSibling;
    }

    public static void main(String[] args) {
        int i = 37;
        BinomialQueue<Integer> h = new BinomialQueue<>();
        BinomialQueue<Integer> h1 = new BinomialQueue<>();
        int numItems = 10000;
        System.out.println("checking...");
        for (i = 37; i != 0; i = (i + 37) % numItems)

        {
            if (i % 2 == 0) {
                h1.insert(i);
            } else {
                h.insert(i);
            }
        }
        h.merge(h1);
        for (i = 1; i < numItems; i++)

        {
            if (h.deleteMin() != i) {
                System.out.println("bug:" + i);
            }
        }
        System.out.println("Check done.");

    }
}
