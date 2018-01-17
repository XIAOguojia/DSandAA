package chapter5;

import java.util.Random;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/24
 * Time:11:10
 * 布谷鸟散列
 */
public class CuckooHashTable<Anytype> {
    //最大装填因子
    private static final double MAX_LOAD = 0.4;
    //允许再散列的次数
    private static final int ALLOWED_REHASHES = 1;
    private int rehashes = 0;
    //默认表的大小
    private static final int DEFAULT_TABLE_SIZE = 101;

    private final HashFamily<? super Anytype> hashFunctions;
    private final int numHashFunctions;
    private Anytype[] array;
    private int currentSize;
    private Random r = new Random();

    /**
     * Construct the hash table.
     *
     * @param hf the hash family
     */
    public CuckooHashTable(HashFamily<? super Anytype> hf) {
        this(hf, DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     *
     * @param hf   the hash family
     * @param size the approximate initial size.
     */
    public CuckooHashTable(HashFamily<? super Anytype> hf, int size) {
        allocateArray(nextPrime(size));
        doClear();
        hashFunctions = hf;
        numHashFunctions = hf.getNumberOfFunctions();

    }

    private boolean insertHelper1(Anytype x) {
        final int COUNT_LIMIT = 100;
        while (true) {
            int lastPos = -1;
            int pos;
            for (int count = 0; count < COUNT_LIMIT; count++) {
                for (int i = 0; i < numHashFunctions; i++) {
                    pos = myhash(x, i);
                    if (array[pos] == null) {
                        array[pos] = x;
                        currentSize++;
                        return true;
                    }
                }
                int i = 0;
                do {
                    pos = myhash(x, r.nextInt(numHashFunctions));
                } while (pos == lastPos && i++ <= 5);
                Anytype tmp = array[lastPos = pos];
                array[pos] = x;
                x = tmp;
            }
            if (++rehashes > ALLOWED_REHASHES) {
                expand();
                rehashes = 0;
            } else {
                rehash();
            }
        }
    }

    private boolean insertHelper2(Anytype x) {
        final int COUNT_LIMIT = 100;
        while (true) {
            for (int count = 0; count < COUNT_LIMIT; count++) {
                int pos = myhash(x, count % numHashFunctions);
                Anytype tmp = array[pos];
                if (tmp == null) {
                    return true;
                } else {
                    x = tmp;
                }
                if (++rehashes > ALLOWED_REHASHES) {
                    expand();
                    rehashes = 0;
                } else {
                    rehash();
                }
            }
        }
    }

    private void rehash() {
        hashFunctions.generateNewFunctions();
        rehash(array.length);
    }

    private void expand() {
        rehash((int) (array.length / MAX_LOAD));
    }

    private void rehash(int newLength) {
        Anytype[] oldArray = array;
        allocateArray(nextPrime(newLength));
        currentSize = 0;
        for (Anytype str : oldArray) {
            if (str != null) {
                insert(str);
            }
        }
    }

    /**
     * Insert into the hash table. If the item is
     * already present, return false.
     *
     * @param x the item to insert.
     */
    public boolean insert(Anytype x) {
        if (contains(x)) {
            return false;
        }
        if (currentSize >= array.length * MAX_LOAD) {
            expand();
        }
        return insertHelper1(x);
    }

    /**
     * Find an item in the hash table.
     *
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains(Anytype x) {
        return findPos(x) != -1;
    }

    /**
     * Method that searches all hash function places.
     *
     * @param x the item to search for.
     * @return the position where the search terminates, or -1 if not found.
     */
    private int findPos(Anytype x) {
        for (int i = 0; i < numHashFunctions; i++) {
            int pos = myhash(x, i);
            if (array[pos] != null && array[pos].equals(x)) {
                return pos;
            }
        }
        return -1;
    }

    private int myhash(Anytype x, int which) {
        int hashVal = hashFunctions.hash(x, which);
        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }
        return hashVal;
    }

    /**
     * Internal method to allocate array.
     *
     * @param arraySize the size of the array.
     */
    private void allocateArray(int arraySize) {
        array = (Anytype[]) new Object[arraySize];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     *
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private int nextPrime(int n) {
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
    private boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i < n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public void makeEmpty() {
        doClear();
    }

    private void doClear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    /**
     * Gets the size of the table.
     *
     * @return number of items in the hash table.
     */
    public int size() {
        return currentSize;
    }

    /**
     * Gets the length (potential capacity) of the table.
     *
     * @return length of the internal array in the hash table.
     */
    public int capacity() {
        return array.length;
    }

    /**
     * Remove from the hash table.
     *
     * @param x the item to remove.
     * @return true if item was found and removed
     */
    public boolean remove(Anytype x) {
        int pos = findPos(x);
        if (pos != -1) {
            array[pos] = null;
            currentSize--;
        }
        return pos != -1;
    }

    public static void main(String[] args) {
        long cumulative = 0;
        final int NUMS = 20000;
        final int GAP = 37;
        final int ATTEMPTS = 10;

        System.out.println("Checking...");
        long startTime = System.currentTimeMillis();

        for (int att = 0; att < ATTEMPTS; att++) {
            System.out.println("ATTEMPT:" + att);
            CuckooHashTable<String> H = new CuckooHashTable<String>(new StringHashFamily(3));
            for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
                H.insert("" + i);
            }
            for (int i = GAP; i !=0 ; i=(i+GAP)%NUMS) {
                if (H.insert(""+i)){
                    System.out.println("shit!!!" + i);
                }
            }
            for (int i = 1; i < NUMS; i += 2) {
                H.remove("" + i);
            }
            for (int i = 2; i < NUMS; i += 2) {
                if (!H.contains("" + i)) {
                    System.out.println("contains bug" + i);
                }
            }
            for (int i = 1; i < NUMS; i += 2) {
                if (H.contains("" + i)) {
                    System.out.println("...shit" + i);
                }
            }
            long endTime = System.currentTimeMillis();
            cumulative += endTime - startTime;
            if (H.capacity() > (NUMS * 4)) {
                System.out.println("Large capacity:"+H.capacity());
            }
            System.out.println(H.size()+"   "+H.capacity());
        }

        System.out.println( "Total elapsed time is: " + cumulative );
    }
}
