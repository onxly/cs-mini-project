package EXEcutioners.adts.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import EXEcutioners.adts.abstractClasses.HashMap;
import EXEcutioners.adts.abstractClasses.Heap;
import EXEcutioners.adts.interfaces.IVertex;

public class Classifier {
    private List<AnimalSignature> trainingData = new ArrayList<>();
    private final int K_NEIGHBORS = 3;

    public void train(AnimalSignature signature) { trainingData.add(signature); }

    public String classify(AnimalSignature unknown) {
        if (trainingData.isEmpty()) return "No Data";

        Heap<Neighbor, String> pq = new Heap<>(Comparator.comparingDouble(n -> n.distance));
        for (AnimalSignature known : trainingData) {
            pq.insert(new Neighbor(known.getSpecies(), calculateDistance(unknown, known)), known.getSpecies());
        }

        // Voting logic fix
        HashMap<String, Integer> votes = new HashMap<>();
        for (int i = 0; i < K_NEIGHBORS && !pq.isEmpty(); i++) {
            String species = pq.removeMin().getKey().species;
            Integer count = votes.get(species);
            votes.put(species, (count == null ? 0 : count) + 1);
        }

        String winner = "";
        int maxVotes = -1;
        for (String s : votes.keySet()) {
            if (votes.get(s) > maxVotes) {
                maxVotes = votes.get(s);
                winner = s;
            }
        }
        return winner;
    }

    public AnimalSignature generateSignature(ImageGraph graph) {
    	
    	int numTypes = 10;
    	
        double[] histogram = new double[numTypes];
        double totalDegree = 0;
        int count = 0;

        for (IVertex<GraphNode> v : graph.vertices()) {
            int type = predictNodeType(v.getElement()); 
            histogram[type]++;
            totalDegree += graph.inDegree(v);
            count++;
        }

        for (int i = 0; i < histogram.length; i++) histogram[i] /= count;
        return new AnimalSignature(histogram, totalDegree / count, null);
    }

    private double calculateDistance(AnimalSignature a, AnimalSignature b) {
        double sum = 0;
        for (int i = 0; i < a.getHistogramSize(); i++) sum += Math.pow(a.histogram[i] - b.histogram[i], 2);
        sum += Math.pow(a.getAverageConnectivity() - b.getAverageConnectivity(), 2) * 10;
        return Math.sqrt(sum);
    }

    private int predictNodeType(GraphNode sp) {
        double[] rgb = sp.getArrRGB();
        // Calculate brightness from 0 to 255
        double brightness = (rgb[0] + rgb[1] + rgb[2]) / 3.0;
        
        // Scale 0-255 down to 0-9
        int bin = (int)(brightness / 256.0 * 10);
        return Math.min(bin, 9); // Ensure it doesn't hit 10
    }

    private static class Neighbor {
        String species; double distance;
        Neighbor(String s, double d) { this.species = s; this.distance = d; }
    }
    
    
}