package com.omade.optimize;

import org.jblas.DoubleMatrix;

public interface OptimizationProblem {

    int dimension();

    double objval(DoubleMatrix x);

    DoubleMatrix gradient(DoubleMatrix x);

    double linesearch();

    DoubleMatrix projection(DoubleMatrix data);
}
