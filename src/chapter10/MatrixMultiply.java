package chapter10;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/28
 * Time:15:18
 *
 * @author Raven
 */
public class MatrixMultiply {
    /**
     * Standard matrix multiplication.
     * Arrays start at 0.
     * Assumes a and b are square.NxN
     * 时间复杂度O(N^3)
     */
    public static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];
        //初始化
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = 0;
            }
        }

//      做乘法
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return c;
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] c = multiply(a, a);
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c.length; j++) {
                System.out.print(c[i][j] + "  ");
            }
        }
    }
}
