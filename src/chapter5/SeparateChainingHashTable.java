package chapter5;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/22
 * Time:10:55
 */
public class SeparateChainingHashTable<Anytype> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    /**
     * The array of Lists.
     */
    private List<Anytype>[] theLists;
    private int currentSize;

    /**
     * Construct the hash table.
     */
    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     *
     * @param size approximate table size.
     */
    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<>();
        }
    }

    /**
     * Insert into the hash table. If the item is
     * already present, then do nothing.
     *
     * @param x the item to insert.
     */
    public void insert(Anytype x) {
        List<Anytype> whichList = theLists[myhash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);
            if (++currentSize > theLists.length) {
                rehash();
            }
        }
    }

    private int myhash(Anytype x) {
        int hashVal = x.hashCode();
        hashVal %= theLists.length;
        if (hashVal < 0) {
            hashVal += theLists.length;
        }
        return hashVal;
    }

    private void rehash() {
        List<Anytype>[] oldList = theLists;
        theLists = new List[nextPrime(2 * theLists.length + 1)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<>();
        }
        currentSize = 0;
        for (List<Anytype> list : oldList) {
            for (Anytype item : list) {
                insert(item);
            }
        }
    }

    /**
     * Remove from the hash table.
     *
     * @param x the item to remove.
     */
    public void remove(Anytype x) {
        List<Anytype> whichList = theLists[myhash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    /**
     * Find an item in the hash table.
     *
     * @param x the item to search for.
     * @return true if x isnot found.
     */
    public boolean contains(Anytype x) {
        List<Anytype> whichList = theLists[myhash(x)];
        return whichList.contains(x);
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++) {
            theLists[i].clear();
        }
        currentSize = 0;
    }

    /**
     * A hash routine for String objects.
     *
     * @param key       the String to hash.
     * @param tableSize the size of the hash table.
     * @return the hash value.
     */
    public static int hash(String key, int tableSize) {
        int hashVal = 0;
        for (int i = 0; i < key.length(); i++) {
            hashVal = 37 * hashVal + key.charAt(i);
        }
        hashVal %= tableSize;
        if (hashVal < 0) {
            hashVal += tableSize;
        }
        return hashVal;
    }

    /**
     * Internal method to find a prime number at least as large as n.
     *
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n) {
        if (n % 2 == 0) {
            n++;
        }
        for (; !isPrime(n); n += 2) {
            ;
        }
        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     *
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SeparateChainingHashTable<Integer> s = new SeparateChainingHashTable<>();
        long startTime = System.currentTimeMillis();
        final int NUMS = 2000000;
        final int GAP = 37;
        System.out.println("checking......");
        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            s.insert(i);
        }
        for (int i = 1; i < NUMS; i += 2) {
            s.remove(i);
        }
        for (int i = 2; i < NUMS; i += 2) {
            if (!s.contains(i)) {
                System.out.println("bugger1");
            }
        }
        for (int i = 1; i < NUMS; i += 2) {
            if (s.contains(i)) {
                System.out.println("bugger2");
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed Time:" + (endTime - startTime));
    }
}
