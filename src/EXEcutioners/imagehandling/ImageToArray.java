package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;

public class ImageToArray {
	
	/**
     * Converts a BufferedImage into a 2D int array.
     * Each int is a packed ARGB value.
     */
    public static int[][] getPixels2D(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // getRGB returns the pixel in Type_Int_ARGB
                pixels[y][x] = image.getRGB(x, y);
            }
        }

        return pixels;
    }

}

