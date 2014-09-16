package com.omade.optimize;


public interface OptimizationProblem {

    int dimension();

    double[] objval();

    double[] gradient();

    double linesearch();

    double[] projection();
}
