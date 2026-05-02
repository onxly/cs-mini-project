package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.graph.GraphNode;

public class LinkedRegionalList {
	private LinkedPositionalList<LinkedPositionalList<RegionFeature>> PerImagelists = new LinkedPositionalList<>();
	private LinkedPositionalList<Iterable<GraphNode>> GraphNodeList = new LinkedPositionalList<Iterable<GraphNode>>();
	
	private int size=0;
	
	public LinkedRegionalList(){};
	
	
	public void AddImageRegions(RegionList list)
	{
		//per region of said image do this....
		//List for collecting all Regions with identified features
		LinkedPositionalList<RegionFeature> RegionFeatures = new LinkedPositionalList<RegionFeature>();
		LinkedPositionalList<GraphNode> nodeFeatures = new LinkedPositionalList<GraphNode>();
		for (Iterator r = list.iterator(); r.hasNext();) 
		{
			Region reg = (Region) r.next();
			
			BufferedImage Origional = reg.getImage();
			BufferedImage Sobel = GrayBlurSobel.GetSobelImage(reg.getImage());
			//Currently a Region with identified Features
			RegionFeature RegionWithFeature = new RegionFeature(Origional, Sobel, reg.getX(), reg.getY());
			
			nodeFeatures.addLast(RegionWithFeature.getGraphNode());
			
			//Adding up all the regions of said feature
			RegionFeatures.addLast(RegionWithFeature);//List of image features;
			//Adding "list of 'feature'( which is basically the whole image) " for "nth" image;
			
			GraphNodeList.addLast(nodeFeatures);
					
		}
		
		PerImagelists.addLast(RegionFeatures);
		System.out.println("welp");
		size++;
	}
	public LinkedPositionalList<LinkedPositionalList<RegionFeature>> getPerImagelists() {
		
		return PerImagelists;
	}
	
	public void setPerImagelists(LinkedPositionalList<LinkedPositionalList<RegionFeature>> perImagelists) {
		PerImagelists = perImagelists;
	}
	
	
	public LinkedPositionalList<Iterable<GraphNode>> getGraphNodeList() {
		return GraphNodeList;
	}


	public void setGraphNodeList(LinkedPositionalList<Iterable<GraphNode>> graphNodeList) {
		GraphNodeList = graphNodeList;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
