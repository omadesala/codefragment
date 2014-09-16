package com.omade.optimize;

public class Optimize implements OptimizationProblem {

    @Override
    public int dimension() {
        return 0;
    }

    @Override
    public double[] objval() {
        return null;
    }

    @Override
    public double[] gradient() {
        return null;
    }

    @Override
    public double linesearch() {
        return 0;
    }

    @Override
    public double[] projection() {
        return null;
    }

}
