package chapter3;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/2
 * Time:11:19
 */
public class MyArrayList<Anytype> implements Iterable<Anytype> {
    private static final int DEFAULT_CAPACITY = 10;
    private int theSize;
    private Anytype[] theItems;
    public MyArrayList(){
        doClear();
    }

    public void clear(){
        doClear();
    }

    private void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int Size(){
        return theSize;
    }

    public boolean Isempty(){
        return Size()==0;
    }

    public void trimToSize(){
        ensureCapacity(Size());
    }

    public Anytype get(int index){
        if(index < 0 || index >= Size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return theItems[index];
    }

    public Anytype set(int index,Anytype newval){
        if(index < 0 || index >= Size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        Anytype old = theItems[index];
        theItems[index] = newval;
        return old;
    }

    public void ensureCapacity(int newCapacity){
        if(newCapacity < theSize){
            return;
        }
        Anytype[] old = theItems;
        theItems = (Anytype[])new Object[newCapacity];
        for (int i = 0; i < Size() ; i++) {
            theItems[i] = old[i];
        }
    }

    public boolean add(Anytype x){
        add(Size(),x);
        return true;
    }

    public void add(int index,Anytype x){
        if(theItems.length == Size()){
            ensureCapacity(Size()*2+1);
        }
        for (int i = theSize; i > index ; i--) {
            theItems[i] = theItems[i-1];
        }
        theItems[index] = x;
        theSize++;
    }

    public void addAll(Iterable<? extends Anytype> items){
        Iterator<? extends  Anytype> it = items.iterator();
        while (it.hasNext()){
            add(it.next());
        }

    }

    public Anytype remove(int index){
        Anytype removedItem = theItems[index];
        for (int i = index; i < Size()-1; i++) {
                theItems[i]=theItems[i+1];
        }
        theSize--;
        return removedItem;
    }

    @Override
    public Iterator<Anytype> iterator(){
        return new ArrayListIterator<Anytype>(this);
    }

     /**
     * Returns a String representation of this collection.
     */
    @Override
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( Anytype x : this ) {
            sb.append( x + " " );
        }
        sb.append( "]" );

        return new String( sb );
    }

    private static class ArrayListIterator<Anytype> implements Iterator<Anytype>{
        private int current = 0;
        private MyArrayList theList;
        public ArrayListIterator(MyArrayList<Anytype> list) {
            theList = list;
        }

        @Override
        public boolean hasNext() {
            return current < theList.Size();
        }

        @Override
        public Anytype next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            //return (Anytype) theList.theItems[current++];//还不能用是因为theItems在MyArrayList中是私有的
            //return theItems[current++];//这是非法的，因为theItems不是ArrayListIterator的一部分它是MyArrayList的一部分，所以这没意义。
            return (Anytype) theList.theItems[current++];
            //无static时是一个内部类，加了static表示ArrayListIterator是一个嵌套类，嵌套类被认为是外部类的一部分，不存在产生不可见问题
        }
        @Override
        public void remove(){
            theList.remove(--current);
        }
    }
    Iterator<Anytype> reverseIterator(){
        return new ArrayListReverseIterator();
    }
    private class ArrayListReverseIterator implements Iterator<Anytype>{
        private int current = Size()-1;

        @Override
        public boolean hasNext() {
            return current>=0;
        }

        @Override
        public Anytype next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            return theItems[current--];
        }
        @Override
        public void remove(){
            MyArrayList.this.remove(--current);
        }
    }
}
class TestArrayList{
    public static void main(String[] args){
        MyArrayList<Integer> list1 = new MyArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }
        for (int i = 10; i < 20; i++) {
            list1.add(0,i);
        }
        System.out.println(list1);
        Iterator<Integer> it = list1.iterator();
        while (it.hasNext()){
            System.out.print("  "+it.next());
        }
        System.out.println();
        list1.remove(0);
        for (Integer i:list1){
            System.out.print("  "+i);
        }
    }
}
