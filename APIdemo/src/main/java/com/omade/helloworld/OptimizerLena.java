package com.omade.helloworld;

import java.awt.image.BufferedImage;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveException;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.symlets.Symlet5;

import org.jblas.DoubleMatrix;

import cc.mallet.optimize.Optimizable;

import com.google.common.base.Preconditions;
import com.omade.optimize.FunctionUtils;

public class OptimizerLena implements Optimizable.ByGradientValue {

    // Optimizables encapsulate all state variables,
    // so a single Optimizer object can be used to optimize
    // several functions.

    private static DoubleMatrix img;// one dim
    private static DoubleMatrix imgFwt;// one dim

    // private double[] parameters;
    private DoubleMatrix param;
    private double lambda = 0.1;

    public OptimizerLena() {

    }

    public double getValue() {

        // double x = parameters[0];
        // double y = parameters[1];

        // return -3 * x * x - 4 * y * y + 2 * x - 4 * y + 18;

        double[] recoverImage = recoverImage(imgFwt);
        DoubleMatrix recoverMatrix = new DoubleMatrix(recoverImage);
        DoubleMatrix diff = recoverMatrix.sub(img);

        double dot = diff.dot(diff) + lambda * imgFwt.sum();;
        return dot;

    }

    public void getValueGradient(double[] gradient) {

        // gradient[0] = -6 * parameters[0] + 2;
        // gradient[1] = -8 * parameters[1] - 4;

        DoubleMatrix absX = absX(this.param);
        DoubleMatrix xComb = DoubleMatrix.zeros(absX.length, 2);

        xComb.putColumn(0, absX);
        xComb.putColumn(1, absX);

        DoubleMatrix result = xComb.sub(imgFwt).mul(2.0).add(DoubleMatrix.ones(dimension()).mul(lambda));

        for (int i = 0; i < gradient.length; i++) {
            gradient[i] = result.get(i);
        }

    }

    // The following get/set methods satisfy the Optimizable interface

    public int getNumParameters() {
        Preconditions.checkState(this.param != null, "parameter is null");
        return param.length;
    }

    public double getParameter(int i) {
        return param.get(i);
    }

    public void getParameters(double[] buffer) {

        Preconditions.checkArgument(buffer != null, "input buffer is null");
        Preconditions.checkState(this.param != null, "parameter is null");

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = param.get(i);
        }
    }

    public void setParameter(int i, double r) {
        param.put(i, r);
    }

    public void setParameters(double[] newParameters) {

        Preconditions.checkArgument(newParameters != null, "input buffer is null");
        Preconditions.checkState(this.param != null, "parameter is null");

        for (int i = 0; i < newParameters.length; i++) {
            param.put(i, newParameters[i]);
        }
    }

    public static DoubleMatrix getImg() {

        return img;

    }

    public static void setImg(DoubleMatrix img) {

        OptimizerLena.img = img;

    }

    public static DoubleMatrix getImgFwt() {

        return imgFwt;

    }

    public static void setImgFwt(DoubleMatrix imgFwt) {

        OptimizerLena.imgFwt = imgFwt;

    }

    public DoubleMatrix getParam() {

        return param;

    }

    public void setParam(DoubleMatrix param) {

        this.param = param;

    }

    /**
     * @Description: map the data into large than zero.
     */
    public DoubleMatrix project(DoubleMatrix data) {

        Preconditions.checkArgument(data != null, "data is null");

        DoubleMatrix copy = data.copy(data);
        for (int i = 0; i < copy.length; i++) {
            copy.put(i, Math.max(copy.get(i), 0));
        }

        return data.max(data);
    }

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

    private double[] recoverImage(DoubleMatrix waveletCol) {

        Preconditions.checkArgument(waveletCol != null, "waveletCol is null");
        try {
            Transform t = new Transform(new FastWaveletTransform(new Symlet5()));
            double[] reverse = t.reverse(waveletCol.data);
            return reverse;
        } catch (JWaveException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @Description: get the wavelet col matrix.
     */
    private DoubleMatrix imgFwt(DoubleMatrix img) {

        Preconditions.checkArgument(img != null, "img is null");

        try {
            Transform t = new Transform(new FastWaveletTransform(new Symlet5()));
            double[] forward = t.forward(img.data);
            return new DoubleMatrix(forward);
        } catch (JWaveException e) {
            e.printStackTrace();
        }

        return null;
    }

    public DoubleMatrix realX(DoubleMatrix input) {

        Preconditions.checkArgument(input != null, "input is null");

        DoubleMatrix funcSplit = funcSplit(input);

        DoubleMatrix row1 = funcSplit.getRow(0);
        DoubleMatrix row2 = funcSplit.getRow(1);

        return row1.sub(row2);
    }

    public DoubleMatrix absX(DoubleMatrix input) {

        Preconditions.checkArgument(input != null, "input is null");

        DoubleMatrix funcSplit = funcSplit(input);

        DoubleMatrix row1 = funcSplit.getRow(0);
        DoubleMatrix row2 = funcSplit.getRow(1);

        return row1.add(row2);
    }

    public static int[] getArray1D(BufferedImage bi) {

        int width = bi.getWidth();
        int height = bi.getHeight();
        int[] imgData = new int[width * height];
        bi.getRGB(0, 0, width, height, imgData, 0, width);
        return imgData;
    }

    public static double[] translateToDouble(int[] data) {

        Preconditions.checkArgument(data != null, "data is null");

        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }

        return result;
    }

    public static int[] translateToInteger(double[] data) {

        Preconditions.checkArgument(data != null, "data is null");

        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (int) data[i];
        }

        return result;
    }

    public double objval(DoubleMatrix waveletCol) {

        double[] recoverImage = recoverImage(waveletCol);
        DoubleMatrix recoverMatrix = new DoubleMatrix(recoverImage);
        DoubleMatrix diff = recoverMatrix.sub(img);

        double dot = diff.dot(diff) + lambda * waveletCol.sum();;
        return dot;

    }

    public DoubleMatrix gradient(DoubleMatrix var) {

        // x_pos, x_neg = split_x(x)
        // x_comb = x_pos + x_neg
        // 2([x_comb,x_comb] - p.img_fwt) + p.lambda * ones(MLOpt.dimension(p))

        DoubleMatrix absX = absX(var);
        DoubleMatrix xComb = DoubleMatrix.zeros(absX.length, 2);

        xComb.putColumn(0, absX);
        xComb.putColumn(1, absX);

        DoubleMatrix result = xComb.sub(imgFwt).mul(2.0).add(DoubleMatrix.ones(dimension()).mul(lambda));

        return result;

    }

    public int dimension() {

        Preconditions.checkState(img != null, "input is null");
        return 2 * img.length;

    }

}
