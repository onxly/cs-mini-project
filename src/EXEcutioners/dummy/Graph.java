package EXEcutioners.dummy;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import EXEcutioners.adts.abstractClasses.Edge;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;

public class Graph implements Serializable{
	
	private String name;
	private File file;
	
	private LinkedPositionalList<RegionNode> regions;
	private HashMap<RegionNode, LinkedPositionalList<Edge>> adj;
	
	public LinkedPositionalList<RegionNode> getRegions(){
		return regions;
	}
	
	public String getName() {
		return this.name;
	}
}
