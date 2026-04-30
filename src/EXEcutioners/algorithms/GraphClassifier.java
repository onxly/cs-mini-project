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
	}
	
	public String classify() {
		
		ArrayList<Graph> graphs = GraphFileHelper.readGraphsFromDirectory("data/graphs");
		
		Double maxScore = null;
		
		for(Graph graph : graphs) {
			
			Double score = GraphSimilarity.calculateScore(inputGraph, graph);
			
			if(maxScore == null || score > maxScore) {
				
				maxScore = score;
				this.closestAnimal = graph.getName();
				
			}
			
		}
		
		return this.closestAnimal + ": " + (maxScore * 100) + "%";
	}
	
	public static String classify(Graph inGraph) {
		
		ArrayList<Graph> graphs = GraphFileHelper.readGraphsFromDirectory("data/graphs");
		
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
