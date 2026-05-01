package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;

public class GrayBlurSobel {
	public static BufferedImage GetSobelImage(BufferedImage file) {
		return GreysScaleApplier.ConvertToGreyScale(file);
	}
}
