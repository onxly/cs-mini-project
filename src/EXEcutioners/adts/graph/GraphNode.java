package EXEcutioners.adts.graph;

import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphNode extends Vertex<IEntry<Integer[], Double[]> ,Double> {
	Integer[] regionPosition;
	Double[] featureVector;
    
    public GraphNode(Integer[] regionPosition, Double[] featureVector) {
    	PQEntry<Integer[], Double[]> entry = new PQEntry<Integer[], Double[]>(regionPosition, featureVector);
    	super(entry, false);
        this.regionPosition = regionPosition;
        this.featureVector = featureVector;
    }
	
	public Integer[] getRegionPos() {
		return this.regionPosition;
	}
	public Double[] getRegionVector() {
		return this.featureVector;
	}

}
