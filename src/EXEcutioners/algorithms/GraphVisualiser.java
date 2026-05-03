package EXEcutioners.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphVisualiser {
	
	private static String styles =
		    "graph {" +
		    "   fill-color: #ffffff;" +
		    "   padding: 70px;" +
		    "}" +

		    "node {" +
		    "   size: 20px;" +
		    "   fill-color: #60a5fa;" +
		    "   stroke-mode: plain;" +
		    "   stroke-color: white;" +
		    "   stroke-width: 2px;" +
		    "   text-size: 14px;" +
		    "   text-color: white;" +
		    "   text-style: bold;" +
		    "   text-background-mode: rounded-box;" +
		    "   text-background-color: #00000099;" +
		    "   text-padding: 4px, 2px;" +
		    "   text-offset: 0px, -20px;" +
		    "}" +

		    "node.query {" +
		    "   size: 34px;" +
		    "   fill-color: #facc15;" +
		    "   stroke-color: white;" +
		    "   stroke-width: 3px;" +
		    "}" +

		    "node.classA {" +
		    "   fill-color: #60a5fa;" +
		    "}" +

		    "node.classB {" +
		    "   fill-color: #34d399;" +
		    "}" +

		    "node.classC {" +
		    "   fill-color: #f87171;" +
		    "}" +

		    "edge {" +
		    "   size: 1px;" +
		    "   fill-color: black;" +
		    "   arrow-shape: none;" +
		    "}";
	
	public static Graph drawGraph(String graphName, ImageGraph imgGraph) {
		
		Graph graph = new SingleGraph(graphName);
	    
		graph.setAttribute("ui.stylesheet", styles);
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
	    
	    graph.setStrict(false);
	    graph.setAutoCreate(true);
	    
	    ArrayList<Node> nodes = new ArrayList<Node>();
	    Iterator<IVertex<IEntry<Integer[], Double[]>>> regions = imgGraph.vertices().iterator();
	    
	    while(regions.hasNext()) {
	    	
	    	nodes.add(graph.addNode(regions.next().getName()));
	    	
	    }
	    
	    for(Node node : nodes) {
	    	
	    	node.setAttribute("ui.label", node.getId());
	    	node.setAttribute("ui.class", "classA");
	    }
	    
	    for (IVertex<IEntry<Integer[], Double[]>> v : imgGraph.vertices()) {
		    Vertex<IEntry<Integer[], Double[]>, Double> vertex = (Vertex<IEntry<Integer[], Double[]>, Double>) v;
		    
		    var outgoing = vertex.getOutgoing();
		    if (!outgoing.isEmpty()) {
		    	
		        for (var entry : outgoing.entrySet()) {
		            IVertex<IEntry<Integer[], Double[]>> n = entry.getKey();
		            IEdge<IEntry<Integer[], Double[]>, Double> edge = entry.getValue();
		            
		            
		            graph.addEdge(v.getName() + n.getName(), v.getName(), n.getName());
		            
		        }
		    }
		}
	    
	    return graph;
		
	}

}
