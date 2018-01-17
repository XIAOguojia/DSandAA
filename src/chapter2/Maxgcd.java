package chapter2;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/11/29
 * Time:20:03
 * Function:求两个整数的最大公约数
 */
public class Maxgcd {
    public static void main(String[] args) {
//        int m = 50,n = 15;
//        System.out.println("Maxgcd1 = "+ Maxgcd1(m,n));
        long p = 2, x = 10;
//        System.out.println("Pow = " + pow(p, x));
        System.out.println("Pow2 = " + pow2(p,x));
    }

    /**
     * 幂运算，求一个数的n次方，运行时间O(logN)
     */
    private static long pow(long p, long x) {
        if (0 == x) {
            return 1;
        } else if (0 == x % 2) {
            return pow(p * p, x / 2);
        } else {
            return pow(p * p, x / 2) * p;
        }

    }
    /**
     * @param p 底数
     * @param x 指数
     * 用循环代替递归
     * */
    private static long pow2(long p, long x) {
        long y = 1;
        for (long i = x; i >= 0; i /= 2) {
            if (i == 0) {
                p = 1;

                break;
            }
            if (i == 1) {
                p = p;
                break;
            }
            if (0 == i % 2) {
                p *= p;
            } else {
                y *= p;
                p *= p;

            }
        }
        return p * y;
    }

    /**
     * 求两个数的最大公约数
     */
    private static int Maxgcd1(int m, int n) {
        while (n != 0) {
            int temp = m % n;
            m = n;
            n = temp;
        }
        return m;
    }
}
