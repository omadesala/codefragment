package com.omade.optimize;

import static org.junit.Assert.*;

import java.util.Random;

import junit.framework.Assert;

import org.jblas.DoubleMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FunctionUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSplit() {

        Random rng = new Random();

        for (int i = 0; i < 5; i++) {

            double x = rng.nextDouble() - 0.5;
            System.out.println("x=" + x);
            double funcPos = FunctionUtils.funcPos(x);
            double funcNeg = FunctionUtils.funcNeg(x);

            System.out.println("x(+)=" + funcPos + " x(-)=" + funcNeg);
            Assert.assertEquals(x, funcPos - funcNeg, 1e-5);

        }

    }

    @Test
    public void testVectorSplit() {

        DoubleMatrix data = DoubleMatrix.rand(4).sub(0.5).transpose();
        data.print();

        if (data.isColumnVector()) {
            System.out.println("data is column vector");
        }
        DoubleMatrix funcSplit = FunctionUtils.funcSplit(data);
        System.out.println("after split: ");
        funcSplit.print();

        DoubleMatrix row1 = funcSplit.getRow(0);
        DoubleMatrix row2 = funcSplit.getRow(1);

        DoubleMatrix sub = row1.sub(row2);
        DoubleMatrix abs = row1.add(row2);

        sub.print();
        abs.print();

    }

}
