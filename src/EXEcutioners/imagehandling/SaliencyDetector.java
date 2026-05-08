package EXEcutioners.imagehandling;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SaliencyDetector {
	
    public static BufferedImage computeSaliency(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();

        float[][] L = new float[h][w];
        float[][] a = new float[h][w];
        float[][] b = new float[h][w];
        rgbToLab(src, L, a, b);

        double meanL = 0, meanA = 0, meanB = 0;
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                meanL += L[y][x]; meanA += a[y][x]; meanB += b[y][x];
            }
        meanL /= (w * h); meanA /= (w * h); meanB /= (w * h);

        float[][] saliency = new float[h][w];
        float maxS = 0;
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                float dL = L[y][x] - (float) meanL;
                float da = a[y][x] - (float) meanA;
                float db = b[y][x] - (float) meanB;
                saliency[y][x] = dL * dL + da * da + db * db;
                if (saliency[y][x] > maxS) maxS = saliency[y][x];
            }


        saliency = gaussianBlur(saliency, w, h, 3, 1.0f);

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        float minS = Float.MAX_VALUE;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (saliency[y][x] < minS) minS = saliency[y][x];
            }
        }

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int v = Math.round(255 * (saliency[y][x] - minS) / (maxS - minS));
                v = Math.min(255, Math.max(0, v));
                result.setRGB(x, y, new Color(v, v, v).getRGB());
            }
        
        return result;
    }


    private static void rgbToLab(BufferedImage img, float[][] L, float[][] a, float[][] b) {
        int w = img.getWidth(), h = img.getHeight();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                Color c = new Color(img.getRGB(x, y));
                float[] lab = rgbToLabPixel(c.getRed(), c.getGreen(), c.getBlue());
                L[y][x] = lab[0]; a[y][x] = lab[1]; b[y][x] = lab[2];
            }
    }

    private static float[] rgbToLabPixel(int r, int g, int b) {
        double rr = pivotRgb(r / 255.0);
        double gg = pivotRgb(g / 255.0);
        double bb = pivotRgb(b / 255.0);

        double X = rr * 0.4124 + gg * 0.3576 + bb * 0.1805;
        double Y = rr * 0.2126 + gg * 0.7152 + bb * 0.0722;
        double Z = rr * 0.0193 + gg * 0.1192 + bb * 0.9505;

        double fx = pivotXyz(X / 0.95047);
        double fy = pivotXyz(Y);
        double fz = pivotXyz(Z / 1.08883);

        return new float[]{
            (float) (116 * fy - 16),
            (float) (500 * (fx - fy)),
            (float) (200 * (fy - fz))
        };
    }

    private static double pivotRgb(double n) {
        return n > 0.04045 ? Math.pow((n + 0.055) / 1.055, 2.4) : n / 12.92;
    }

    private static double pivotXyz(double n) {
        return n > 0.008856 ? Math.cbrt(n) : 7.787 * n + 16.0 / 116.0;
    }


    private static float[][] gaussianBlur(float[][] src, int w, int h, int radius, float sigma) {
        float[] kernel = buildKernel(radius, sigma);
        float[][] tmp = new float[h][w];
        float[][] out = new float[h][w];

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                float sum = 0, weight = 0;
                for (int k = -radius; k <= radius; k++) {
                    int xx = Math.min(Math.max(x + k, 0), w - 1);
                    sum += src[y][xx] * kernel[k + radius];
                    weight += kernel[k + radius];
                }
                tmp[y][x] = sum / weight;
            }

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                float sum = 0, weight = 0;
                for (int k = -radius; k <= radius; k++) {
                    int yy = Math.min(Math.max(y + k, 0), h - 1);
                    sum += tmp[yy][x] * kernel[k + radius];
                    weight += kernel[k + radius];
                }
                out[y][x] = sum / weight;
            }
        return out;
    }

    private static float[] buildKernel(int radius, float sigma) {
        float[] k = new float[2 * radius + 1];
        for (int i = -radius; i <= radius; i++)
            k[i + radius] = (float) Math.exp(-(i * i) / (2 * sigma * sigma));
        return k;
    }

}

