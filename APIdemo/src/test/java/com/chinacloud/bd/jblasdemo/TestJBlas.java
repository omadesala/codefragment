package com.chinacloud.bd.jblasdemo;

import java.lang.invoke.VolatileCallSite;

import org.jblas.DoubleMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJBlas {

    volatile int j = 0;

    @Before
    public void setUp() throws Exception {

        System.out.println("setUp");

        j = j++;
        System.out.println("j=" + j);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Before
    public void init() {
        System.out.println("init");

    }

    @Test
    public void test() {
        System.out.println("j2=" + j);

        System.out.println("test in test");
        // DoubleMatrix m1 = new DoubleMatrix();
        DoubleMatrix mr = DoubleMatrix.rand(2, 2);
        DoubleMatrix mn = DoubleMatrix.randn(2, 2);
        mn.print();
        mr.print();
        System.out.println("mn+mr:");

        DoubleMatrix mresult = mn.addi(mr);
        DoubleMatrix mresult2 = mn.add(mr);
        mresult.print();
        mresult2.print();
    }

}
