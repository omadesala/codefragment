package com.omade.optimize;

import Jama.Matrix;

public interface OptimizationFunction {

    int dimension();

    double objval(Matrix x);

    double[] gradient(Matrix x);

    double linesearch();

    double[] projection();
}
