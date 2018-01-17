package chapter3;

import java.util.*;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/2
 * Time:19:28
 */
public class MyLinkedList<Anytype> implements Iterable<Anytype> {
    public MyLinkedList(){
        doClear();
    }

    public void clear(){
        doClear();
    }

    private void doClear() {
        beginMarker = new Node<Anytype>(null,null,null);
        endMarker = new Node<>(null,beginMarker,null);
        beginMarker.next = endMarker;
        theSize = 0;
        modCount++;
    }

    private static class Node<Anytype>{
        public Node(Anytype d, Node<Anytype> p,Node<Anytype> n){
            data = d;
            prev = p;
            next = n;
        }
        public Anytype data;
        public Node<Anytype> prev;
        public Node<Anytype> next;
    }


    public int size(){
        return theSize;
    }

    public boolean isEmpty(){
        return size()==0;
    }

    public boolean add(Anytype x){
        add(size(),x);
        return true;
    }

    public void add(int index, Anytype x) {
        addBefore(getNode(index,0,size()),x);
    }

    public void addFirst(Anytype x){
        addBefore(beginMarker.next,x);
    }
    public void addLast(Anytype x){
        addBefore(endMarker.prev,x);
    }
    /**
     * Adds an item to this collection,at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if index is not between 0 and size()-1.
     * */
    private void addBefore(Node<Anytype> p, Anytype x) {
        Node<Anytype> newNode = new Node<>(x,p.prev,p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        //p.prev.next = p.prev = newNode;上面两句可以合并成这一句
        //p.prev.next = p.prev = new Node<>(x,p.prev,p);上面三句可以合并成这一句
        //三句易于理解，一句整洁
        theSize++;
        modCount++;
    }

    /**
     * Gets the node at position index,which must range from 0 to size()-1.
     * @param index search index
     * @return internal node corresponding to index
     * @throws IndexOutOfBoundsException if index is not between 0 and size()-1,inclusive
     * */
    private Node<Anytype> getNode(int index){
        return getNode(index,0,size()-1);
    }

    /**
     * Gets the node at position index,which must range from lower to upper.
     * @param index search index.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to index.
     * @throws IndexOutOfBoundsException if index is not between lower and upper,inclusive.
     * */
    private Node<Anytype> getNode(int index, int lower, int upper) {
        Node<Anytype> p;
        if(index < lower || index > upper){
            throw new IndexOutOfBoundsException();
        }
        if(index < size()/2){
            p = beginMarker.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        }else {
            p = endMarker;
            for (int i = size(); i > index; i--) {
                p = p.prev;
            }
        }
        return p;
    }

    public Anytype get(int index){
        return getNode(index).data;
    }

    public Anytype set(int index,Anytype newval){
        Node<Anytype> p = getNode(index);
        Anytype oldval = p.data;
        p.data = newval;
        return oldval;
    }


    public Anytype remove(int index){
        return remove(getNode(index));
    }

    /**
     * Removes the object contain in Node p.
     * @param p the node containing the object.
     * @return the item was removed from the collection.
     * */
    private Anytype remove(Node<Anytype> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        return p.data;
    }
    public void removeFirst(){
        remove(beginMarker.next);
    }
    public void removeLast(){
        remove(endMarker.prev);
    }
    public void removeAll(Iterable<? extends Anytype> items){
        Anytype item,element;
        Iterator<? extends Anytype> it = items.iterator();
        while (it.hasNext()){
            item = it.next();
            Iterator<? extends Anytype> itlist = iterator();
            while (itlist.hasNext()){
                element = itlist.next();
                if (element.equals(item)){
                    itlist.remove();
                }
            }
        }
    }

    public Anytype getFirst() {
        return get(0);
    }
    public Anytype getLast(){
        return get(size()-1);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(Anytype x:this){
            sb.append(x+" ");
        }
        sb.append("]");
        return new String(sb);
    }

    @Override
    public Iterator<Anytype> iterator() {
        return new LinkedListIterator();
    }
    private int theSize;
    private int modCount = 0;
    private Node<Anytype> beginMarker;
    private Node<Anytype> endMarker;

    private class LinkedListIterator implements Iterator<Anytype> {
        private Node<Anytype> current = beginMarker.next;
        private int expectedModcount = modCount;
        private boolean okToRemove = false;
        @Override
        public boolean hasNext() {
            return current != endMarker;
        }

        @Override
        public Anytype next() {
            if(modCount != expectedModcount){
                throw new ConcurrentModificationException();
            }
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Anytype nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if(modCount != expectedModcount){
                throw new ConcurrentModificationException();
            }
            if(!okToRemove){
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(current.prev);
            expectedModcount++;
            okToRemove = false;
        }
    }
}

class TestLinkedList{
    public static void main(String[] args){
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);
        for (int i = 10; i < 20; i++) {
            list.add(list.size(),i);
        }
        System.out.println(list);
        list.remove(0);
        list.remove(list.size()-1);
        System.out.println(list);
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()){
            it.next();
            it.remove();
            System.out.println(list);
        }
    }

    public static <Anytype> void printList(Iterator<Anytype> it) {
//        if (!it.hasNext()) {
//            return;
//        }
//        System.out.println(it.next());
//        printList(it);
//尾递归使用不当，当调用超过20000个活动记录的栈时，栈可能溢出
        while(true){
        if (!it.hasNext()) {
            return;
        }
        System.out.println(it.next());
        }
    }
}


