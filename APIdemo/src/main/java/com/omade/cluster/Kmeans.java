package com.omade.cluster;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

import com.omade.utils.WekaMysqlUtil;

/**
 * Created by ping on 16-4-11.
 */
public class Kmeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instances ins = null;
		Instances tempIns = null;

		SimpleKMeans KM = null;
		DistanceFunction disFun = null;
		ClusterEvaluation eval = new ClusterEvaluation();
		try {
			/*
			 * 1.读入样本
			 */
			// File file = new
			// File("C://Program Files//Weka-3-6//data//contact-lenses.arff");
			// ArffLoader loader = new ArffLoader();
			// loader.setFile(file);
			// ins = loader.getDataSet();

			ins = WekaMysqlUtil.load();

			// ins.deleteAttributeType(Attribute.DATE);
			// ins.

			/*
			 * 2.初始化聚类器 在3.6版本可以通过setDistanceFunction(DistanceFunction df)
			 * 函数设置聚类算法内部的距离计算方式 而在3.5版本里面默认的采用了欧几里得距离
			 */
			KM = new SimpleKMeans();
			// 设置聚类要得到的类别数量
			DistanceFunction df = new CosineDistanceMeasure();
			// df.setInstances(ins);
			KM.setDistanceFunction(df);
			KM.setNumClusters(10);
			KM.setDisplayStdDevs(true);
			// KM.setSeed(10);
			KM.setPreserveInstancesOrder(true);

			/*
			 * 3.使用聚类算法对样本进行聚类
			 */
			KM.buildClusterer(ins);
			System.out.println("trainning finished !");

			/*
			 * 4.打印聚类结果
			 */
			tempIns = KM.getClusterCentroids();
			System.out.println("CentroIds: " + tempIns);

			int[] assignments = KM.getAssignments();
			int i = 0;
			for (int clusterNum : assignments) {
				System.out.println("Instance " + i + " -> Cluster "
						+ clusterNum);
				i++;
			}
			//
			// eval.setClusterer(KM); // the cluster to evaluate
			// eval.evaluateClusterer(ins); // data to evaluate the clusterer
			// // on
			// System.out.println("# of clusters: " + eval.getNumClusters()); //
			// output
			// #
			// of
			// clusters

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}