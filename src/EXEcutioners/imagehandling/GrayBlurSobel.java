package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;

public class GrayBlurSobel {
	public static BufferedImage GetSobelImage(String filename) {
		return GreysScaleApplier.ConvertToGreyScale(filename);
	}
}
