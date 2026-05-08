package EXEcutioners.adts.graph;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;
import EXEcutioners.adts.abstractClasses.MapGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IVertex;

public class ImageGraph extends MapGraph<GraphNode, Double> implements Serializable {
    private String animalName;
    private GraphSegmenter seg;
    
    public ImageGraph() {
        super(false); // Undirected
        this.seg = new GraphSegmenter();
    }

    public void buildGraph(int[][] pixels, int regNum, double comp) {
        // 1. Run SLIC segmentation
        List<GraphNode> nodes = seg.segment(pixels, regNum, comp);
        Map<Integer, IVertex<GraphNode>> vertexMap = new HashMap<>();
        
        // 2. Insert vertices into MapGraph
        for (GraphNode node : nodes) {
            IVertex<GraphNode> v = super.insertVertex(node);
            vertexMap.put(node.getId(), v);
        }

        // 3. Scan pixel boundaries to build edges (The "Skeleton")
        int[][] labels = seg.getLabels();
        for (int x = 0; x < seg.getWidth(); x++) {
            for (int y = 0; y < seg.getHeight(); y++) {
                int curr = labels[x][y];
                if (x + 1 < seg.getWidth() && curr != labels[x + 1][y]) 
                    link(vertexMap.get(curr), vertexMap.get(labels[x + 1][y]));
                if (y + 1 < seg.getHeight() && curr != labels[x][y + 1]) 
                    link(vertexMap.get(curr), vertexMap.get(labels[x][y + 1]));
            }
        }
    }

    private void link(IVertex<GraphNode> u, IVertex<GraphNode> v) {
        if (u != null && v != null && getEdge(u, v) == null) {
            double dist = calculateDistance(u.getElement().getFeatures(), v.getElement().getFeatures());
            insertEdge(u, v, dist);
        }
    }

    private double calculateDistance(Double[] f1, Double[] f2) {
        double sum = 0;
        for (int i = 0; i < f1.length; i++) sum += Math.pow(f1[i] - f2[i], 2);
        return Math.sqrt(sum);
    }
    
    public void setAnimalName(String name) {
    	this.animalName = name;
    }
    
    public String getAnimalName() {
    	return this.animalName;
    }
}