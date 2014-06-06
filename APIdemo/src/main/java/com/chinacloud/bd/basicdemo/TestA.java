package com.chinacloud.bd.basicdemo;

public class TestA {

    private static final int[] COINS = new int[] { 1, 5, 10, 20, 50 };
    private static final int SUM = 10;
    private static int i = 1;

    public static void main(String[] args) {
        calc(0, 0, "");
        System.out.println("totally " + (i - 1) + " solutions.");
    }

    public void test() {

    }

    private static void calc(int sum, int cionIdex, String pre) {

        System.out.println("sum: " + sum + "  cionIdex: " + cionIdex + "  pre: " + pre);

        if (SUM == sum) {
            System.out.println("case " + (i++) + ": " + pre);
        }
        for (int i = cionIdex; i < COINS.length; i++) {
            if (SUM - sum >= 0) {
                calc(sum + COINS[i], i, pre + " " + COINS[i]);
            }
        }
    }
    
    
}
