package EXEcutioners.algorithms.ml;

import java.io.File;

import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SpeciesClassifier {

	public static void train(Instances data) {
	   	    
	    if (data.classIndex() == -1)
	        data.setClassIndex(data.numAttributes() - 1);

	    RandomForest rf = new RandomForest();
	    rf.setNumIterations(100);
	    try {
			rf.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    System.out.println("Model trained successfully!");
	    
	    try {
	    	
	    	File modelFolder = new File("model");
	    	
	    	if(!modelFolder.exists() || !modelFolder.isDirectory()) {
	    		modelFolder.mkdir();
	    	}
	    	
			weka.core.SerializationHelper.write("model/wildgraph.model", rf);
			System.out.println("Model file saved!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
