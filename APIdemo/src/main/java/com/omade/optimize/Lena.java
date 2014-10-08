package com.omade.optimize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.symlets.Symlet5;
import Jama.Matrix;

import com.google.gson.internal.Pair;

public class Lena implements OptimizationFunction {

    private static int width;
    private static int height;
    private static double[] img_fwt;
    private static double[][] img_fwt_2d;
    private static Random rng = new Random();

    // ////////////

    public static void main(String[] args) {

        File imgFile = new File("src/main/resources/lena.bmp");
        JFrame app = new JFrame();
        app.setSize(800, 600);

        try {
            BufferedImage read = ImageIO.read(imgFile);

            width = read.getWidth();
            height = read.getHeight();
            // int[] array = getArray1D(read);
            // int[] arrayNoise = null;

            int[][] arrayNoise2D = null;

            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

            try {

                // arrayNoise = filter(read);
                arrayNoise2D = filter(getArray1D(read), width * height);

                // arrayNoise2D = getArray2D(read);
                Transform t = new Transform(new FastWaveletTransform(new Symlet5()));

                // for (int i = 0; i < imgarr.length; i++) {
                // imgarr[i] = (double) arrayNoise[i];
                // }

                // img_fwt = t.forward(imgarr);
                img_fwt_2d = t.forward(array2dToDouble(arrayNoise2D, width, height));

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (Math.abs(img_fwt_2d[i][j]) < 1e3) {
                            img_fwt_2d[i][j] = 0.;
                        }
                    }
                }

                int[][] col = IntArray2D(img_fwt_2d, width, height);

                double[][] reverse = t.reverse(img_fwt_2d);

                // double[] reverse = t.reverse(img_fwt);

                // int[] recv = new int[reverse.length];
                int[][] recv = new int[width][height];

                // int[] diff = new int[reverse.length];
                int[][] diff = new int[width][height];

                int[][] ori = getArray2D(read);

                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {

                        diff[i][j] = (int) reverse[i][j] - ori[i][j];
                        recv[i][j] = (int) reverse[i][j];
                    }
                }

                System.out.println("this is the recved image ");

                // bufImage.setRGB(0, 0, width, height, diff, 0, width);
                bufImage.setRGB(0, 0, width, height, array2dflatInt(recv, width, height), 0, width);
                JLabel label1 = new JLabel(new ImageIcon(bufImage));
                app.add(label1);
                // JLabel label2 = new JLabel(new ImageIcon(read));
                // app.add(label2);

            } catch (JWaveFailure e) {
                e.printStackTrace();
            } catch (JWaveException e) {
                e.printStackTrace();
            }
            app.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] array2dflatInt(int[][] array2d, int width, int height) {

        int[] array = new int[width * height];

        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array[index++] = array2d[i][j];
            }
        }
        return array;
    }

    public static double[] array2dflatDouble(int[][] array2d, int width, int height) {

        double[] array = new double[width * height];

        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array[index++] = array2d[i][j];
            }
        }
        return array;
    }

    public static int[][] array1dTo2dInt(int[] array1D, int width, int height) {

        int[][] array = new int[width][height];
        int index = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array[i][j] = array1D[index++];
            }
        }
        return array;
    }

    public static double[][] array1dTo2dDouble(int[] array1D, int width, int height) {

        double[][] array = new double[width][height];
        int index = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                array[i][j] = array1D[index++];
            }
        }
        return array;
    }

    public static double[] doubleArray1D(int[] array1D, int length) {

        double[] array = new double[length];

        for (int i = 0; i < length; i++) {
            array[i] = array1D[i];
        }
        return array;
    }

    public static double[][] array2dToDouble(int[][] array2D, int width, int height) {
        double[][] array = new double[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                array[i][j] = array2D[i][j];
            }
        }
        return array;
    }

    public static int[][] IntArray2D(double[][] array2D, int width, int height) {
        int[][] array = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                array[i][j] = (int) array2D[i][j];
            }
        }
        return array;
    }

    public static int[] getArray1D(BufferedImage bi) {

        int width = bi.getWidth();
        int height = bi.getHeight();
        int[] imgData = new int[width * height];
        bi.getRGB(0, 0, width, height, imgData, 0, width);
        return imgData;
    }

    public static int[][] getArray2D(BufferedImage bi) {

        int[] imgData1D = getArray1D(bi);
        int[][] imgData = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imgData[i][j] = imgData1D[i * width + j];
            }
        }
        return imgData;
    }

    public static int clamp(int p) {
        return p > 255 ? 255 : (p < 0 ? 0 : p);
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

    @Override
    public int dimension() {
        return width * height;
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

    public double absfuncX(double inputX) {

        double funcPos = Math.max(inputX, 0);
        double funcNeg = Math.max(-inputX, 0);

        System.out.println("funcPos: " + funcPos + " funcNeg: " + funcNeg);

        return funcPos + funcNeg;

    }

    public Pair<Double, Double> splitX(double inputX) {

        double funcPos = Math.max(inputX, 0);
        double funcNeg = Math.max(-inputX, 0);

        System.out.println("funcPos: " + funcPos + " funcNeg: " + funcNeg);
        Pair<Double, Double> ret = new Pair<Double, Double>(funcPos, funcNeg);

        return ret;

    }

    public List<Pair<Double, Double>> splitX(Double[] inputX, int length) {

        List<Pair<Double, Double>> ret = new ArrayList<Pair<Double, Double>>();

        for (int i = 0; i < length; i++) {

            double funcPos = Math.max(inputX[i], 0);
            double funcNeg = Math.max(-inputX[i], 0);
            // System.out.println("funcPos: " + funcPos + " funcNeg: " +
            // funcNeg);
            Pair<Double, Double> retpoint = new Pair<Double, Double>(funcPos, funcNeg);
            ret.add(retpoint);
        }

        return ret;

    }

    @Override
    public double objval(Matrix x) {

        return 0;

    }

    @Override
    public double[] gradient(Matrix x) {

        return null;

    }
    // private double splitX(double inputX) {
    //
    // }
}
