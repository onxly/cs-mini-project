package EXEcutioners.algorithms.ml;

import weka.classifiers.trees.RandomForest;
import weka.core.*;

public class SpeciesPredictor {

	private RandomForest model;
    private Instances header;

    public SpeciesPredictor(String modelPath, Instances header) throws Exception {
        this.model = (RandomForest) SerializationHelper.read(modelPath);
        this.header = header;
    }

    public String classify(double[] histogram) throws Exception {
        Instance instance = new DenseInstance(1.0, new double[header.numAttributes()]);
        instance.setDataset(header);

        for (int i = 0; i < histogram.length; i++) {
            instance.setValue(i, histogram[i]);
        }

        double resultIndex = model.classifyInstance(instance);

        return header.classAttribute().value((int) resultIndex);
    }
    
    public double[] getConfidences(double[] histogram) throws Exception {
        Instance instance = new DenseInstance(1.0, histogram);
        instance.setDataset(header);
        return model.distributionForInstance(instance);
    }
    
}
