package chapter2;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/16
 * Time:14:24
 */
public class BinarySearch {
    public static void main(String[] args) {
        Integer[] a = {4, 8, 9, 2, 3, 4, 6, 87, 1, 0};
        //获取开始时间,单位毫秒
//        long startTime = System.currentTimeMillis();
        //获取开始时间,单位纳秒
        long startTime=System.nanoTime();
        System.out.println("binarySearch1:" + binarySearch1(a, 3));
        //binarSearch1的运行时间
//        long endTime1 = System.currentTimeMillis();
        long endTime1 = System.nanoTime();
        System.out.println("binarySearch2:" + binarySearch2(a, 3));
        //binarySearch2的运行时间
//        long endTime2 = System.currentTimeMillis();
        long endTime2 = System.nanoTime();
        System.out.println("binarySearch1_Time:" + (endTime1 - startTime));
        System.out.println("binarySearch2_Time:" + (endTime2 - endTime1));
    }

    /**
     * 每次迭代执行三次比较
     */
    public static <Anytype extends Comparable<? super Anytype>> int binarySearch1(Anytype[] a, Anytype x) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = x.compareTo(a[mid]);
            if (compareResult < 0) {
                high = mid - 1;
            } else if (compareResult > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 每次迭代只执行两次查找，虽然比较次数比较少，但是必须要执行到low>high时才退出循环，
     * 但又因为运行时间是O(logN)，所以增加的循环次数不会很多。
     */
    public static <Anytype extends Comparable<? super Anytype>> int binarySearch2(Anytype[] a, Anytype x) {
        int low = 0, high = a.length - 1;
        int current = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = x.compareTo(a[mid]);
            if (compareResult < 0) {
                high = mid - 1;
            } else {
                current = mid;
                low = mid + 1;
            }
        }
        return (current != -1 && a[current] == x) ? current : -1;
    }

}
