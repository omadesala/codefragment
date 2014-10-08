package com.omade.optimize;

import org.jblas.DoubleMatrix;
import org.jblas.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LenaTest {

    private Random rnd = null;

    @Before
    public void setUp() throws Exception {

        rnd = new Random();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {

        DoubleMatrix rand = DoubleMatrix.rand(4);
        rand.print();

        DoubleMatrix sub = rand.sub(0.5);

        sub.print();

    }

}
