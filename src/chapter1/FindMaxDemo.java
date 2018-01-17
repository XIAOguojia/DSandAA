package chapter1;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Created by intellij IDEA
 * User:Raven
 * Date:2017/11/20
 * Time:23:55
 */
public class FindMaxDemo {
    public static void main(String[] args){
        Comparable[] shapes = {
                (Comparable) new Circle(2.0),
                (Comparable) new Circle(3.0),
                (Comparable) new Circle(4.0)
                //       new Rectangle(3.0, 4.0)
        };
        String[] strings = {"joe","Bob","Bill","zeke"};
        System.out.println(findMax(shapes));
        System.out.println(findMax(strings));
        int val = 38;
        
    }
    private static Comparable findMax(Comparable[] arr) {
        /**
         * 返回数组的最大值
         * 数组长度必须大于0
         * */
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].compareTo(arr[index])>0){
                index = i;
            }
        }
        return arr[index];
    }
}
