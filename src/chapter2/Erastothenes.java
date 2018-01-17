package chapter2;

import static java.lang.Math.sqrt;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/4
 * Time:11:28
 * 用厄拉多塞筛法求素数
 */
public class Erastothenes {
    private static final int N = 100;
    public static void main(String[] args){
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 2; i < sqrt(N); i++) {
            if (arr[i]!=0){
                for (int j = 2; i*j < N; j++) {
                    arr[j*i] = 0;
                }
            }
        }
        System.out.println("These are prime numbers:");
        for (int i = 2; i < arr.length; i++) {
            if (arr[i]!=0) {
                System.out.print("  " + arr[i]);
            }
        }
    }
}
