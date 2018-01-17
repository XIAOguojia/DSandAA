package chapter5;

import java.util.Random;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/24
 * Time:15:00
 */
public class StringHashFamily implements HashFamily<String> {
    private final int[] MULTIPIIERS;
    private final Random r = new Random();

    public StringHashFamily(int d) {
        MULTIPIIERS = new int[d];
        generateNewFunctions();
    }

    @Override
    public int hash(String x, int which) {
        final int mutipilier = MULTIPIIERS[which];
        int hashVal = 0;
        for (int i = 0; i < x.length(); i++) {
            hashVal = mutipilier * hashVal + x.charAt(i);
        }
        return hashVal;
    }

    @Override
    public int getNumberOfFunctions() {
        return MULTIPIIERS.length;
    }

    @Override
    public void generateNewFunctions() {
        for (int i = 0; i < MULTIPIIERS.length; i++) {
            MULTIPIIERS[i] = r.nextInt();
        }
    }
}
