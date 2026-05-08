/*package EXEcutioners.algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.Viewer;

import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphVisualiser {
	
	private Stage graphStage;
	private FxViewer viewer;
	private FxDefaultView graphView;
	
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
	
	public void drawGraph(ArrayList<PQEntry<String, ImageGraph>> imgGraphs) {
		
		ArrayList<Graph> graphs = new ArrayList<>();
		
		for(PQEntry<String, ImageGraph> entry : imgGraphs) {
			
			Graph graph = new SingleGraph(entry.getKey());
			
			ImageGraph imgGraph = entry.getValue();
		    
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
			    	
			        for (var e : outgoing.entrySet()) {
			            IVertex<IEntry<Integer[], Double[]>> n = e.getKey();
			            IEdge<IEntry<Integer[], Double[]>, Double> edge = e.getValue();
			            
			            
			            graph.addEdge(v.getName() + n.getName(), v.getName(), n.getName());
			            
			        }
			    }
			}
		    
		    graphs.add(graph);
			
		}
	    
	    openGraphWindow(graphs);
	    		
	}
	
	private void openGraphWindow(ArrayList<Graph> graphs) {

	    if (graphStage != null && graphStage.isShowing()) {
	        graphStage.toFront();
	        return;
	    }

	    Graph combinedGraph = new SingleGraph("Combined Graph");

	    // Apply styles to the combined graph itself
	    combinedGraph.setAttribute("ui.stylesheet", styles);
	    combinedGraph.setAttribute("ui.quality");
	    combinedGraph.setAttribute("ui.antialias");

	    combinedGraph.setStrict(false);
	    combinedGraph.setAutoCreate(true);

	    int graphIndex = 0;

	    for (Graph g : graphs) {

	        String prefix = "g" + graphIndex + "_";

	        for (Node node : g) {

	            String newNodeId = prefix + node.getId();

	            if (combinedGraph.getNode(newNodeId) == null) {

	                Node newNode = combinedGraph.addNode(newNodeId);

	                newNode.setAttribute("ui.label", node.getId());

	                // Copy node class, for example classA, classB, classC
	                if (node.hasAttribute("ui.class")) {
	                    newNode.setAttribute("ui.class", node.getAttribute("ui.class"));
	                }

	                // Copy inline style if you ever use ui.style
	                if (node.hasAttribute("ui.style")) {
	                    newNode.setAttribute("ui.style", node.getAttribute("ui.style"));
	                }
	            }
	        }

	        for (Edge edge : g.edges().toList()) {

	            String newEdgeId = prefix + edge.getId();

	            String sourceId = prefix + edge.getSourceNode().getId();
	            String targetId = prefix + edge.getTargetNode().getId();

	            if (combinedGraph.getEdge(newEdgeId) == null) {

	                Edge newEdge = combinedGraph.addEdge(
	                    newEdgeId,
	                    sourceId,
	                    targetId,
	                    edge.isDirected()
	                );

	                if (edge.hasAttribute("ui.class")) {
	                    newEdge.setAttribute("ui.class", edge.getAttribute("ui.class"));
	                }

	                if (edge.hasAttribute("ui.style")) {
	                    newEdge.setAttribute("ui.style", edge.getAttribute("ui.style"));
	                }

	                if (edge.hasAttribute("ui.label")) {
	                    newEdge.setAttribute("ui.label", edge.getAttribute("ui.label"));
	                }
	            }
	        }

	        graphIndex++;
	    }

	    FxViewer viewer = new FxViewer(
	        combinedGraph,
	        Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD
	    );

	    viewer.enableAutoLayout();

	    FxDefaultView graphView =
	        (FxDefaultView) viewer.addDefaultView(false);

	    BorderPane root = new BorderPane();
	    root.setCenter(graphView);

	    Scene scene = new Scene(root, 800, 600);

	    graphStage = new Stage();
	    graphStage.setTitle("Graph View");
	    graphStage.setScene(scene);

	    graphStage.setOnCloseRequest(e -> {
	        viewer.close();
	        graphStage = null;
	    });

	    graphStage.show();
	}
	
	private void closeGraphWindow(FxViewer viewer, Stage graphStage, FxDefaultView graphView) {
	    if (viewer != null) {
	        viewer.disableAutoLayout();
	        viewer.close();
	        viewer = null;
	    }

	    if (graphStage != null) {
	        graphStage.close();
	        graphStage = null;
	    }

	    graphView = null;
	}

}*/
