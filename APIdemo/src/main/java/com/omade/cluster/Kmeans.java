package com.omade.cluster;

import com.google.common.collect.Lists;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.Collections;
import java.util.List;

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
//			DistanceFunction df = new CosineDistanceMeasure();
            DistanceFunction df = new EuclideanDistance();
            KM.setDistanceFunction(df);
            KM.setNumClusters(10);
            KM.setDisplayStdDevs(true);
//			KM.setInitializationMethod();
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
                System.out.print(clusterNum + "\t");
//                System.out.println("Instance " + i + " -> Cluster "
//                        + clusterNum);
                i++;
            }

            System.out.println("cluster size: ");
            List<Double> sortData = Lists.newArrayList();
            double[] clusterSizes = KM.getClusterSizes();
            for (double item : clusterSizes) {
//                System.out.print(item + "\t");
                sortData.add(item);
            }
            Collections.sort(sortData);

            System.out.println("sorted cluster size: " + sortData.toString());
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