package EXEcutioners.adts.graph;

public class AnimalSignature {
    String species; // Only for training data
    double[] histogram; // Frequency of node types
    double averageConnectivity; // Average degree of nodes

    public AnimalSignature(double[] histogram, double avgConnectivity, String species) {
        this.histogram = histogram;
        this.averageConnectivity = avgConnectivity;
        this.species = species;
    }
}