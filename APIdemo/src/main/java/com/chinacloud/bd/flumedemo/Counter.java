package com.chinacloud.bd.flumedemo;

public class Counter implements Runnable {

    private static long count = 0;
    private static long endnum;

    Counter(long init) {
        endnum = init;
    }

    public void run() {

        for (int i = 0; i < endnum; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static long getCount() {
        return count;
    }

}
