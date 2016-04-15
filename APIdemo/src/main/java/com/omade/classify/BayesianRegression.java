package com.omade.classify;

import java.util.List;

import org.apache.derby.tools.sysinfo;

import weka.core.Instances;

import com.google.common.base.Preconditions;
import com.omade.cluster.CosineDistanceMeasure;
import com.omade.cluster.Kmeans;
import com.omade.optimize.MatrixUtils;
import com.omade.utils.WekaUtil;

import Jama.Matrix;

public class BayesianRegression {

	/**
	 * 
	 * \frac{\sum_{i=1}^n y_i * exp(-\frac{\left \| x-x_i \right
	 * \|_2^2}{4})}{\sum_{i=1}^n exp(-\frac{\left \| x-x_i \right \|_2^2}{4})}
	 * 
	 * @param train
	 * @param x
	 * @return
	 */

	public static double predict(Matrix train, Matrix x) {

		Preconditions.checkArgument(train != null
				&& train.getRowDimension() > 0);
		Preconditions.checkArgument(x != null && MatrixUtils.isRow(x),
				"x is not a row");

		double expectValue = 0;
		double numerator = 1;
		double denominator = 1;
		int recordNum = train.getRowDimension();

		for (int i = 0; i < recordNum; i++) {

			Matrix rowMatrix = MatrixUtils.getRowMatrix(train, i);
			int indexOfLastElement = x.getColumnDimension() - 1;

			double trainPointY = rowMatrix.get(0, indexOfLastElement);
			Matrix trainPonitX = rowMatrix.getMatrix(0, 0, 0,
					indexOfLastElement);

			double expItemvValue = exp(trainPonitX, x);
			denominator += expItemvValue;
			numerator += trainPointY * expItemvValue;
		}

		if (denominator == 0) {
			return Double.MAX_VALUE;
		}

		expectValue = numerator / denominator;
		return expectValue;
	}

	public static double exp(Matrix x1, Matrix x2) {

		Preconditions.checkArgument(
				MatrixUtils.isRow(x1) && MatrixUtils.isRow(x2),
				"x1 or x2 not is a row");
		Preconditions.checkArgument(
				x1.getColumnDimension() == x2.getColumnDimension(),
				"x1 and x2 without same dimension");

		double subNormal2 = x1.minus(x2).norm2();
		return Math.exp(-(subNormal2 * subNormal2) / 4.0);
	}

	public static void main(String[] args) {

		String tableName = "600000SS11";

		Instances trainSet = WekaUtil.load(tableName, 0, 200);
		Instances testSet = WekaUtil.load(tableName, 201, 20);
		Instances testSetTmp = WekaUtil.load(tableName, 201, 20);

		testSet.deleteAttributeAt(testSet.numAttributes() - 1);

		Matrix matrixTestData = WekaUtil.getMatrix(testSet);
		List<Instances> clusteredInstances = Kmeans.train(trainSet,
				new CosineDistanceMeasure(), 10);

		// for (Instances cluster : clusteredInstances) {

		int numTestInstances = testSet.numInstances();
		for (int i = 0; i < numTestInstances; i++) {

			Matrix inputX = MatrixUtils.getRowMatrix(matrixTestData, i);
			Matrix matrixTrainData = WekaUtil.getMatrix(trainSet);

			double predict = predict(matrixTrainData, inputX);
			Double targetValue = testSetTmp.get(i).value(
					testSetTmp.numAttributes() - 1);
			// System.out.println("predict:" + predict);
			// System.out.println("target:" + targetValue);
			System.out.println("error:" + (targetValue - predict));

			// System.out.println("test data: " + testSetTmp.get(i).toString());
		}
		// }
	}
}
