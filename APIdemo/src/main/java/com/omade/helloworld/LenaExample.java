package com.omade.helloworld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveException;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.symlets.Symlet5;

import org.jblas.DoubleMatrix;

import cc.mallet.optimize.GradientAscent;
import cc.mallet.optimize.Optimizer;

import com.google.common.base.Preconditions;
import com.omade.optimize.FunctionUtils;
import com.omade.optimize.OptimizationProblem;

public class LenaExample implements OptimizationProblem {

    private OptimizerLena optimizer;

    private static int width;
    private static int height;
    private static DoubleMatrix img;// one dim
    private static DoubleMatrix img_fwt;// one dim
    // private static double[] img_fwt;

    private BufferedImage imgBuf;
    private static Random rng = new Random();

    private double lambda = 0.1;
    private DoubleMatrix initX;

    private int iterMax = 500;

    public LenaExample() {

        this.setOptimizer(new OptimizerLena());
        init();
    }

    public static void main(String[] args) {

        // JFrame app = new JFrame();
        // BufferedImage bufImage = new BufferedImage(width, height,
        // BufferedImage.TYPE_BYTE_GRAY);

        LenaExample lenaExample = new LenaExample();
        Optimizer optimizer = new GradientAscent(lenaExample.getOptimizer());
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

        DoubleMatrix imgfwt = lenaExample.getOptimizer().getParam();

        DoubleMatrix recoverImage = lenaExample.recoverImage(imgfwt);

        int[] revImage = translateToInteger(recoverImage);

        // bufImage.setRGB(0, 0, width, height, revImage, 0, width);
        // JLabel label1 = new JLabel(new ImageIcon(bufImage));
        // app.add(label1);
    }

    public void init() {
        File imgFile = new File("src/main/resources/lena.bmp");

        try {
            imgBuf = ImageIO.read(imgFile);

            // BufferedImage bufImage = new BufferedImage(width, height,
            // BufferedImage.TYPE_BYTE_GRAY);

            width = imgBuf.getWidth();
            height = imgBuf.getHeight();

            img = new DoubleMatrix(translateToDouble(filter(imgBuf)));

            img_fwt = imgFwt(img);
            initX = project(DoubleMatrix.rand(dimension()));

            getOptimizer().setImg(img);
            getOptimizer().setImgFwt(img_fwt);
            getOptimizer().setParam(initX);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doFilter() {

    }

    public static int[] filter(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        inPixels = getArray1D(src);
        int index = 0;
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;

                tr = clamp(addGNoise(tr, rng));
                tg = clamp(addGNoise(tg, rng));
                tb = clamp(addGNoise(tb, rng));

                outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
            }
        }

        return outPixels;
    }

    public static int addGNoise(int tr, Random random) {
        int v, ran;
        boolean inRange = false;
        do {
            ran = (int) Math.round(random.nextGaussian() * 50);
            v = tr + ran;
            // check whether it is valid single channel value
            inRange = (v >= 0 && v <= 255);
            if (inRange)
                tr = v;
        } while (!inRange);
        return tr;
    }

    public static int clamp(int p) {
        return p > 255 ? 255 : (p < 0 ? 0 : p);
    }

    public static int[][] filter(int[] inPixels, int length) {
        int[][] outPixels = new int[width][height];
        int index = 0;
        for (int row = 0; row < height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;

                tr = clamp(addGNoise(tr, rng));
                tg = clamp(addGNoise(tg, rng));
                tb = clamp(addGNoise(tb, rng));

                outPixels[row][col] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
            }
        }

        return outPixels;
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

    @Override
    public int dimension() {

        Preconditions.checkState(img != null, "input is null");
        return 2 * img.length;

    }

    @Override
    public double linesearch() {

        return 1.;

    }

    @Override
    public DoubleMatrix projection(DoubleMatrix data) {

        Preconditions.checkArgument(data != null, "data is null");

        DoubleMatrix copy = data.copy(data);
        for (int i = 0; i < copy.length; i++) {
            copy.put(i, Math.max(copy.get(i), 0));
        }

        return data.max(data);

    }

    public DoubleMatrix recoverImage(DoubleMatrix waveletCol) {

        Preconditions.checkArgument(waveletCol != null, "waveletCol is null");
        try {
            Transform t = new Transform(new FastWaveletTransform(new Symlet5()));
            return new DoubleMatrix(t.reverse(waveletCol.data));

        } catch (JWaveException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @Description: get the wavelet col matrix.
     */
    public DoubleMatrix imgFwt(DoubleMatrix img) {

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

    public static int[] translateToInteger(DoubleMatrix data) {

        Preconditions.checkArgument(data != null, "data is null");

        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (int) data.get(i);
        }

        return result;
    }

    @Override
    public double objval(DoubleMatrix waveletCol) {

        DoubleMatrix recoverMatrix = recoverImage(waveletCol);
        DoubleMatrix diff = recoverMatrix.sub(img);

        double dot = diff.dot(diff) + lambda * waveletCol.sum();;
        return dot;

    }

    @Override
    public DoubleMatrix gradient(DoubleMatrix var) {

        // x_pos, x_neg = split_x(x)
        // x_comb = x_pos + x_neg
        // 2([x_comb,x_comb] - p.img_fwt) + p.lambda * ones(MLOpt.dimension(p))

        DoubleMatrix absX = absX(var);
        DoubleMatrix xComb = DoubleMatrix.zeros(absX.length, 2);

        xComb.putColumn(0, absX);
        xComb.putColumn(1, absX);

        DoubleMatrix result = xComb.sub(img_fwt).mul(2.0).add(DoubleMatrix.ones(dimension()).mul(lambda));

        return result;

    }

    public OptimizerLena getOptimizer() {

        return optimizer;

    }

    public void setOptimizer(OptimizerLena optimizer) {

        this.optimizer = optimizer;

    }

}
