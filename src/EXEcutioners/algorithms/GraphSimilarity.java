package EXEcutioners.algorithms;

import java.util.Iterator;

import EXEcutioners.adts.abstractClasses.Edge;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.abstractClasses.Vertex;
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
	private ImageGraph graphA;
	private ImageGraph graphB;
	
	public GraphSimilarity(ImageGraph graphA, ImageGraph graphB) {
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
	
	public ImageGraph getGraphA() {
		return this.graphA;
	}
	
	public ImageGraph getGraphB() {
		return this.graphB;
	}
	
	public void setGraphA(ImageGraph graph) {
		this.graphA = graph;
		this.score = null;
	}
	
	public void setGraphB(ImageGraph graph) {
		this.graphB = graph;
		this.score = null;
	}
	
	public void calculateScore() {
		
		this.score = GraphSimilarity.calculateScore(this.graphA, this.graphB);
		
	}
	
	public static Double calculateScore(ImageGraph a, ImageGraph b) {
		
		Double totalDistance = 0.0;
		
		Iterator<IVertex<IEntry<Integer[], Double[]>>> regionsA =  a.vertices().iterator();
		
		while(regionsA.hasNext()) {
			
			IVertex<IEntry<Integer[], Double[]>> i = regionsA.next();
			
			Double minDistance = null;
			
			Iterator<IVertex<IEntry<Integer[], Double[]>>> regionsB =  b.vertices().iterator();
			
			while(regionsB.hasNext()) {
				
				IVertex<IEntry<Integer[], Double[]>> j = regionsB.next();
				
				Double distance = calculateDistance(i,j);
				
				if(minDistance == null || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			totalDistance += minDistance;
			
		}
		
		return 1.0/(1.0+(totalDistance/a.numVertices()));
	}
	
	
	public static Double calculateDistance(IVertex<IEntry<Integer[], Double[]>> a, IVertex<IEntry<Integer[], Double[]>> b) {
		
		Double[] featuresA = a.getElement().getValue();
		Double[] featuresB = b.getElement().getValue();
		
		Double sum = 0.0;
		Double finalDistance;
		int numFeatures = featuresA.length;
		
		if(featuresB.length != numFeatures) return null;
		
		for(int i=0; i < numFeatures; i++) {
			
			sum += Math.pow(featuresA[i] - featuresB[i], 2);
			
		}
		
		Vertex<IEntry<Integer[], Double[]>, Double> aV = (Vertex<IEntry<Integer[], Double[]>, Double>) a;
		Vertex<IEntry<Integer[], Double[]>, Double> bV = (Vertex<IEntry<Integer[], Double[]>, Double>) b;
		
		Iterator<IEdge<IEntry<Integer[], Double[]>, Double>> edgesA = aV.getOutgoing().values().iterator();
		Iterator<IEdge<IEntry<Integer[], Double[]>, Double>> edgesB = bV.getOutgoing().values().iterator();
		
		Double sumA = 0.0;
		Double sumB = 0.0;
		
		while(edgesA.hasNext()) {
			
			sumA += edgesA.next().getElement();
			
		}
		
		while(edgesB.hasNext()) {
			
			sumB += edgesB.next().getElement();
			
		}
		
		int degreeA = aV.getOutgoing().size();
		int degreeB = bV.getOutgoing().size();
		
		Double avgWeightA = sumA/degreeA;
		Double avgWeightB = sumB/degreeB;
		
		
		finalDistance = (0.1 * Math.abs(avgWeightA - avgWeightB)) + (0.2 * Math.abs(degreeA - degreeB)) + (0.7 * Math.sqrt(sum));
		
		return finalDistance;
	}
	
}
