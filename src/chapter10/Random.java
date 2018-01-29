package chapter10;

import chapter7.Sort;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/29
 * Time:10:19
 *
 * @author Raven
 */
public class Random {
    private static final int A = 48271;
    private static final int M = 2147483647;
    private static final int Q = M / A;
    private static final int R = M % A;

    /**
     * Construct this Random object with
     * initial state obtained from system clock.
     */
    public Random() {
        this((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
    }

    /**
     * Construct this Random object with
     * specified initial state.
     *
     * @param initialValue the initial state.
     */
    public Random(int initialValue) {
        if (initialValue < 0) {
            initialValue += M;
        }
        state = initialValue;
        if (state == 0) {
            state = 1;
        }
    }

    /**
     * Return a pseudorandom int, and change the
     * internal state.
     *
     * @return the pseudorandom int.
     */
    public int randomInt() {
        int tmpState = A * (state % Q) - R * (state / Q);
        if (tmpState >= 0) {
            state = tmpState;
        } else {
            state = tmpState + M;
        }

        return state;
    }

    /**
     * Return an int in the closed range [low,high], and
     * change the internal state.
     *
     * @param low  the minimum value returned.
     * @param high the maximum value returned.
     * @return the pseudorandom int.
     */
    public int randomInt(int low, int high) {
        double partitionSize = (double) M / (high - low + 1);
        return (int) (randomInt() / M) + low;
    }

    /**
     * Return an long in the closed range [low,high], and
     * change the internal state.
     *
     * @param low  the minimum value returned.
     * @param high the maximum value returned.
     * @return the pseudorandom long.
     */
    public long randomLong(long low, long high) {
        long longVal = ((long) randomInt() << 31) + randomInt();
        long longM = ((long) M << 31) + M;
        double partitionSize = (double) longM / (high - low + 1);
        return (long) (longVal / partitionSize) + low;
    }

    /**
     * Randomly rearrange an array.
     * The random numbers used depend on the time and day.
     *
     * @param a the array.
     */
    public static void permute(Object[] a) {
        Random r = new Random();
        for (int i = 1; i < a.length; i++) {
            Sort.swapReference(a, i, r.randomInt(0, i));
        }
    }

    private int state;

    public static void main(String[] args) {
        Random r = new Random(1);
        for (int i = 0; i < 20; i++) {
            System.out.println(r.randomInt());
        }
    }

}
