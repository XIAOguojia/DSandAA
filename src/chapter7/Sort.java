package chapter7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/16
 * Time:16:22
 *
 * @author Raven
 */
public class Sort {
    /**
     * Simple insertion sort
     *
     * @param a an array of Comparable items
     */
    public static <Anytype extends Comparable<? super Anytype>> void insertionSort(Anytype[] a) {
        int j;
        for (int p = 1; p < a.length; p++) {
            Anytype tmp = a[p];
            for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
    }

    /**
     * Shellsort,using Shell's(poor) increments.
     *
     * @param a an array of comparable items.
     */
    public static <Anytype extends Comparable<? super Anytype>> void shellsort(Anytype[] a) {
        int j;
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                Anytype tmp = a[i];
                for (j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap) {
                    a[j] = a[j - gap];
                }
                a[j] = tmp;
            }
//            show((Integer[]) a);
        }
    }

    /**
     * Internal method for heapsort.
     *
     * @param i the index of an item in the heap.
     * @return the index of the left child.
     */
    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * Internal method for heapsort that is used in deleteMax and buildHeap.
     *
     * @param a an array of Comparable items.
     * @index i the position from which to percolate down.
     * @int n the logical size of the binary heap.
     */
    private static <Anytype extends Comparable<? super Anytype>> void percDown(Anytype[] a, int i, int n) {
        int child;
        Anytype tmp;
        for (tmp = a[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0) {
                child++;
            }
            if (tmp.compareTo(a[child]) < 0) {
                a[i] = a[child];
            } else {
                break;
            }
        }
        a[i] = tmp;
//        show(a);
    }

    /**
     * Standard heapsort.
     *
     * @param a an array of Comparable items.
     */
    public static <Anytype extends Comparable<? super Anytype>> void heapsort(Anytype[] a) {
        //Build heap
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            percDown(a, i, a.length);
        }

        //deleteMax
        for (int i = a.length - 1; i > 0; i--) {
            swapReference(a, 0, i);
            percDown(a, 0, i);
        }
    }

    /**
     * Method to swap to elements in an array.
     *
     * @param a      an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static <Anytype> void swapReference(Anytype[] a, int index1, int index2) {
        Anytype tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    /**
     * Mergesort algorithm.
     *
     * @param a an array of Comparable items.
     */
    public static <Anytype extends Comparable<? super Anytype>> void mergeSort(Anytype[] a) {
        Anytype[] tmpArray = (Anytype[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length - 1);
    }

    /**
     * Internal method that makes recursive calls.
     *
     * @param a        an array of Comparable items.
     * @param tmpArray an array to place the merged result.
     * @param left     the left-most index of the subarray.
     * @param right    the right-most index of the subarray.
     */
    private static <Anytype extends Comparable<? super Anytype>> void mergeSort(Anytype[] a, Anytype[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmpArray, left, center);
            mergeSort(a, tmpArray, center + 1, right);
            merge(a, tmpArray, left, center + 1, right);
        }
    }

    /**
     * Internal method that merges two sorted halves of a subarray.
     *
     * @param a        an array of Comparable items.
     * @param tmpArray an array to place the merged result.
     * @param leftPos  the left-most index of the subarray.
     * @param rightPos the index of the start of the second half.
     * @param rightEnd the right-most index of the subarray.
     */
    private static <Anytype extends Comparable<? super Anytype>> void merge(Anytype[] a, Anytype[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        // Main loop
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                tmpArray[tmpPos++] = a[leftPos++];
            } else {
                tmpArray[tmpPos++] = a[rightPos++];
            }
        }
        // Copy rest of first half
        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = a[leftPos++];
        }
        // Copy rest of right half
        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = a[rightPos++];
        }
        // Copy tmpArray back
        for (int i = 0; i < numElements; rightEnd--, i++) {
            a[rightEnd] = tmpArray[rightEnd];
        }
    }

    /**
     * Simple recursion quicksort
     */
    public static void sort(List<Integer> items) {
        if (items.size() > 1) {
            List<Integer> smaller = new ArrayList<>();
            List<Integer> same = new ArrayList<>();
            List<Integer> larger = new ArrayList<>();
            Integer chosenItem = items.get(items.size() / 2);

            for (Integer i : items) {
                if (i < chosenItem) {
                    smaller.add(i);
                } else if (i > chosenItem) {
                    larger.add(i);
                } else {
                    same.add(i);
                }
            }

            sort(smaller);
            sort(larger);

            items.clear();
            items.addAll(smaller);
            items.addAll(same);
            items.addAll(larger);
        }
    }

    /**
     * Quicksort algorithm.
     *
     * @param a an array of Comparable items.
     */
    public static <Anytype extends Comparable<? super Anytype>> void quicksort(Anytype[] a) {
        quicksort(a, 0, a.length - 1);
    }

    private static final int CUTOFF = 3;

    /**
     * Return median of left, center, and right.
     * Order these and hide the pivot.
     * 三数中值分割法
     */
    private static <Anytype extends Comparable<? super Anytype>> Anytype median3(Anytype[] a, int left, int right) {
        int center = (left + right) / 2;
        if (a[center].compareTo(a[left]) < 0) {
            swapReference(a, left, center);
        }
        if (a[right].compareTo(a[left]) < 0) {
            swapReference(a, left, right);
        }
        if (a[right].compareTo(a[center]) < 0) {
            swapReference(a, center, right);
        }
        //Place pivot at position right -1
        swapReference(a, center, right - 1);
        return a[right - 1];
    }

    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     *
     * @param a     an array of Comparable items.
     * @param left  the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <Anytype extends Comparable<? super Anytype>> void quicksort(Anytype[] a, int left, int right) {
        if (left + CUTOFF <= right) {
            Anytype pivot = median3(a, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i].compareTo(pivot) < 0) {
                }
                while (a[--j].compareTo(pivot) > 0) {
                }
                if (i < j) {
                    swapReference(a, i, j);
                } else {
                    break;
                }
            }
            //Restore pivot
            swapReference(a, i, right - 1);
            //Sort small elements
            quicksort(a, left, i - 1);
            //Sort large elements
            quicksort(a, i + 1, right);
        } else {
            //Do an insertion sort on the subarray
            insertionSort(a, left, right);
        }

    }

    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     *
     * @param a     an array of Comparable items.
     * @param left  the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    private static <Anytype extends Comparable<? super Anytype>> void insertionSort(Anytype[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Anytype tmp = a[i];
            int j;
            for (j = i; j > 0 && a[j].compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
    }

    /**
     * Quick selection algorithm.
     * Places the kth smallest item in a[k-1].
     *
     * @param a an array of Comparable items.
     * @param k the desired rank (1 is minimum) in the entire array.
     */
    public static <Anytype extends Comparable<? super Anytype>> Anytype quickSelect(Anytype[] a, int k) {
        quickSelect(a, 0, a.length - 1, k);
        return a[k - 1];
    }

    /**
     * Internal selection method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * Places the kth smallest item in a[k-1].
     *
     * @param a     an array of Comparable items.
     * @param left  the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     * @param k     the desired index (1 is minimum) in the entire array.
     */
    private static <Anytype extends Comparable<? super Anytype>> void quickSelect(Anytype[] a, int left, int right, int k) {
        if (left + CUTOFF <= right) {
            Anytype pivot = median3(a, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i].compareTo(pivot) < 0) {
                }
                while (a[--j].compareTo(pivot) > 0) {
                }
                if (i < j) {
                    swapReference(a, i, j);
                } else {
                    break;
                }

            }
            swapReference(a, i, right - 1);
            if (k <= i) {
                quickSelect(a, left, i - 1, k);
            } else {
                quickSelect(a, i + 1, right, k);
            }
        } else {
            insertionSort(a, left, right);
        }
    }

    /**
     * Test the sort algorithm.
     */
    public static void main(String[] args) {
//        Sort.test1();
//        Sort.test2();
//        Sort.test3();
//        Sort.test4();
//        Sort.test5();
        List<String> lst = new ArrayList<>();
        final int LEN = 3;
        Random r = new Random();

        for (int i = 0; i < 10000; i++) {
            String str = "";
            int len = LEN;
            for (int j = 0; j < len; j++) {
                str += (char) ('a' + r.nextInt(26));
            }
            lst.add(str);
        }

        String[] arr1 = new String[lst.size()];
        String[] arr2 = new String[lst.size()];
        lst.toArray(arr1);
        lst.toArray(arr2);

//        System.out.println("排序前：");
//        for (int i = 0; i < arr1.length; i++) {
//            System.out.print(arr1[i]+"  ");
//        }
//        System.out.println();
//        RadixSort.radixSortA(arr1,LEN);
//        RadixSort.countingRadixSort(arr1,LEN);
//        System.out.println("排序后：");
//        for (int i = 0; i < arr1.length; i++) {
//            System.out.print(arr1[i]+"  ");
//        }

        long start, end;
        start = System.currentTimeMillis();
        Arrays.sort(arr1);
        end = System.currentTimeMillis();
        System.out.println("Arrays sort time:" + (end - start));

        start = System.currentTimeMillis();
        RadixSort.radixSortA(arr1,LEN);
        end = System.currentTimeMillis();
        System.out.println("Radix sort time:" + (end - start));

        start = System.currentTimeMillis();
        RadixSort.countingRadixSort(arr1,LEN);
        end = System.currentTimeMillis();
        System.out.println("Count sort time:" + (end - start));

        start = System.currentTimeMillis();
        Sort.mergeSort(arr1);
        end = System.currentTimeMillis();
        System.out.println("Merge sort time:" + (end - start));


//        for (int i = 0; i < arr1.length; i++) {
//            if (!arr1[i].equals(arr2[i])) {
//                System.out.println("bugs:" + i);
//            }
//        }
    }


    private static void test5() {
        Integer[] a = {8, 1, 4, 9, 0, 3, 5, 2, 7, 6};
        show(a);
        //   Sort.quicksort(a);
        System.out.println(Sort.quickSelect(a, 3));
        show(a);
    }

    private static void test4() {
        Integer[] a = {24, 13, 26, 1, 2, 27, 38, 15};
        show(a);
        Sort.mergeSort(a);
        show(a);
    }

    private static void test3() {
        Integer[] a = new Integer[7];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        Sort.show(a);
        Sort.heapsort(a);
        Sort.show(a);
    }

    private static void test2() {
        Integer[] a = {81, 94, 11, 96, 12, 35, 17, 95, 28, 58, 41, 75, 15};
        Sort.show(a);
        Sort.shellsort(a);
        Sort.show(a);
    }

    private static void test1() {
        Integer[] a = {34, 8, 64, 51, 32, 21};
        Sort.show(a);
        Sort.insertionSort(a);
        Sort.show(a);
    }

    private static <Anytype> void show(Anytype[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "  ");
        }
        System.out.println();
    }
}
