package com.omade.utils;

import com.google.common.base.Preconditions;
import com.omade.optimize.MatrixUtils;

import Jama.Matrix;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * Created by ping on 16-4-12.
 */
public class WekaUtil {

	public static Instances load() {

		InstanceQuery query = null;
		try {
			query = new InstanceQuery();
			query.setUsername("root");
			// query.setPassword("root");
			query.setPassword("hadoop");
			query.setQuery("select * from 600000SS11");
			// query.setDatabaseURL("jdbc:mysql://localhost:3306/stocks?characterEncoding=UTF8");
			query.setDatabaseURL("jdbc:mysql://172.16.50.80:3306/stocks?characterEncoding=UTF8");
			Instances data = query.retrieveInstances();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if your data is sparse, then you can say so too
		// query.setSparseData(true);
		throw new IllegalStateException("error");
	}

	public static Instances load(String tableName, int offset, int length) {

		InstanceQuery query = null;
		try {
			query = new InstanceQuery();
			query.setUsername("root");
			// query.setPassword("root");
			query.setPassword("hadoop");
			// query.setQuery("select * from 600000SS11");
			String sql = "select * from " + tableName + " limit " + offset
					+ "," + length;
			System.out.println("execute sql: " + sql);
			query.setQuery(sql);
			// query.setDatabaseURL("jdbc:mysql://localhost:3306/stocks?characterEncoding=UTF8");
			query.setDatabaseURL("jdbc:mysql://172.16.50.80:3306/stocks?characterEncoding=UTF8");
			Instances data = query.retrieveInstances();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if your data is sparse, then you can say so too
		// query.setSparseData(true);
		throw new IllegalStateException("error");
	}

	public static Matrix getMatrix(Instances instances) {

		Preconditions.checkArgument(instances != null);

		int numInstances = instances.numInstances();
		int numAttributes = instances.numAttributes();

		Preconditions.checkArgument(numAttributes > 0 && numInstances > 0);

		Matrix records = new Matrix(numInstances, numAttributes);

		for (int i = 0; i < numInstances; i++) {
			Instance row = instances.get(i);
			for (int j = 0; j < numAttributes; j++) {
				records.set(i, j, row.value(j));
			}
		}

		return records;
	}

	public static void main(String[] args) {

		Instances load = load();

		Matrix matrix = getMatrix(load);
		MatrixUtils.printMatrix(matrix);
		System.out.println("num: " + load.size());
	}
}
