package com.omade.optimize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.netlib.util.doubleW;
import org.openxmlformats.schemas.drawingml.x2006.main.ThemeDocument;

import math.jwave.Transform;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.symlets.Symlet15;

public class PGDLena {

    public static void main(String[] args) {

        File imgFile = new File("src/main/resources/lena.bmp");
        JFrame app = new JFrame();
        app.setSize(800, 600);
        try {
            BufferedImage read = ImageIO.read(imgFile);
            // Graphics g = read.createGraphics();
            // g.drawImage(read, 0, 0, read.getWidth(), read.getHeight(), null);
            // g.dispose();

            int width = read.getWidth();
            int height = read.getHeight();

            try {
                int[] array = getArray(read);
                double[] imgarr = new double[array.length];

                for (int i = 0; i < array.length; i++) {
                    imgarr[i] = array[i];
                }

                Transform t = new Transform(new FastWaveletTransform(
                        new Symlet15()));

                double[] forward = t.forward(imgarr);
                int[] col = new int[forward.length];

                for (int i = 0; i < forward.length; i++) {
                    col[i] = (int) forward[i];
                }

                BufferedImage bufImage = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                bufImage.setRGB(0, 0, width, height, col, 0, width);
                JLabel label1 = new JLabel(new ImageIcon(bufImage));
                app.add(label1);

                double[] reverse = t.reverse(forward);

                int[] recv = new int[reverse.length];

                for (int i = 0; i < reverse.length; i++) {
                    recv[i] = (int) reverse[i];
                }
                System.out.println("this is the recved image ");

                // bufImage.setRGB(0, 0, width, height, recv, 0, width);

                // JLabel label1 = new JLabel(new ImageIcon(bufImage));
                // app.add(label1);

            } catch (JWaveFailure e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JWaveException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // double[] arrTime = { 1., 1., 1., 1., 1., 1., 1., 1. };
            //
            // double[] arrHilb = t.forward(arrTime); // 1-D FWT Haar forward
            //
            // double[] arrReco = t.reverse(arrHilb); // 1-D FWT Haar reverse

            // app.setIconImage(read);

            // JLabel label1 = new JLabel(new ImageIcon(read));
            // app.add(label1);

            // Graphics graphics = app.getGraphics();
            //
            // graphics.drawImage(read, 0, 0, null);
            app.show();
            Thread.sleep(10000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int[] getArray(BufferedImage bi) {

        int width = bi.getWidth();
        int height = bi.getHeight();
        // int img[][]=new int[width][height];
        int[] imgData = new int[width * height];
        bi.getRGB(0, 0, width, height, imgData, 0, width);
        return imgData;
    }
}
