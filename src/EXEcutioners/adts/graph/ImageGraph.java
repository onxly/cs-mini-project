package EXEcutioners.adts.graph;

import java.util.List;

import EXEcutioners.adts.abstractClasses.Heap;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IVertex;

public class ImageGraph extends MapGraph<String, Double> {
	private int k = 0;
	private String animalName;
	
	public ImageGraph(int kValue) {
		super(false);
		this.k = kValue;
	}
	
	public ImageGraph(int kValue, String animal) {
		super(false);
		this.k = kValue;
		this.animalName = animal;
	}
	
	
	public double calculateDistance(GraphNode node1, GraphNode node2) {
        double sum = 0;
        for (int i = 0; i < node1.featureVector.length; i++) {
        	//node.feature is to change...aker idk what the vector is to beS
            sum += Math.pow(node1.featureVector[i] - node2.featureVector[i], 2);
        }
        return Math.sqrt(sum);
    }
	
	public void buildGraph(Iterable<GraphNode> NodeList) {
		for (GraphNode node : NodeList) {
			Heap<Double, GraphNode> list = new Heap<>();
			
			for (GraphNode n : NodeList) {
				if (n != node) {
					double dist = calculateDistance(node, n);
					list.insert(dist, n);
				}
			}
			
			int iCount = 0;
			while (!list.isEmpty() && iCount < k) {
				IEntry<Double ,GraphNode> entry =  list.removeMin();
				double dis = entry.getKey();
				GraphNode node2 = entry.getValue();
				super.insertEdge(node, node2, dis);
			}
		}
	}
	
	public void setAnimalName(String animal) {	
		this.animalName = animal;	
	}
	
	public String getAnimalName() {
		return this.animalName;
	}
	
}
