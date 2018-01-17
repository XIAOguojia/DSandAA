package chapter3;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/4
 * Time:20:02
 * Josephus Problem
 */
public class Josephus {
    public static void main(String[] args){
        int m = 1;
        int n = 5;
        //System.out.println(pass(m,n));
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
    }

    private static int pass(int m, int n) {
        int count = 1;
        int m_count = 1;
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i+1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (m_count == n){
                for (int j = 0; j < arr.length; j++) {
                    if (arr[j]!=0){
                        return arr[j];
                    }
                }
            }

            if (arr[i]==0){
                continue;
            }

            if (count%(m+1) == 0){
                System.out.println(arr[i]);
                arr[i] = 0;
                m_count++;
            }


            count++;

            if (i == arr.length-1){
                i = -1;
            }
        }
        return -1;
    }

//    public static void mypass(int m,int n){
//        int i,j,mPrime,numLeft;
//        ArrayList<Integer> L = new ArrayList<>();
//        for (i = 1; i <= n; i++) {
//            L.add(i);
//        }
//        Iterator<Integer> iter = L.listIterator();
//        Integer item = 0;
//
//        numLeft = n;
//        mPrime = m % n;
//
//        for (i = 0;i < n; i++){
//            mPrime = m%numLeft;
//            if (mPrime <= numLeft/2){
//                if(iter.hasNext()){
//                    item = iter.next();
//                }
//                for (j = 0; j < mPrime; j++){
//                    if (!iter.hasNext()){
//                        iter = L.listIterator();
//                        item = iter.next();
//                    }
//                }
//            }else {
//                for (j = 0;j < numLeft - mPrime;j++ ){
//                    if(!iter.hasPrevious()){
//                        iter = L.listIterator(L.size());
//                    }
//                    item = iter.previous();
//                }
//            }
//
//            System.out.print("Removed"+item+" ");
//            iter.remove();
//            if (!iter.hasNext()){
//                iter = L.listIterator();
//            }
//            System.out.println();
//            for (Integer x:L) {
//                System.out.print(x+" ");
//            }
//            System.out.println();
//            numLeft--;
//        }
//    }
}
