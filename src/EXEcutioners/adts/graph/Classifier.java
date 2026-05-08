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
            pq.insert(new Neighbor(known.species, calculateDistance(unknown, known)), known.species);
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

    // signature generation now uses the ImageGraph's structure
    public AnimalSignature generateSignature(ImageGraph graph, int numTypes) {
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
        for (int i = 0; i < a.histogram.length; i++) sum += Math.pow(a.histogram[i] - b.histogram[i], 2);
        sum += Math.pow(a.averageConnectivity - b.averageConnectivity, 2) * 10;
        return Math.sqrt(sum);
    }

    private int predictNodeType(GraphNode sp) {
        // Logic: 0 = Fur/Texture, 1 = Background/Grass
        return (sp.getArrRGB()[1] > sp.getArrRGB()[0] && sp.getArrRGB()[1] > sp.getArrRGB()[2]) ? 1 : 0;
    }

    private static class Neighbor {
        String species; double distance;
        Neighbor(String s, double d) { this.species = s; this.distance = d; }
    }
}