package chapter3;

import java.util.Iterator;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2017/12/3
 * Time:15:06
 */

public class test{
    public static void main(String[] args){
        int[] nums = {3,2,4};
        int target = 6;
        int[] a = twoSum(nums,target);
        System.out.println(twoSum(nums,target));
        int count = 0;
        for (int i = 0; i < 100; i++) {
            count = count++;

        }
        System.out.println(count);
        //count=0;因为java的自增是先把变量拷贝到临时容器中，自增完后再把临时容器中的值返回代码如下
       /*
       *     private int autoadd(int count){
        int temp = count;
        count  = count+1;
        return temp;
        }
       * */
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] arr = new int[2];
        for(int i = 0;i < nums.length;i++){
            for(int j = i+1;j < nums.length;j++){
                if(( nums[i]+nums[j] ) == target){
                    arr[0] = i;
                    arr[1] = j;
                    break;
                }
            }
        }
        return arr;
    }
}