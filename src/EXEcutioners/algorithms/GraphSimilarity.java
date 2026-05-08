package EXEcutioners.algorithms;

import java.util.Iterator;

import EXEcutioners.adts.abstractClasses.Edge;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.graph.AnimalSignature;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;
import EXEcutioners.dummy.Graph;
import EXEcutioners.dummy.RegionNode;

//simple version (will be more complex later)
public class GraphSimilarity {
	
	private Double score;
	private AnimalSignature graphA;
	private AnimalSignature graphB;
	
	public GraphSimilarity(AnimalSignature graphA, AnimalSignature graphB) {
		score = null;
		this.graphA = graphA;
		this.graphB = graphB;
	}
	
	public Double getScore() {
		if(score == null) {
			calculateScore();
		}
		
		return this.score;
	}
	
	public AnimalSignature getGraphA() {
		return this.graphA;
	}
	
	public AnimalSignature getGraphB() {
		return this.graphB;
	}
	
	public void setGraphA(AnimalSignature graph) {
		this.graphA = graph;
		this.score = null;
	}
	
	public void setGraphB(AnimalSignature graph) {
		this.graphB = graph;
		this.score = null;
	}
	
	public void calculateScore() {
		
		this.score = GraphSimilarity.calculateScore(this.graphA, this.graphB);
		
	}
	
	public static Double calculateScore(AnimalSignature a, AnimalSignature b) {
		/*
		Double totalDistance = 0.0;
		
		Iterator<IVertex<GraphNode>> regionsA =  a.vertices().iterator();
		
		while(regionsA.hasNext()) {
			
			IVertex<GraphNode> i = regionsA.next();
			
			Double minDistance = null;
			
			Iterator<IVertex<GraphNode>> regionsB =  b.vertices().iterator();
			
			while(regionsB.hasNext()) {
				
				IVertex<GraphNode> j = regionsB.next();
				
				Double distance = calculateDistance(i,j);
				
				if(minDistance == null || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			totalDistance += minDistance;
			
		}
		
		return 1.0/(1.0+(totalDistance/a.numVertices()));
		*/
		double distance = calculateDistance(a, b);

	    // 'sigma' controls the sensitivity. 
	    // Lower sigma = more strict (percentages drop faster)
	    // Higher sigma = more lenient (percentages stay higher)
	    // For normalized histograms, 1.0 to 2.0 is usually perfect.
	    double sigma = 1.5; 

	    // Formula: e^(-distance / sigma) * 100
	    double similarity = Math.exp(-distance / sigma) * 100.0;

	    return similarity; // Ensure it doesn't go negative
	}
	
	
	private static double calculateDistance(AnimalSignature a, AnimalSignature b) {
        double sum = 0;
        for (int i = 0; i < a.getHistogramSize(); i++) sum += Math.pow(a.histogram[i] - b.histogram[i], 2);
        sum += Math.pow(a.getAverageConnectivity() - b.getAverageConnectivity(), 2) * 10;
        return Math.sqrt(sum);
    }
	
}
