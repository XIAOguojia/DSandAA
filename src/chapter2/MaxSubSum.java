package chapter2;

import java.util.logging.Level;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/11/29
 * Time:16:38
 * Function:求解最大子序列和问题的四种算法
 */
public class MaxSubSum {
    public static void main(String[] args){
//        int n = 100;
//        System.out.println("partialSum ="+sum(n));
        int[] a = {-2,11,-4,13,-5,-2};
//        System.out.println("MaxSubSum1 ="+MaxSubSum1(a));
//        System.out.println("MaxSubSum2 ="+MaxSubSum2(a));
//        int[] b = {4,-3,5,-2,-1,2,6,-2};
//        System.out.println("MaxSubSum3 ="+MaxSubSum3(b,0,b.length-1));
        System.out.println("MaxSubSum4 ="+MaxSubSum4(a));
    }
    /**
     * 把负数的子序列舍弃，运行时间O(N)
     * */
    private static int MaxSubSum4(int[] a) {
        int MaxSum = 0,thisSum = 0;
        for (int i = 0; i < a.length; i++) {
            thisSum += a[i];
            if (thisSum > MaxSum){
                MaxSum = thisSum;
            }else if(thisSum < 0){
                thisSum = 0;
            }
        }
        return MaxSum;
    }

    /**
     *基于分治法的求最大子序列和，运行时间为O(NlogN)
     * @return
     * @param  b, left, right;
     * */
    private static int MaxSubSum3(int[] b,int left,int right) {
            if(left == right){
                if (b[left] > 0){
                    return b[left];
                } else{
                    return 0;
                }
            }

            int center = (left+right)/2;
            int MaxLeftSum = MaxSubSum3(b,left,center);
            int MaxRightSum = MaxSubSum3(b,center+1,right);

            int MaxLeftborderSum = 0,LeftborderSum = 0;
            int MaxRightborderSum = 0,RightborderSum = 0;

        for (int i = center; i >= left; i--) {
            LeftborderSum += b[i];
            if(LeftborderSum > MaxLeftborderSum){
                MaxLeftborderSum = LeftborderSum;
            }
        }
        for (int i = center+1; i < right; i++) {
            RightborderSum += b[i];
            if (MaxRightborderSum < RightborderSum){
                MaxRightborderSum = RightborderSum;
            }
        }
            return Max3(MaxLeftSum,MaxRightSum,MaxLeftborderSum+MaxRightborderSum);
    }

    private static int Max3(int maxLeftSum, int maxRightSum, int i) {
        int max = 0;
        if (maxLeftSum >= maxRightSum){
            max = maxLeftSum;
            if (max > i){
                return max;
            }else {
                return i;
            }
        }else {
            max = maxRightSum;
            if (max > i){
                return max;
            }else {
                return i;
            }
        }
    }

    /***
     * 基于穷举法的改进算法，运行时间为O(N^2)
     */
    private static int MaxSubSum2(int[] a) {
        int MaxSum = 0;
        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];
                if (MaxSum < thisSum){
                    MaxSum = thisSum;
                }
            }

        }
        return MaxSum;
    }

    /**
     * 穷举法求解最大子序列和问题，运行时间为O(N^3)
     * */
    private static int MaxSubSum1(int[] a) {
        int MaxSum = 0;
        for (int i = 0; i <a.length ; i++) {
            for (int j = i; j < a.length ; j++) {
                int thisSum = 0;
                for (int k = i; k <= j ; k++) {
                    thisSum += a[k];
                }
                if(MaxSum < thisSum){
                    MaxSum = thisSum;
                }
            }
        }
        return MaxSum;
    }
    //求立方和
    private static int sum(int n) {
        int partialSum = 0;
        for (int i = 1; i <= n ; i++) {
            partialSum += i*i*i;
        }
        return partialSum;
    }


}
