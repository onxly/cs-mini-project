package EXEcutioners.adts.graph;

public class AnimalSignature {
    private String species;
    public double[] histogram;
    private double averageConnectivity; 

    public AnimalSignature(double[] histogram, double avgConnectivity, String species) {
        this.histogram = histogram;
        this.averageConnectivity = avgConnectivity;
        this.species = species;
    }
    
    public int getHistogramSize() {
    	if (histogram != null) {
    		return histogram.length;
    	} else {
    		return 0;
    	}
    }

	public double[] getHistogram() {
		return histogram;
	}

	public void setHistogram(double[] histogram) {
		this.histogram = histogram;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public double getAverageConnectivity() {
		return averageConnectivity;
	}

	public void setAverageConnectivity(double averageConnectivity) {
		this.averageConnectivity = averageConnectivity;
	}
}