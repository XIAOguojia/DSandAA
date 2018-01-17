package chapter6;

import java.nio.BufferUnderflowException;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/15
 * Time:19:22
 * @author Raven
 */
public class LeftistHeap<Anytype extends Comparable<? super Anytype>> {
    private Node<Anytype> root;

    /**
     * Construct the leftist heap.
     */
    public LeftistHeap() {
        root = null;
    }

    private static class Node<Anytype> {
        //Constructors
        Node(Anytype theElement) {
            this(theElement, null, null);
        }

        public Node(Anytype theElement, Node<Anytype> lt, Node<Anytype> rt) {
            element = theElement;
            left = lt;
            right = rt;
            npl = 0;
        }

        //The data in the node
        Anytype element;
        //Left child
        Node<Anytype> left;
        //Right child
        Node<Anytype> right;
        //null path length
        int npl;
    }

    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     *
     * @param rhs the other leftist heap.
     */
    public void merge(LeftistHeap<Anytype> rhs) {
        //Avoid aliasing problems
        if (this == rhs) {
            return;
        }
        root = merge(root, rhs.root);
        rhs.root = null;
    }

    /**
     * Internal method to merge two roots.
     * Deals with deviant cases and calls recursive merge1.
     */
    private Node<Anytype> merge(Node<Anytype> h1, Node<Anytype> h2) {
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }
        if (h1.element.compareTo(h2.element) < 0) {
            return mergel(h1, h2);
        } else {
            return mergel(h2, h1);
        }
    }

    /**
     * Internal method to merge two roots.
     * Assumes trees are not empty, and h1's root contains smallest item.
     */
    private Node<Anytype> mergel(Node<Anytype> h1, Node<Anytype> h2) {
        if (h1.left == null) {
            h1.left = h2;
        } else {
            h1.right = merge(h1.right, h2);
            if (h1.left.npl < h1.right.npl) {
                swapChildren(h1);
            }
            h1.npl = h1.right.npl + 1;
        }
        return h1;
    }

    /**
     * Swaps t's two children.
     */
    private static <AnyType> void swapChildren(Node<AnyType> t) {
        Node<AnyType> tmp = t.left;
        t.left = t.right;
        t.right = tmp;
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
        return root.element;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     *
     * @param x the item to insert.
     */
    public void insert(Anytype x) {
        root = merge(new Node<Anytype>(x), root);
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
        Anytype minItem = root.element;
        root = merge(root.left, root.right);
        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    public static void main(String[] args) {
        int nums = 100;
        LeftistHeap<Integer> h = new LeftistHeap<>();
        LeftistHeap<Integer> h1 = new LeftistHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % nums) {
            if (i % 2 == 0) {
                h1.insert(i);
            } else {
                h.insert(i);
            }
        }
        h.merge(h1);
        for (i = 1; i < nums; i++) {
            if (h.deleteMin() != i) {
                System.out.println("bug:" + i);
            }
        }
    }
}
