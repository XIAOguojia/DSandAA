package chapter1;

import java.util.Comparator;

/**
 * Created by intellij IDEA
 * User:Raven
 * Date:2017/11/21
 * Time:21:18
 * Function:用函数对象(function object)来实现找最大值
 * 一个函数通过将其放在一个对象内部而被传递，这样的的对象通常叫做函数对象
 */
public class functionO_FindMax {
    public static void main(String[] args) {
        String[] arr = {"asdf", "yukh", "ZUTF"};
        System.out.println(FindMax(arr, new CaseInsensitiveCompare()));
        //一个函数对象作为第二个参数传递给FindMax
        //函数作为参数是注意到对象既包含数据也包含方法，可以定义一个没有数据而只有一个方法的类，并传递给该类的一个实例

    }


    public static <Anytype> Anytype FindMax(Anytype[] arr, Comparator<? super Anytype> cmp) {
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (cmp.compare(arr[i], arr[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return arr[maxIndex];
    }
}

class CaseInsensitiveCompare implements Comparator<String> {
    @Override
    public int compare(String lhs, String rhs) {
        return lhs.compareToIgnoreCase(rhs);
    }
}