package EXEcutioners.adts.graph;


import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;

public class GraphSegmenter implements Serializable{
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[][] getLabels() {
		return labels;
	}

	public void setLabels(int[][] labels) {
		this.labels = labels;
	}

	public double[][] getDistances() {
		return distances;
	}

	public void setDistances(double[][] distances) {
		this.distances = distances;
	}

	private int width, height;
    private int[][] labels; 
    private double[][] distances; 
    private int lbpType;


    
    public List<GraphNode> segment(int[][] pixelData, int regNum, double dblComp) {
        this.width = pixelData.length;
        this.height = pixelData[0].length;
        this.labels = new int[width][height];
        this.distances = new double[width][height];
        
        int gridInterval = (int) Math.sqrt((width * height) / regNum); // Grid interval
        List<GraphNode> seeds = initializeSeeds(pixelData, gridInterval);

        for (int i = 0; i < 10; i++) {
            for (double[] row : distances) {
            	Arrays.fill(row, Double.MAX_VALUE);
            }

            for (GraphNode seed : seeds) {
                int startX = Math.max(0, (int)seed.getCenter()[0] - gridInterval);
                int endX = Math.min(width, (int)seed.getCenter()[0] + gridInterval);
                int startY = Math.max(0, (int)seed.getCenter()[1] - gridInterval);
                int endY = Math.min(height, (int)seed.getCenter()[1] + gridInterval);
                seed.setLbpType(LBP.getLBPValue(pixelData, (int)seed.getCenter()[0], (int)seed.getCenter()[1]));
                for (int x = startX; x < endX; x++) {
                    for (int y = startY; y < endY; y++) {
                        double dist = calculate5DDist(pixelData[x][y], x, y, seed, gridInterval, dblComp);
                        if (dist < distances[x][y]) {
                            distances[x][y] = dist;
                            labels[x][y] = seed.getId(); // Changed from getElement()
                        }
                    }
                }
            }
            updateSeeds(seeds, pixelData); // Move seeds to center of their assigned pixels
        }
        return seeds;
    }

    private double calculate5DDist(int rgb, int x, int y, GraphNode node, int gridInterval, double dblComp) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        double dColor = Math.sqrt(Math.pow(red - node.getArrRGB()[0], 2) + Math.pow(green - node.getArrRGB()[1], 2) + Math.pow(blue - node.getArrRGB()[2], 2));
        double dSpace = Math.sqrt(Math.pow(x - node.getCenter()[0], 2) + Math.pow(y - node.getCenter()[1], 2));
        
        return Math.sqrt(Math.pow(dColor, 2) + Math.pow(dSpace / gridInterval, 2) * Math.pow(dblComp, 2));
    }
    
    
    private List<GraphNode> initializeSeeds(int[][] pixelData, int S) {
        List<GraphNode> seeds = new ArrayList<>();
        int idCounter = 0;

        for (int x = S / 2; x < width; x += S) {
            for (int y = S / 2; y < height; y += S) {
                
                Point newPoint = findLowestGradient(pixelData, x, y);
                
                int rgb = pixelData[newPoint.x][newPoint.y];
                seeds.add(new GraphNode(idCounter++, newPoint.x, newPoint.y, rgb));
            }
        }
        return seeds;
    }
    
    private Point findLowestGradient(int[][] pixelData, int x, int y) {
        double minGrad = Double.MAX_VALUE;
        Point bestPoint = new Point(x, y);

        // Look at a 3x3 neighborhood
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > 0 && i < width - 1 && j > 0 && j < height - 1) {
                    // Simple gradient: Difference between right neighbor and bottom neighbor
                    double grad = Math.abs(getGray(pixelData[i+1][j]) - getGray(pixelData[i][j])) +
                                 Math.abs(getGray(pixelData[i][j+1]) - getGray(pixelData[i][j]));
                    
                    if (grad < minGrad) {
                        minGrad = grad;
                        bestPoint = new Point(i, j);
                    }
                }
            }
        }
        return bestPoint;
    }

    private double getGray(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return 0.299 * r + 0.587 * g + 0.114 * b; // Standard grayscale conversion
    }
    
    private void updateSeeds(List<GraphNode> seeds, int[][] pixelData) {
        // 1. Create accumulators for each seed
        double[] sumR = new double[seeds.size()];
        double[] sumG = new double[seeds.size()];
        double[] sumB = new double[seeds.size()];
        double[] sumX = new double[seeds.size()];
        double[] sumY = new double[seeds.size()];
        int[] pixelCount = new int[seeds.size()];

        // 2. Sum up the values of all pixels assigned to each seed
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int id = labels[x][y];
                if (id != -1) {
                    int rgb = pixelData[x][y];
                    sumR[id] += (rgb >> 16) & 0xFF;
                    sumG[id] += (rgb >> 8) & 0xFF;
                    sumB[id] += (rgb >> 0) & 0xFF;
                    sumX[id] += x;
                    sumY[id] += y;
                    pixelCount[id]++;
                }
            }
        }

        for (GraphNode seed : seeds) {
            int id = seed.getId(); // Changed from getElement()
            if (pixelCount[id] > 0) {
                seed.getArrRGB()[0] = sumR[id] / pixelCount[id];
                // ... rest of the averaging logic ...
            }
        }

        // Inside segment method:
        
    }
    
}
