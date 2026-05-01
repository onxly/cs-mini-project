/*package EXEcutioners.algorithms.ml;

import EXEcutioners.dummy.Graph;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import java.util.ArrayList;

public class GraphSimilarityML {
	
	public static Double calculateSimilarity(Graph graphA, Graph graphB) {

        double[] vectorA = graphA.getFeatures();
        double[] vectorB = graphB.getFeatures();

        if (vectorA.length != vectorB.length) {
        	System.err.println("Invalid feature size!!!");
        }

        try {
            Instances dataset = createDataset(vectorA.length);

            Instance instanceA = createInstance(vectorA, dataset);
            Instance instanceB = createInstance(vectorB, dataset);

            dataset.add(instanceA);
            dataset.add(instanceB);

            EuclideanDistance distanceFunction = new EuclideanDistance(dataset);

            double distance = distanceFunction.distance(dataset.instance(0), dataset.instance(1));

            return 1.0 / (1.0 + distance);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private static Instances createDataset(int featureCount) {

        ArrayList<Attribute> attributes = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            attributes.add(new Attribute("feature_" + i));
        }

        Instances dataset = new Instances("GraphSimilarityDataset", attributes, 2);

        return dataset;
    }

    private static Instance createInstance(double[] vector, Instances dataset) {

        Instance instance = new DenseInstance(dataset.numAttributes());

        for (int i = 0; i < vector.length; i++) {
            instance.setValue(i, vector[i]);
        }

        instance.setDataset(dataset);

        return instance;
    }
	
}*/
