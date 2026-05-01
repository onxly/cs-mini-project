package EXEcutioners.algorithms;

import java.util.ArrayList;

import EXEcutioners.dummy.Graph;
import EXEcutioners.utils.GraphFileHelper;

public class GraphClassifier {
	
	private Graph inputGraph;
	private String closestAnimal;
	
	public GraphClassifier(Graph graph) {
		this.inputGraph = graph;
	}
	
	public String getClosest() {
		return this.closestAnimal;
	}
	
	public Graph getGraph() {
		return inputGraph;
	}
	
	public void setGraph(Graph graph) {
		this.inputGraph = graph;
		this.closestAnimal = null;
	}
	
	public String classify() {
		
		String response = GraphClassifier.classify(inputGraph);
		
		this.closestAnimal = response.split(":")[0];

		return response;
	}
	
	public static String classify(Graph inGraph) {
		
		ArrayList<Graph> graphs = GraphFileHelper.readGraphsFromDirectory("data/graphs");
		
		if(graphs == null) {
			
			System.err.println("Something is wrong with the graphs folder!");
			return "GraphClassifier Error!";
			
		}
		
		String animalName = "";
		
		Double maxScore = null;
		
		for(Graph graph : graphs) {
			
			Double score = GraphSimilarity.calculateScore(inGraph, graph);
			
			if(maxScore == null || score > maxScore) {
				
				maxScore = score;
				animalName = graph.getName();
				
			}
			
		}
		
		return animalName + ": " + (maxScore * 100) + "%";
	}

}
