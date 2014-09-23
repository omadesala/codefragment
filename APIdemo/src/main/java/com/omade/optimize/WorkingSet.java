package com.omade.optimize;

import Jama.Matrix;

public class WorkingSet {

    // the matrix used for vector and vector array.
    public Matrix x; // vector
    public Matrix x_prev; // vector

    public Matrix g; // vector
    public Matrix g_prev;// vector

    public double f;
    public double f_prev;

    public Matrix d; // vector
    public double t;

    public int iter;

    public Matrix f_all;// vector
    public Matrix x_all;// vector[] array
    public Matrix g_all;// vector[] array
    public Matrix d_all;// vector[] array
    public Matrix t_all;// vector[] array
}
