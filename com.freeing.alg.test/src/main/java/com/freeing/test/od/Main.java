package com.freeing.test.od;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){

            int n = in.nextInt();
            int m = in.nextInt();
            int[] nums = new int[n];
            for(int i=0; i<n; i++){
                nums[i] = in.nextInt();
            }

            int sum_value = 0;
            boolean flag = false;
            int[] remainders = new int[m];
            remainders[0] = 1;
            for(int i=0; i<n; i++){
                sum_value += nums[i];
                sum_value %= m;
                if(remainders[sum_value] != 0){
                    flag = true;
                    break;
                }
                remainders[sum_value] ++;
            }

            System.out.println(flag ? 1 : 0);
        }
        return;
    }
}