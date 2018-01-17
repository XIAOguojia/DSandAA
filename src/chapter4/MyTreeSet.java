package chapter4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/23
 * Time:8:41
 */
public class MyTreeSet<Anytype extends Comparable<? super Anytype>> {
    private BinaryNode<Anytype> root;
    int modCount = 0;

    public MyTreeSet() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
        modCount++;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(Anytype x) {
        return contains(x, root);
    }

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

    public Anytype findMin() throws UnderflowException {
        if (isEmpty()) {
            throw new UnderflowException();
        }
        return findMin(root).element;
    }

    public Anytype findMax() throws UnderflowException {
        if (isEmpty()) {
            throw new UnderflowException();
        }
        return findMax(root).element;
    }

    public void insert(Anytype x) {
        root = insert(x, root, null);
    }

    private BinaryNode<Anytype> insert(Anytype x, BinaryNode<Anytype> t, BinaryNode<Anytype> pt) {
        if (t == null) {
            modCount++;
            return new BinaryNode<Anytype>(x, null, null, pt);
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right, t);
        } else {
            ;
        }
        return t;
    }

    private static class BinaryNode<Anytype> {
        BinaryNode(Anytype theElement) {
            this(theElement, null, null, null);
        }

        public BinaryNode(Anytype theElement, BinaryNode<Anytype> lt, BinaryNode<Anytype> rt, BinaryNode<Anytype> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        Anytype element;
        BinaryNode<Anytype> left;
        BinaryNode<Anytype> right;
        BinaryNode<Anytype> parent;

    }

    public Iterator<Anytype> iterator() {
        return new MyTreeSetIterator();
    }

    private class MyTreeSetIterator implements Iterator<Anytype> {
        private BinaryNode<Anytype> current = findMin(root);
        private BinaryNode<Anytype> previous;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private boolean atEnd = false;

        @Override
        public boolean hasNext() {
            return !atEnd;
        }

        @Override
        public Anytype next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Anytype nextItem = current.element;
            previous = current;
            if (current.right != null) {
                current = findMin(current.right);
            } else {
                BinaryNode<Anytype> child = current;
                current = current.parent;
                while (current != null && current.left != child) {
                    child = current;
                    current = current.parent;
                }
                if (current == null) {
                    atEnd = true;
                }
            }
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            MyTreeSet.this.remove(previous.element);
            okToRemove = false;
        }
    }

    private void remove(Anytype x) {
        root = remove(x, root);
    }

    private BinaryNode<Anytype> remove(Anytype x, BinaryNode<Anytype> t) {
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
            t.right = remove(t.element,t.right);
        } else {
            modCount++;
            BinaryNode<Anytype> oneChild;
            oneChild = (t.left != null) ? t.left : t.right;
            oneChild.parent = t.parent;
            t = oneChild;
        }
        return t;
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        } else {
            printTree(root);
        }
    }

    private void printTree(BinaryNode<Anytype> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private BinaryNode<Anytype> findMin(BinaryNode<Anytype> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    private BinaryNode<Anytype> findMax(BinaryNode<Anytype> t) {
        if (t == null) {
            return null;
        } else if (t.right == null) {
            return t;
        }
        return findMax(t.right);
    }
}

class UnderflowException extends Exception {

}