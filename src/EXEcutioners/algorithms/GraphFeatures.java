/*package EXEcutioners.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphFeatures {

	public static ArrayList<Double> getVector(ImageGraph a){
		
		ArrayList<Double> vectorA = new ArrayList<Double>();
		
		Iterator<IVertex<IEntry<Integer[], Double[]>>> regionsA =  a.vertices().iterator();
		
		while(regionsA.hasNext()) {
			
			Double[] featuresA = regionsA.next().getElement().getValue();
			
			for(Double feature : featuresA) {
				vectorA.add(feature);
			}
		}
		
		return vectorA;
	}
	
}*/
