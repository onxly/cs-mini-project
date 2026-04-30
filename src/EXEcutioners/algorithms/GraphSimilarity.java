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
	}
	
	public void setGraphB(Graph graph) {
		this.graphB = graph;
	}
	
	public void calculateScore() {
		
		Double totalDistance = 0.0;
		
		Iterator<RegionNode> regionsA =  graphA.getRegions().iterator();
		
		Iterator<RegionNode> regionsB =  graphB.getRegions().iterator();
		
		while(regionsA.hasNext()) {
			
			RegionNode i = regionsA.next();
			
			Double minDistance = null;
			
			while(regionsB.hasNext()) {
				
				RegionNode j = regionsB.next();
				
				Double distance = calculateDistance(i,j);
				
				if(minDistance == null || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			totalDistance += minDistance;
			
		}
		
		this.score = 1/1+(totalDistance/16);
	}
	
	public static Double calculateScore(Graph a, Graph b) {
		
		Double totalDistance = 0.0;
		
		Iterator<RegionNode> regionsA =  a.getRegions().iterator();
		
		Iterator<RegionNode> regionsB =  b.getRegions().iterator();
		
		while(regionsA.hasNext()) {
			
			RegionNode i = regionsA.next();
			
			Double minDistance = null;
			
			while(regionsB.hasNext()) {
				
				RegionNode j = regionsB.next();
				
				Double distance = calculateDistance(i,j);
				
				if(minDistance == null || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			totalDistance += minDistance;
			
		}
		
		return 1/1+(totalDistance/16);
	}
	
	// This might be the same one @Boyzn uses (reference that instead if that is the case)
	public static Double calculateDistance(RegionNode a, RegionNode b) {
		
		Double[] featuresA = a.getFeatures();
		Double[] featuresB = b.getFeatures();
		
		Double sum = 0.0;
		
		for(int i=0; i<featuresA.length; i++) {
			
			sum += Math.pow(featuresA[i] - featuresB[i], 2);
			
		}
		
		
		return Math.sqrt(sum);
	}
	
}
