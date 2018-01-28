package chapter6;

import java.nio.BufferUnderflowException;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/15
 * Time:14:23
 */
public class BinaryHeap<Anytype extends Comparable<? super Anytype>> {
    /**
     * Construct the binary heap.
     */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct the binary heap.
     *
     * @param capacity the capacity of the binary heap.
     */
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (Anytype[]) new Comparable[capacity + 1];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public BinaryHeap(Anytype[] items) {
        currentSize = items.length;
        array = (Anytype[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (Anytype item : items) {
            array[i++] = item;
        }
        buildHeap();
    }

    private static final int DEFAULT_CAPACITY = 10;
    //Number of elements in heap
    private int currentSize;
    /**
     * The heap array
     */
    private Anytype[] array;

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Internal method to percolate down in the heap.
     *
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        Anytype tmp = array[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                child++;
            }
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     *
     * @param x the item to insert.
     */
    public void insert(Anytype x) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1);
        }

        //Percolate up
        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    /**
     * 重写insert方法把被插入项的引用放在位置0处
     */
    public void insert0(Anytype x) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (; hole > 1 && x.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[0] = array[hole] = x;
    }

    private void enlargeArray(int newSize) {
        Anytype[] old = array;
        array = (Anytype[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++) {
            array[i] = old[i];
        }
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public Anytype findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return array[1];
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
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public Anytype deleteMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }

        Anytype MinItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);
        return MinItem;
    }

    //Test program
    public static void main(String[] args) {
        int NumItem = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>();
        int i = 37;
        for (i = 37; i != 0; i = (i + 37) % NumItem) {
            h.insert(i);
        }
        for (i = 1; i < NumItem; i++) {
            if (h.deleteMin() != i) {
                System.out.println("bug:" + i);
            }
        }
    }
}
