package chapter10;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/28
 * Time:16:34
 *
 * @author Raven
 */
public class Fig10_45 {
    /**
     * 以递归的方式计算
     */
    public static double eval(int n) {
        if (n == 0) {
            return 1.0;
        }
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += eval(i);
        }
        return 2.0 * sum / n + n;
    }

    /**
     * 用O(N^2)的时间复杂度运行
     */
    public static double eval2(int n) {
        double[] c = new double[n + 1];
        c[0] = 1.0;
        for (int i = 1; i <= n; i++) {
            double sum = 0.0;
            for (int j = 0; j < i; j++) {
                sum += c[j];
            }
            c[i] = 2 * sum / i + i;
        }
        return c[n];
    }

    /***
     *简化到以O(N)时间运行
     */
    public static double eval3(int n) {
        if (n == 0) {
            return 1.0;
        }

        double sum = 0.0;
        double last = 1.0;
        double answer = 0.0;

        for (int i = 1; i <= n; i++) {
            sum += last;
            answer = 2.0 * sum / i + i;
            last = answer;
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(eval(10));
        System.out.println(eval2(10));
        System.out.println(eval3(10));
    }
}
