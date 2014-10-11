package com.omade.helloworld;

import org.jblas.DoubleMatrix;

import cc.mallet.optimize.GradientAscent;
import cc.mallet.optimize.Optimizer;

public class OptimizerDemo {

    public static void main(String[] args) {

        OptimizerSample optimizable = new OptimizerSample();
        // OptimizerLena optimizable = new OptimizerLena();

        optimizable.setParam(DoubleMatrix.rand(2));
        // Optimizer optimizer = new LimitedMemoryBFGS(optimizable);
        Optimizer optimizer = new GradientAscent(optimizable);

        boolean converged = false;

        try {
            converged = optimizer.optimize();
        } catch (IllegalArgumentException e) {
            // This exception may be thrown if L-BFGS
            // cannot step in the current direction.
            // This condition does not necessarily mean that
            // the optimizer has failed, but it doesn't want
            // to claim to have succeeded...
        }

        System.out.println(optimizable.getParameter(0) + ", " + optimizable.getParameter(1));
    }
}
