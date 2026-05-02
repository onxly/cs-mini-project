package EXEcutioners.adts.graph;

import java.util.List;

import EXEcutioners.adts.abstractClasses.Heap;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IVertex;

public class ImageGraph extends MapGraph<IEntry<Integer[], Double[]>, Double> {
	private int k;
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
	
	
	public double calculateDistance(IVertex<IEntry<Integer[], Double[]>> node1, IVertex<IEntry<Integer[], Double[]>> node2) {
        double sum = 0;
        for (int i = 0; i < node1.getElement().getValue().length; i++) {
        	//node.feature is to change...aker idk what the vector is to beS
            sum += Math.pow(node1.getElement().getValue()[i] - node2.getElement().getValue()[i], 2);
        }
        return Math.sqrt(sum);
    }
	
	public void buildGraph(Iterable<GraphNode> NodeList) {
		int iVertex = 1;
		for (GraphNode node : NodeList) {
			Vertex<IEntry<Integer[], Double[]>, Double> vertex = super.insertVertex(new PQEntry<>(node.regionPosition, node.featureVector));
			vertex.setName("v"+iVertex);

			iVertex++;
		}
		
		for (IVertex<IEntry<Integer[], Double[]>> node1 : super.vertices) {
			Heap<Double, IVertex<IEntry<Integer[], Double[]>>> list = new Heap<>();
			for (IVertex<IEntry<Integer[], Double[]>> node2 : super.vertices) {
				if (node1 != node2) {
					double dist = calculateDistance(node1, node2);
					list.insert(dist, node2);
					
				}
			}
			
			int iCount = 0;
			while (!list.isEmpty() && iCount < k) {
				IEntry<Double, IVertex<IEntry<Integer[], Double[]>>> entry =  list.removeMin();
				
				double dis = entry.getKey();
				IVertex<IEntry<Integer[], Double[]>> node2 = entry.getValue();
				try {
					super.insertEdge(node1, node2, dis);
					iCount++;	
				} catch (IllegalArgumentException e) {
					
				}
									
			}	
		}
		}
	
	//Testing purposes
	public void drawGraph() {
		StringBuilder sGraph = new StringBuilder();
		sGraph.append("--- Graph Test Output ---\n");

		for (IVertex<IEntry<Integer[], Double[]>> v : vertices()) {
		    Vertex<IEntry<Integer[], Double[]>, Double> vertex = (Vertex<IEntry<Integer[], Double[]>, Double>) v;
		    
		    sGraph.append(String.format("Vertex [%s]\n", v.getName()));
		    
		    // 2. Loop through outgoing edges to draw neighbors
		    var outgoing = vertex.getOutgoing();
		    if (outgoing.isEmpty()) {
		        sGraph.append("   (No outgoing edges)\n");
		    } else {
		        for (var entry : outgoing.entrySet()) {
		            IVertex<IEntry<Integer[], Double[]>> n = entry.getKey();
		            IEdge<IEntry<Integer[], Double[]>, Double> edge = entry.getValue();
		            
		            // Format: --> [NeighborElement] (Weight: 0.85)
		            sGraph.append(String.format("   --> %s (Weight: %.2f)\n", 
		            	n.getName(), 
		                edge.getElement()));
		        }
		    }
		    sGraph.append("\n"); // Space between vertices
		}
		System.out.println(sGraph.toString());

	}
	
	public void setAnimalName(String animal) {	
		this.animalName = animal;	
	}
	
	public String getAnimalName() {
		return this.animalName;
	}
	
}
