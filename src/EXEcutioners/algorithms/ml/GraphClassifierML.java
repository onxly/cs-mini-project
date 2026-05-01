package EXEcutioners.algorithms.ml;

import java.util.ArrayList;

import EXEcutioners.dummy.Graph;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class GraphClassifierML {

	private IBk classifier;
    private Instances dataset;
    private int featureCount;
    private ArrayList<String> classLabels;

    public GraphClassifierML(int k) {
        this.classifier = new IBk(k);
        this.classLabels = new ArrayList<>();
    }

    public void train(ArrayList<Graph> trainingGraphs) {

        if (trainingGraphs == null || trainingGraphs.isEmpty()) {
        	System.err.println("No Graphs to train on!!!");
            return;
        }

        double[] firstVector = {}; // get first vector
        this.featureCount = firstVector.length;

        ArrayList<Attribute> attributes = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            attributes.add(new Attribute("feature_" + i));
        }

        for (Graph graph : trainingGraphs) {
            String animal = graph.getName();

            if (!classLabels.contains(animal)) {
                classLabels.add(animal);
            }
        }

        Attribute classAttribute = new Attribute("animal", classLabels);
        attributes.add(classAttribute);

        dataset = new Instances("WildlifeGraphs", attributes, trainingGraphs.size());
        dataset.setClassIndex(dataset.numAttributes() - 1);

        for (Graph graph : trainingGraphs) {
            double[] vector = graph.getFeatures(); // get feature vectors

            if (vector.length != featureCount) {
                System.err.println("Invalid feature size!!!");
                continue;
            }

            Instance instance = new DenseInstance(dataset.numAttributes());

            for (int i = 0; i < featureCount; i++) {
                instance.setValue(attributes.get(i), vector[i]);
            }

            instance.setValue(classAttribute, graph.getName());
            instance.setDataset(dataset);

            dataset.add(instance);
        }

        try {
			classifier.buildClassifier(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String classify(Graph inputGraph) throws Exception {

        if (dataset == null || classifier == null) {
        	System.err.println("Invalid feature size!!!");
        }

        double[] vector = inputGraph.getFeatures();

        if (vector.length != featureCount) {
        	System.err.println("Invalid feature size!!!");
        	return null;
        }

        Instance instance = new DenseInstance(dataset.numAttributes());

        for (int i = 0; i < featureCount; i++) {
            instance.setValue(i, vector[i]);
        }

        instance.setDataset(dataset);

        double predictedIndex = classifier.classifyInstance(instance);
        double[] distribution = classifier.distributionForInstance(instance);

        String predictedAnimal = dataset.classAttribute().value((int) predictedIndex);
        double confidence = distribution[(int) predictedIndex];

        return predictedAnimal + ": " + confidence + "%";
    }
}
