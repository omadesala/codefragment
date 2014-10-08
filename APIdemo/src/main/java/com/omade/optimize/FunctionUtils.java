package com.omade.optimize;

import org.jblas.DoubleMatrix;

import com.google.common.base.Preconditions;

public class FunctionUtils {

    /**
     * 
     * @Description: the split result is storage in each row
     * @param @param data the input should be a row vector.
     * 
     * @return DoubleMatrix ,the result will contain two row . 0 is postive
     *         part,1 row is negative part.
     * @throws
     */
    public static DoubleMatrix funcSplit(DoubleMatrix data) {

        Preconditions.checkArgument(data != null, "input is null");
        Preconditions.checkArgument(data.isRowVector(), "not a row vector");

        DoubleMatrix result = new DoubleMatrix(2, data.length);

        for (int i = 0; i < data.length; i++) {

            result.put(0, i, FunctionUtils.funcPos(data.get(i)));
            result.put(1, i, FunctionUtils.funcNeg(data.get(i)));
        }

        return result;

    }

    public static double funcPos(double data) {

        return Math.max(data, 0.);

    }

    public static double funcNeg(double data) {
        return Math.max(-data, 0.);
    }

}
