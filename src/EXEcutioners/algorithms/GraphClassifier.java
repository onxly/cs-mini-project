/*package EXEcutioners.algorithms;

import java.util.ArrayList;
import java.util.Comparator;

import EXEcutioners.adts.abstractClasses.HashMap;
import EXEcutioners.adts.abstractClasses.Heap;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.utils.GraphFileHelper;

public class GraphClassifier {
	private static class DefaultComparator implements Comparator<Double> {

		@Override
		public int compare(Double o1, Double o2) {
			// TODO Auto-generated method stub
			return ((Comparable<Double>) o2).compareTo(o1);
		}

	}
	
	
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
		
		ArrayList<ImageGraph> graphs = GraphFileHelper.readGraphsFromDirectory("graphs");
		
		if(graphs == null) {
			
			System.err.println("Something is wrong with the graphs folder!");
			return "GraphClassifier Error!";
			
		}
		
		String animalName = "";
		
		Double maxScore = null;
		
		Heap<Double, String> scores = new Heap<>(new DefaultComparator());
		
		for(ImageGraph graph : graphs) {
			
			Double score = GraphSimilarity.calculateScore(inGraph, graph);
			
			scores.insert(score, graph.getAnimalName());
			
			
		}
		
		ArrayList<IEntry<Double, String>> top = new ArrayList<IEntry<Double,String>>();
		
		for(int i=0; i<5; i++) {
			
			top.add(scores.removeMin());
			
		}
		
		HashMap<String, Integer> animalCounter = new HashMap<>();
		
		for(IEntry<Double, String> entry : top) {
			
			String currentAnimal = entry.getValue();
			
			Integer currentCount = animalCounter.get(currentAnimal);
			
			if(currentCount != null) {
				
				animalCounter.put(currentAnimal, currentCount + 1);
				
			}else {
				animalCounter.put(currentAnimal, 1);
			}
			
		}
		
		if (animalCounter == null || animalCounter.isEmpty()) {
			return "Something went wrong";
		}
		
		if(animalCounter.size() == top.size()) {
			
			animalName = top.get(0).getValue();
			//maxScore = top5.get(0).getKey();
			
		}else {
			
			String maxKey = null;
			Integer maxValue = null;

			for (IEntry<String, Integer> entry : animalCounter.entrySet()) {
			    if (maxValue == null || entry.getValue() > maxValue) {
			        maxValue = entry.getValue();
			        maxKey = entry.getKey();
			    }
			}
			
			animalName = maxKey;
		}		

			
		return top.get(0).getValue();
	}

}*/
