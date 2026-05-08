package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageRescaler {


	public static BufferedImage rescale(BufferedImage img, int newW, int newH) throws IOException {
        int oldW = img.getWidth();
        int oldH = img.getHeight();
        BufferedImage result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
     
        float xRatio = (float) (oldW - 1) / newW;
        float yRatio = (float) (oldH - 1) / newH;

        for (int i = 0; i < newH; i++) {
            for (int j = 0; j < newW; j++) {
                int x = (int) (xRatio * j);
                int y = (int) (yRatio * i);

                float xDiff = (xRatio * j) - x;
                float yDiff = (yRatio * i) - y;

                int pA = img.getRGB(x, y);
                int pB = img.getRGB(x + 1, y);
                int pC = img.getRGB(x, y + 1);
                int pD = img.getRGB(x + 1, y + 1);

                int red = interpolate(pA >> 16, pB >> 16, pC >> 16, pD >> 16, xDiff, yDiff);
                int green = interpolate(pA >> 8, pB >> 8, pC >> 8, pD >> 8, xDiff, yDiff);
                int blue = interpolate(pA, pB, pC, pD, xDiff, yDiff);

                int rgb = (red << 16) | (green << 8) | blue;
                result.setRGB(j, i, rgb);
            }
        }

        return result;
    }

    private static int interpolate(int a, int b, int c, int d, float xDiff, float yDiff) {
        a &= 0xFF; b &= 0xFF; c &= 0xFF; d &= 0xFF;

        float top = a * (1 - xDiff) + b * xDiff;
        float bottom = c * (1 - xDiff) + d * xDiff;
        
        return (int) (top * (1 - yDiff) + bottom * yDiff);
    }
}