package EXEcutioners.adts.graph;

import java.awt.image.BufferedImage;

import EXEcutioners.imagehandling.ImageToArray;

public class LBP {
	public static int getLBPValue(int[][] grayPixels, int x, int y) {
		
		
	    int center = grayPixels[x][y];
	    int lbpCode = 0;

	    // Define the 8 neighbors (clockwise or counter-clockwise)
	    int[][] neighbors = {
	        {x-1, y-1}, {x, y-1}, {x+1, y-1},
	        {x+1, y},   {x+1, y+1}, {x, y+1},
	        {x-1, y+1}, {x-1, y}
	    };

	    for (int i = 0; i < 8; i++) {
	        int nx = neighbors[i][0];
	        int ny = neighbors[i][1];
	        
	        // Compare neighbor to center
	        if (grayPixels[nx][ny] >= center) {
	            // Set the i-th bit to 1
	            lbpCode |= (1 << (7 - i));
	        }
	    }
	    
	    return lbpCode;
	}
}
