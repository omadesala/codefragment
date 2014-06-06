package com.chinacloud.bd.flumedemo;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.lexicalscope.contest.ConcurrentTestRunner;

@RunWith(ConcurrentTestRunner.class)
public class CounterTest {

    @Test
    @Ignore
    public void testConcurrent() {

        long endnum = 1000;
        Runnable counter1 = new Counter(endnum);
        Runnable counter2 = new Counter(endnum);
        new Thread(counter1).start();
        new Thread(counter2).start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(endnum * 2, Counter.getCount());
    }

    @Test
    public void test2() {
        new Thread(new Counter(100)).start();
    }

}
