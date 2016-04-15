package com.omade.cluster;

import Jama.Matrix;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.omade.optimize.MatrixUtils;
import com.omade.utils.JavaGNUplot;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.omade.utils.WekaUtil;

/**
 * Created by ping on 16-4-11.
 */
public class Kmeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Instances> train = train(WekaUtil.load(),
				new CosineDistanceMeasure(), 10);
		// show(train);
	}

	/**
	 * The last one is the Centroid
	 * 
	 * @param data
	 * @param disFun
	 * @param clusterNumber
	 * @return
	 */
	public static List<Instances> train(Instances data,
			DistanceFunction disFun, int clusterNumber) {

		List<Instances> rets = Lists.newArrayList();
		SimpleKMeans KM = new SimpleKMeans();
		Random random = new Random(1000);
		// ClusterEvaluation eval = new ClusterEvaluation();

		try {
			// shuffle
			data.randomize(random);
			KM.setNumClusters(11);
			// KM.setInitializationMethod(null);
			KM.setDistanceFunction(disFun);
			KM.setDisplayStdDevs(true);
			// KM.setSeed(10);
			KM.setPreserveInstancesOrder(true);

			KM.buildClusterer(data);

			System.out.println("trainning finished !");
			int numClusters = KM.getNumClusters();
			Instances clusterCentroids = KM.getClusterCentroids();
			for (int i = 0; i < numClusters; i++) {
				rets.add(new Instances(data, 0));
			}

			System.out.println("cluster num:" + numClusters);
			System.out.println("CentroIds: " + clusterCentroids);

			int[] assignments = KM.getAssignments();
			for (int i = 0, len = assignments.length; i < len; i++) {
				Instance instance = data.get(i);
				rets.get(assignments[i]).add(instance);
			}
			rets.add(clusterCentroids);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rets;
	}

	public static void show(List<Instances> data) {

		Preconditions.checkArgument(data != null && data.size() > 0);

		for (Instances instances : data) {
			Matrix oneCluster = WekaUtil.getMatrix(instances);
			JavaGNUplot.showDataArray("cluster size", "order", "size",
					oneCluster.transpose());
		}
	}

}