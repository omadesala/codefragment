package com.omade.cluster;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

import com.omade.utils.WekaUtil;

/**
 * Created by ping on 16-4-11.
 * http://blog.csdn.net/zhaoxinfan/article/details/8955254
 */
public class EMCluster {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Instances ins = null;
        Instances tempIns = null;

        // SimpleKMeans KM = null;
        EM em = null;
        DistanceFunction disFun = null;
        try {
            /*
             * 1.读入样本
			 */
            // File file = new
            // File("C://Program Files//Weka-3-6//data//contact-lenses.arff");
            // ArffLoader loader = new ArffLoader();
            // loader.setFile(file);
            // ins = loader.getDataSet();

            ins = WekaUtil.load("IBM11");

            ins.deleteAttributeType(Attribute.DATE);
            // ins.

			/*
			 * 2.初始化聚类器 在3.6版本可以通过setDistanceFunction(DistanceFunction df)
			 * 函数设置聚类算法内部的距离计算方式 而在3.5版本里面默认的采用了欧几里得距离
			 */
            // KM = new SimpleKMeans();

            // em = new EM();
            // 设置聚类要得到的类别数量
            // train clusterer
            EM clusterer = new EM();
            // set further options for EM, if necessary...
            String[] options = new String[4];

            // max. iterations
            options[0] = "-I";
            options[1] = "100";
            // set cluseter numbers
            options[2] = "-N";
            options[3] = "12";

            clusterer.setOptions(options);
            clusterer.buildClusterer(ins);

            System.out.println("ret: " + clusterer.toString());

            // evaluate clusterer
            // ClusterEvaluation eval = new ClusterEvaluation();
            // eval.setClusterer(clusterer);
            // eval.evaluateClusterer(data);

            // print results
            // System.out.println(eval.clusterResultsToString());
			/*
			 * 3.使用聚类算法对样本进行聚类
			 */
            // KM.buildClusterer(ins);

			/*
			 * 4.打印聚类结果
			 */
            // tempIns = KM.getClusterCentroids();
            // System.out.println("CentroIds: " + tempIns);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}