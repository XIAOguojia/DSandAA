package chapter10;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/28
 * Time:16:19
 */
public class Fibonacci {
    /**
     * Compute Fibonacci numbers as described in Chapter 1.
     * 经典递归实现斐波那契数列
     */
    public static int fib(int n){
        if (n<=1){
            return 1;
        }
        return fib(n-1)+fib(n-2);
    }

    /**
     * 斐波那契的线性算法
     * 时间复杂度O(N)
     * */
    public static int fib2(int n){
        if (n<=1){
            return 1;
        }
        int last = 1;
        int nextToLast = 1;
        int answer = 1;
        for (int i = 2; i <= n; i++) {
            answer = last + nextToLast;
            nextToLast = last;
            last = answer;
        }
        return answer;
    }

    public static void main(String[] args) {
        long start,end;

        start = System.currentTimeMillis();
        System.out.println(fib(100));
        end = System.currentTimeMillis();
        System.out.println("Time:"+(end-start));

        start = System.currentTimeMillis();
        System.out.println(fib2(100));
        end = System.currentTimeMillis();
        System.out.println("Time:"+(end-start));
    }

}
