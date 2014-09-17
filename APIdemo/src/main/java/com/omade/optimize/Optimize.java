package com.omade.optimize;

import java.util.List;

import com.google.common.collect.Lists;

public class Optimize implements OptimizationProblem {

    private static String direction = "gradient";
    private static String stepsize = "wolfe";
    private static int maxiter = 100;
    private static double xTol = 1e-15;
    private static double fTol = 1e-5;
    private static double gTol = 1e-5;

    private static List<Double> x = Lists.newArrayList();
    private static List<Double> f = Lists.newArrayList();
    private static List<Double> g = Lists.newArrayList();

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

    public void Optimize() {

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

}
