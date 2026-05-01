package EXEcutioners.algorithms;

import java.util.ArrayList;

import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.utils.GraphFileHelper;

public class GraphClassifier {
	
	private ImageGraph inputGraph;
	private String closestAnimal;
	
	public GraphClassifier(ImageGraph graph) {
		this.inputGraph = graph;
	}
	
	public String getClosest() {
		return this.closestAnimal;
	}
	
	public ImageGraph getGraph() {
		return inputGraph;
	}
	
	public void setGraph(ImageGraph graph) {
		this.inputGraph = graph;
		this.closestAnimal = null;
	}
	
	public String classify() {
		
		String response = GraphClassifier.classify(inputGraph);
		
		this.closestAnimal = response.split(":")[0];

		return response;
	}
	
	public static String classify(ImageGraph inGraph) {
		
		ArrayList<ImageGraph> graphs = GraphFileHelper.readGraphsFromDirectory("data/graphs");
		
		if(graphs == null) {
			
			System.err.println("Something is wrong with the graphs folder!");
			return "GraphClassifier Error!";
			
		}
		
		String animalName = "";
		
		Double maxScore = null;
		
		for(ImageGraph graph : graphs) {
			
			Double score = GraphSimilarity.calculateScore(inGraph, graph);
			
			if(maxScore == null || score > maxScore) {
				
				maxScore = score;
				animalName = graph.getAnimalName();
				
			}
			
		}
		
		return animalName + ": " + (maxScore * 100) + "%";
	}

}
