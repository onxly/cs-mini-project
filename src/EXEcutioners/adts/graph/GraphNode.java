package EXEcutioners.adts.graph;

import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphNode extends Vertex<String ,Double> {
	int[] regionPosition;
    double[] featureVector;
    
    public GraphNode(int[] regionPosition, double[] featureVector) {
    	super("Something", false);
        this.regionPosition = regionPosition;
        this.featureVector = featureVector;
    }
	
	public int[] getRegionPos() {
		return this.regionPosition;
	}
	public double[] getRegionVector() {
		return this.featureVector;
	}

}
