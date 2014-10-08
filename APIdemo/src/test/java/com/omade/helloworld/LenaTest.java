package com.omade.helloworld;

import org.jblas.DoubleMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LenaTest {

    // private Lena lena;

    @Before
    public void setUp() throws Exception {
        // lena = new Lena();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProject() {
        DoubleMatrix test = DoubleMatrix.rand(2);
        test.print();

        double dot = test.dot(test);
        System.out.println("dot: " + dot);

    }

    @Test
    public void testMatrixSum() {
        DoubleMatrix test = DoubleMatrix.rand(2);
        test.print();

        double sum = test.sum();
        System.out.println("sum: " + sum);

    }

    @Test
    public void testgradient() {
        DoubleMatrix test = DoubleMatrix.rand(2).sub(0.5);
        test.print();

        if (test.isColumnVector()) {
            System.out.println("test is column vector ");
        } else {
            System.out.println("test is row vector ");
        }

        DoubleMatrix result = new DoubleMatrix(2, 2);

        result.putColumn(0, test);
        result.putColumn(1, test);

        result.print();
    }

}
