package com.omade.optimize;

import java.util.List;

import org.netlib.util.doubleW;
import org.openxmlformats.schemas.drawingml.x2006.main.ThemeDocument;

import Jama.Matrix;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Optimize<T> {

    private static String direction = "gradient";
    private static String stepsize = "wolfe";
    private static String stepsize_ap = "wolfe";
    private static int maxiter = 100;
    private static Double xTol = 1e-15;
    private static Double fTol = 1e-5;
    private static Double gTol = 1e-5;

    private static boolean verbose = false;
    private static boolean store_x = false;
    private static boolean store_f = true;
    private static boolean store_g = false;
    private static boolean store_d = false;
    private static boolean store_t = false;

    private static boolean do_projection = false;

    // private static List<Double> x = Lists.newArrayList();
    // private static List<Double> f = Lists.newArrayList();
    // private static List<Double> g = Lists.newArrayList();

    private OptimizationFunction function = null;
    private WorkingSet ws = null;
    // private WorkingSet<Double> ws = new WorkingSet<Double>();

    private int dimension = 0;

    public Optimize() {

    }

    public Optimize(Builder builder) {

        this.function = builder.getOptimizer();
        this.direction = builder.getDirection();
        this.stepsize = builder.getStepsize();
        this.stepsize_ap = builder.getStepsize_ap();
        this.maxiter = builder.getMaxiter();

        this.xTol = builder.getxTol();
        this.fTol = builder.getfTol();
        this.gTol = builder.getgTol();

        this.verbose = builder.isVerbose();
        this.store_x = builder.isStore_x();
        this.store_f = builder.isStore_f();
        this.store_g = builder.isStore_g();
        this.store_d = builder.isStore_d();
        this.store_t = builder.isStore_t();

        this.do_projection = builder.isDo_projection();

        this.dimension = function.dimension();

    }

    public void Optimize(Matrix data) {

        Preconditions.checkArgument(MatrixUtils.isRow(data),
                "the input data should be a row vector");

        ws = new WorkingSet();

        ws.x = data;
        ws.x_prev = MatrixUtils.zeroRow(data.getColumnDimension());

        ws.g = MatrixUtils.zeroRow(data.getColumnDimension());
        ws.g_prev = MatrixUtils.zeroRow(data.getColumnDimension());

        ws.f = function.objval(ws.x);
        ws.f_prev = 0.;

        ws.d = MatrixUtils.zeroRow(data.getColumnDimension());
        ws.t = 0.;
        ws.iter = 0;

        if (this.direction.equals("")) {

        }

        int curIter = 0;

        while (curIter < maxiter) {

            curIter += 1;

        }

        // while ws.iter < maxiter
        // ws.iter += 1
        // ws.x_prev = ws.x
        // ws.f_prev = ws.f
        // # compute gradient
        // ws.g_prev = ws.g
        // ws.g = gradient(p, ws.x_prev)
        // # compute direction
        // calcdir!()
        // # linesearch
        // ws.t = linesearcher(p, ws.x_prev, ws.d)
        // # (optional) projection
        // if do_projection
        // ws.d = projection(p, ws.x_prev + ws.t*ws.d) - ws.x_prev
        // ws.t = linesearcher_ap(p, ws.x_prev, ws.d)
        // end
        // # move
        // ws.x = ws.x_prev + ws.t*ws.d
        // ws.f = objval(p, ws.x)
        // # traces
        // if verbose
        // @printf " %4d: %.6f (%f)\n" ws.iter ws.f ws.f_prev-ws.f
        // end
        // # recording for debugging / visualization
        // if store_x; ws.x_all[:,ws.iter+1] = ws.x; end
        // if store_g; ws.g_all[:,ws.iter] = ws.g; end
        // if store_d; ws.d_all[:,ws.iter] = ws.d; end
        // if store_t; ws.t_all[ws.iter] = ws.t; end
        // if store_f; ws.f_all[ws.iter+1] = ws.f; end
        // # stop condition
        // if abs(ws.f_prev - ws.f) < ftol; break; end
        // if norm(ws.x-ws.x_prev) < xtol; break; end
        // if ws.iter > 1
        // if norm(ws.g) < gtol; break; end
        // end
        // end
        // return ws
        // end
        //
    }

    public static class Builder {

        private OptimizationFunction optimizer = null;
        private static String direction = "gradient";
        private static String stepsize = "wolfe";
        private static String stepsize_ap = "wolfe";
        private static int maxiter = 100;
        private static double xTol = 1e-15;
        private static double fTol = 1e-5;
        private static double gTol = 1e-5;

        private static boolean verbose = false;
        private static boolean store_x = false; // keep records of x
        private static boolean store_f = true; // keep records of function
                                               // values
        private static boolean store_g = false; // keep records of gradients
        private static boolean store_d = false; // keep records of moving
                                                // direction
        private static boolean store_t = false; // keep records of step size
        private static boolean do_projection = false; // projected gradient
                                                      // descent

        // wolfe_opt = {}, # only used in wolfe linesearch
        // backtrack_opt = {}, # only used in backtrack linesearch

        public static String getDirection() {
            return direction;
        }

        public static void setDirection(String direction) {
            Builder.direction = direction;
        }

        public static String getStepsize() {
            return stepsize;
        }

        public static void setStepsize(String stepsize) {
            Builder.stepsize = stepsize;
        }

        public static int getMaxiter() {
            return maxiter;
        }

        public static void setMaxiter(int maxiter) {
            Builder.maxiter = maxiter;
        }

        public static double getxTol() {
            return xTol;
        }

        public static void setxTol(double xTol) {
            Builder.xTol = xTol;
        }

        public static double getfTol() {
            return fTol;
        }

        public static void setfTol(double fTol) {
            Builder.fTol = fTol;
        }

        public static double getgTol() {
            return gTol;
        }

        public static void setgTol(double gTol) {
            Builder.gTol = gTol;
        }

        public OptimizationFunction getOptimizer() {
            return optimizer;
        }

        public void setOptimizer(OptimizationFunction optimizer) {
            this.optimizer = optimizer;
        }

        public Optimize build() {
            return new Optimize(this);
        }

        public static String getStepsize_ap() {
            return stepsize_ap;
        }

        public static void setStepsize_ap(String stepsize_ap) {
            Builder.stepsize_ap = stepsize_ap;
        }

        public static boolean isVerbose() {
            return verbose;
        }

        public static void setVerbose(boolean verbose) {
            Builder.verbose = verbose;
        }

        public static boolean isStore_x() {
            return store_x;
        }

        public static void setStore_x(boolean store_x) {
            Builder.store_x = store_x;
        }

        public static boolean isStore_f() {
            return store_f;
        }

        public static void setStore_f(boolean store_f) {
            Builder.store_f = store_f;
        }

        public static boolean isStore_g() {
            return store_g;
        }

        public static void setStore_g(boolean store_g) {
            Builder.store_g = store_g;
        }

        public static boolean isStore_d() {
            return store_d;
        }

        public static void setStore_d(boolean store_d) {
            Builder.store_d = store_d;
        }

        public static boolean isStore_t() {
            return store_t;
        }

        public static void setStore_t(boolean store_t) {
            Builder.store_t = store_t;
        }

        public static boolean isDo_projection() {
            return do_projection;
        }

        public static void setDo_projection(boolean do_projection) {
            Builder.do_projection = do_projection;
        }

    }

}
