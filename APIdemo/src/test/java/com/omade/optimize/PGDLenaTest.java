package com.omade.optimize;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netlib.util.doubleW;

import com.google.gson.internal.Pair;

public class PGDLenaTest {

    private PGDLena lena = new PGDLena();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAbsfuncX() {

        double absfuncX = lena.absfuncX(100);

        System.out.println("absfuncX :  " + absfuncX);

        absfuncX = lena.absfuncX(-100);

        System.out.println("absfuncX :  " + absfuncX);
    }

    @Test
    public void testsplitX() {

        Double[] test = new Double[3];
        test[0] = 100.;
        test[1] = -100.;
        test[2] = -23.3;

        List<Pair<Double, Double>> ret = lena.splitX(test, test.length);

        for (int i = 0; i < ret.size(); i++) {
            Pair<Double, Double> pointPair = ret.get(i);
            System.out.println("posX :  " + pointPair.first + " negX "
                    + pointPair.second);
            // Double absDouble = pointPair.first + pointPair.second;
            // System.out.println("point" + absDouble);
        }

    }
}
