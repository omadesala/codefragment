package com.omade.cluster;

import Jama.Matrix;
import com.omade.optimize.MatrixUtils;
import com.omade.utils.JavaGNUplot;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

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

            Random random = new Random(1000);
            ins = WekaMysqlUtil.load();
            ins.randomize(random);

            // ins.deleteAttributeType(Attribute.DATE);
            // ins.

			/*
             * 2.初始化聚类器 在3.6版本可以通过setDistanceFunction(DistanceFunction df)
			 * 函数设置聚类算法内部的距离计算方式 而在3.5版本里面默认的采用了欧几里得距离
			 */

            for (int m = 0; m < 1; m++) {

                KM = new SimpleKMeans();
                // 设置聚类要得到的类别数量
                DistanceFunction df = new CosineDistanceMeasure();
                // DistanceFunction df = new EuclideanDistance();
                // df.setInstances(ins);
                // KM.setInitializationMethod(null);
                KM.setDistanceFunction(df);
                KM.setNumClusters(11);
//                KM.setNumClusters(5 + m);
                KM.setDisplayStdDevs(true);
                // KM.setSeed(10);
                KM.setPreserveInstancesOrder(true);

				/*
                 * 3.使用聚类算法对样本进行聚类
				 */
                KM.buildClusterer(ins);
                System.out.println("trainning finished !");
                System.out.println("cluster num:" + KM.getNumClusters());

				/*
                 * 4.打印聚类结果
				 */
                tempIns = KM.getClusterCentroids();
                // System.out.println("CentroIds: " + tempIns);

                double[] clusterSizes = KM.getClusterSizes();
                Arrays.sort(clusterSizes);
                System.out.println("cluster size: " + clusterSizes.toString());
                int columnNum = ins.numAttributes();
                double clusterIndexSize = 10;

                System.out.println("^^^^^^^^^^^^^^^^^^^^^^clusterIndexSize:" + clusterIndexSize);
                int[] assignments = KM.getAssignments();
                int rownum = 0;
                System.out.println("print assignments");

                int clusternum = 1;
                int clustertotal = 0;
                for (int i = 0, len = assignments.length; i < len; i++) {
                    if (assignments[i] == clusternum) {
                        clustertotal++;
                    }
                }


                System.out.println("cluster order: " + clusternum + " size: " + clustertotal);
                Matrix records = new Matrix(clustertotal, columnNum);

                for (int i = 0, len = assignments.length; i < len; i++) {

                    System.out.print(assignments[i] + "\t");

                    if (assignments[i] == clusternum) {
                        double[] rowdata = ins.get(i).toDoubleArray();
//                        System.out.println("row: " + Arrays.toString(rowdata));
                        for (int k = 0; k < rowdata.length; k++) {
                            records.set(rownum, k, rowdata[k]);
                        }
                        rownum++;
                    }
                }

                MatrixUtils.printMatrix(records);

//                JavaGNUplot.showData("cluster size", "order", "size", new Matrix(clusterSizes, clusterSizes.length).transpose());
                JavaGNUplot.showDataArray("cluster size", "order", "size", records.transpose());


//                long suberror = 0;
//                long sumTopN = 0;
//                double sum = 0;
//
//                for (int k = 0; k < clusterSizes.length; k++) {
//                    // suberror += (clusterSizes[k + 1] - clusterSizes[k]);
//                    sum += clusterSizes[k];
//                }
//                for (int k = clusterSizes.length - clusterSizes.length / 3; k < clusterSizes.length; k++) {
//                    // suberror += (clusterSizes[k + 1] - clusterSizes[k]);
//                    sumTopN += clusterSizes[k];
//                }
//
//                // System.out.println("\nsuberror: " + suberror);
//                System.out.println("\n sumTopN: " + sumTopN + " percent: "
//                        + sumTopN / sum);
//                System.out.println("\n cluster size: "
//                        + Arrays.toString(clusterSizes));


                // int i = 0;
                // for (int clusterNum : assignments) {
                // System.out.println("Instance " + i + " -> Cluster "
                // + clusterNum);
                // i++;
                // }

                //
                // eval.setClusterer(KM); // the cluster to evaluate
                // eval.evaluateClusterer(ins); // data to evaluate the
                // clusterer
                // // on
                // System.out.println("# of clusters: " +
                // eval.getNumClusters()); //
                // output
                // #
                // ofs
                // clusters
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}