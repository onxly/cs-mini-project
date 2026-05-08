package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageBlurrer {

	public static BufferedImage applyBlur(BufferedImage img, int radius) throws IOException {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        float[][] kernel = createGaussianKernel(radius);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                float rSum = 0, gSum = 0, bSum = 0;

                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                        
                        int ix = Math.min(Math.max(x + kx, 0), width - 1);
                        int iy = Math.min(Math.max(y + ky, 0), height - 1);

                        int pixel = img.getRGB(ix, iy);
                        float weight = kernel[ky + radius][kx + radius];

                        rSum += ((pixel >> 16) & 0xFF) * weight;
                        gSum += ((pixel >> 8) & 0xFF) * weight;
                        bSum += (pixel & 0xFF) * weight;
                    }
                }

                int blurredPixel = (Math.round(rSum) << 16) | (Math.round(gSum) << 8) | Math.round(bSum);
                result.setRGB(x, y, blurredPixel);
            }
        }

        return result;
    }

    private static float[][] createGaussianKernel(int radius) {
        int size = radius * 2 + 1;
        float[][] kernel = new float[size][size];
        float sigma = Math.max(radius / 2.0f, 1.0f);
        float sum = 0;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                float val = (float) (Math.exp(-(x * x + y * y) / (2 * sigma * sigma)));
                kernel[y + radius][x + radius] = val;
                sum += val;
            }
        }
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kernel[i][j] /= sum;
            }
        }
        return kernel;
    }

}