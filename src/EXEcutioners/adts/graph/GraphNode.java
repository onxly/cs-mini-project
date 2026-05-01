package EXEcutioners.adts.graph;

import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IPosition;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphNode extends Vertex<String ,Double> {
	String regionPosition;
	
    Double[] featureVector;
    
    public GraphNode(String regionPosition, Double[] featureVector) {
    	super("Something", false);
        this.regionPosition = regionPosition;
        this.featureVector = featureVector;
    }
	
	public int[] getRegionPos() {
		return new int[2];
	}
	public double[] getRegionVector() {
		return new double[2];
	}

	@Override
	public String getElement() {
		return super.getElement();
	}

	@Override
	public IPosition<IVertex<String>> getPosition() {
		PQEntry<String, Double[]> entry = new PQEntry<String, Double[]>(regionPosition, featureVector);
		return (IPosition<IVertex<String>>) entry;
	}

}
