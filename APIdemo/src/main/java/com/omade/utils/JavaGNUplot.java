package com.omade.utils;

import Jama.Matrix;
import com.omade.optimize.MatrixUtils;
import com.panayotis.gnuplot.GNUPlotParameters;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

public class JavaGNUplot {

	/**
	 * @param args
	 *            参数描述
	 * @throws
	 * @Description: main method for entry
	 */
	public static void main(String[] args) {

		Matrix data = MatrixUtils.randomRow(10);
		MatrixUtils.printMatrix(data);
		showData("stock data", "time", "price", data);
	}

	/**
	 * @param data
	 *            one dim data
	 */
	public static void showData(String title, String xAxisName,
			String yAxisName, Matrix data) {

		GNUPlotParameters param = new GNUPlotParameters(false);

		ArrayList<String> preInit = param.getPreInit();

		// preInit.add("set contour base");// draw contour

		StringBuffer xrange = new StringBuffer();
		int dataSize = data.getColumnDimension();
		xrange.append("set xrange [0:" + dataSize + "]");

		System.out.println("xrange:" + xrange.toString());
		Double maxElement = MatrixUtils.getMaxElement(data);
		Double minElement = MatrixUtils.getMinElement(data);

		StringBuffer yrange = new StringBuffer();
		yrange.append("set yrange [" + minElement + ":" + maxElement + "]");
		System.out.println("yrange:" + yrange.toString());

		preInit.add(xrange.toString());// draw contour
		preInit.add(yrange.toString());// draw contour
		// preInit.add("set xrange [-5:20]");// draw contour
		// preInit.add("set yrange [-5:20]");// draw contour
		// preInit.add("set size square");// draw contour

		JavaPlot p = new JavaPlot(param, "/usr/bin/gnuplot", null);

		p.setTitle(title);
		p.getAxis("x").setLabel(xAxisName, "Arial", 20);
		p.getAxis("y").setLabel(yAxisName);

		Matrix points = new Matrix(2, dataSize);

		for (int j = 0; j < dataSize; j++) {
			points.set(0, j, j);
			points.set(1, j, MatrixUtils.getRowAsArray(data, 0)[j]);
		}

		System.out.println("draw");
		MatrixUtils.printMatrix(points);

		DataSetPlot s = new DataSetPlot(points.transpose().getArray());

		PlotStyle myPlotStyle = new PlotStyle();
		myPlotStyle.setStyle(Style.LINESPOINTS);
		myPlotStyle.setLineWidth(1);
		s.setPlotStyle(myPlotStyle);
		p.addPlot(s);
		p.plot();
		p.addPlot("0; pause 1000;");
	}

}
