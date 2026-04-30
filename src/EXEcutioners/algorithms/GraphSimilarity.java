package EXEcutioners.algorithms;

import java.util.Iterator;

import EXEcutioners.adts.abstractClasses.Edge;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;
import EXEcutioners.dummy.Graph;
import EXEcutioners.dummy.RegionNode;

//simple version (will be more complex later)
public class GraphSimilarity {
	
	private Double score;
	private Graph graphA;
	private Graph graphB;
	
	public GraphSimilarity(Graph graphA, Graph graphB) {
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
	
	public Graph getGraphA() {
		return this.graphA;
	}
	
	public Graph getGraphB() {
		return this.graphB;
	}
	
	public void setGraphA(Graph graph) {
		this.graphA = graph;
		this.score = null;
	}
	
	public void setGraphB(Graph graph) {
		this.graphB = graph;
		this.score = null;
	}
	
	public void calculateScore() {
		
		this.score = GraphSimilarity.calculateScore(this.graphA, this.graphB);
		
	}
	
	public static Double calculateScore(Graph a, Graph b) {
		
		Double totalDistance = 0.0;
		
		Iterator<RegionNode> regionsA =  a.getRegions().iterator();
		
		while(regionsA.hasNext()) {
			
			RegionNode i = regionsA.next();
			
			Double minDistance = null;
			
			Iterator<RegionNode> regionsB =  b.getRegions().iterator();
			
			while(regionsB.hasNext()) {
				
				RegionNode j = regionsB.next();
				
				Double distance = calculateDistance(i,j);
				
				if(minDistance == null || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			totalDistance += minDistance;
			
		}
		
		return 1.0/(1.0+(totalDistance/a.getRegions().size()));
	}
	
	
	public static Double calculateDistance(RegionNode a, RegionNode b) {
		
		Double[] featuresA = a.getFeatures();
		Double[] featuresB = b.getFeatures();
		
		Double sum = 0.0;
		Double finalDistance;
		int numFeatures = featuresA.length;
		
		if(featuresB.length != numFeatures) return null;
		
		for(int i=0; i < numFeatures; i++) {
			
			sum += Math.pow(featuresA[i] - featuresB[i], 2);
			
		}
		
		finalDistance = (0.1 * Math.abs(a.getAvgWeight() - b.getAvgWeight())) + (0.2 * Math.abs(a.getDegree() - b.getDegree())) + (0.7 * Math.sqrt(sum));
		
		return finalDistance;
	}
	
}
