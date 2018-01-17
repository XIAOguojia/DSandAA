package chapter3;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/9
 * Time:16:18
 */
public class SingleList {
    public SingleList(){
        init();
    }
    private boolean add(Object x){
        if (contains(x)){
            return false;
        }else {
            Node<Object> p = new Node<Object>(x);

            p.next = head.next;
            head.next = p;
            theSize++;
        }
        return true;
    }
    private boolean remove(Object x){
        if (!contains(x)){
            return false;
        }else {
            Node<Object> p = head.next;
            Node<Object> trailer = head;
            while (!p.data.equals(x)){
                trailer = p;
                p = p.next;
            }
            trailer.next = p.next;
            theSize--;
        }
        return true;
    }
    public int size(){
        return theSize;
    }
    private void print(){
        Node<Object> p = head.next;
        while (p!=null){
            System.out.print(p.data+" ");
            p = p.next;
        }
        System.out.println();
    }
    private boolean contains(Object x){
        Node<Object> p = head.next;
        while (p!=null){
            if (x.equals(p.data)){
                return true;
            }else {
                p = p.next;
            }
        }
        return false;
    }
    private void init(){
        theSize = 0;
        head = new Node<Object>();
        head.next = null;
    }
    private Node<Object> head;
    private int theSize;
    private class Node<Object>{
        Node(){
            this(null,null);
        }
        Node(Object d){
            this(d,null);
        }
        Node(Object d,Node n){
            data = d;
            next = n;
        }
        Object data;
        Node next;
    }
}
