package chapter3;

import javax.xml.soap.Node;
import java.util.LinkedList;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/22
 * Time:21:06
 */
public class Deque<Anytype> {
    private LinkedList<Anytype> L;
    public Deque(){
        L = new LinkedList<>();
    }
    public void push(Anytype x){
        L.addFirst(x);

    }
    public Anytype pop(){
        return L.removeFirst();
    }
    public void inject(Anytype x){
        L.addLast(x);
    }
    public Anytype eject(){
        return L.removeLast();
    }


}
