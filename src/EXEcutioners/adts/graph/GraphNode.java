package EXEcutioners.adts.graph;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphNode implements Serializable{
    private int id;
    private double[] arrRGB;
    private double[] center;
    private List<Point> pixels;
    private int lbpType;

    public GraphNode(int iId, int centX, int centY, int rgb) {
        this.id = iId;
        this.arrRGB = new double[3];
        this.arrRGB[0] = (rgb >> 16) & 0xFF;
        this.arrRGB[1] = (rgb >> 8) & 0xFF;
        this.arrRGB[2] = rgb & 0xFF;
        this.center = new double[]{centX, centY};
        this.pixels = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public double[] getArrRGB() { return arrRGB; }
    public double[] getCenter() { return center; }
    public List<Point> getPixels() { return pixels; }
    public int getLbpType() { return lbpType; }
    public void setLbpType(int type) { this.lbpType = type; }

    /**
     * Combines Color (RGB) into a feature vector for distance calculations.
     */
    public Double[] getFeatures() {
        return new Double[]{arrRGB[0], arrRGB[1], arrRGB[2]};
    }
}