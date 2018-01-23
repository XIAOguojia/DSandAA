package chapter5;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/22
 * Time:16:00
 */
public class QuadraticProbingHashTable<Anytype> {
    private static final int DEFAULT_TABLE_SIZE = 11;
    private HashEntry<Anytype>[] array;
    private int currentSize;
    // The number of occupied cells
    private int occupied;

    /**
     * Construct the hash table.
     */
    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     *
     * @param size the approximate initial size.
     */
    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        makeEmpty();
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        currentSize = 0;

    }

    /**
     * Get current size.
     *
     * @return the size.
     */
    public int size() {
        return currentSize;
    }

    /**
     * Test if the QuadraticProbingHashTable is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Get length of internal table.
     *
     * @return the size.
     */
    public int capacity() {
        return array.length;
    }

    /**
     * Internal method to allocate array.
     *
     * @param size the size of the array.
     */
    private void allocateArray(int size) {
        array = new HashEntry[nextPrime(size)];
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
        for (int i = 3; i * i < n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static class HashEntry<Anytype> {
        //the element
        public Anytype element;
        //false is marked deleted
        public boolean isActive;

        public HashEntry(Anytype e) {
            this(e, true);
        }

        public HashEntry(Anytype e, boolean b) {
            element = e;
            isActive = b;
        }
    }

    /**
     * Find an item in the hash table.
     *
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains(Anytype x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    /**
     * Return true if currentPos exists and is active.
     *
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    /**
     * Method that performs quadratic probing resolution.
     *
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos(Anytype x) {
        int offset = 1;
        int currentPos = myhash(x);
        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }

    public Anytype find(Anytype x) {
        int currentPos = findPos(x);
        return isActive(currentPos) ? array[currentPos].element : null;
    }

    private int myhash(Anytype x) {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }
        return hashVal;
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     *
     * @param x the item to insert.
     */
    public boolean insert(Anytype x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return false;
        }
        if (array[currentPos] == null) {
            ++occupied;
        }
        array[currentPos] = new HashEntry<Anytype>(x, true);
        currentSize++;
        if (currentSize > array.length / 2) {
            rehash();
        }
        return true;
    }

    /**
     * Expand the hash table.
     */
    private void rehash() {
        HashEntry<Anytype>[] oldArray = array;
        allocateArray(2 * oldArray.length + 1);
        occupied = 0;
        currentSize = 0;
        for (HashEntry<Anytype> arr : oldArray) {
            if (arr != null && arr.isActive) {
                insert(arr.element);
            }
        }
    }

    /**
     * Remove from the hash table.
     *
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove(Anytype x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
            currentSize--;
            return true;
        }
        return false;
    }

    // Simple main
    public static void main(String[] args) {
        QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>();


        long startTime = System.currentTimeMillis();

        final int NUMS = 20000;
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");


        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            H.insert("" + i);
        }
        for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
            if (H.insert("" + i)) {
                System.out.println("OOPS!!! " + i);
            }
        }
        for (int i = 1; i < NUMS; i += 2) {
            H.remove("" + i);
        }

        for (int i = 2; i < NUMS; i += 2) {
            if (!H.contains("" + i)) {
                System.out.println("Find fails " + i);
            }
        }
        for (int i = 1; i < NUMS; i += 2) {
            if (H.contains("" + i)) {
                System.out.println("OOPS!!! " + i);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Elapsed time: " + (endTime - startTime));
    }

}
