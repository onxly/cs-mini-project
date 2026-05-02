package EXEcutioners.imagehandling;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreysScaleApplier {
	public static BufferedImage ConvertToGreyScale(BufferedImage file)
	{
	
		BufferedImage GrayScaleImage = null;
		try {
			BufferedImage OrigionalImage = file;
			int Width = OrigionalImage.getWidth();
			int Height = OrigionalImage.getHeight();
			
			GrayScaleImage=new BufferedImage(Width, Height, BufferedImage.TYPE_BYTE_GRAY);
			for(int y = 0 ; y<Height; y++)
			{
				for(int x = 0 ; x<Width;x++)
				{
					int rgb = OrigionalImage.getRGB(x, y);
					int r = (rgb>>16) & 0xFF;
					int g = (rgb>>8) & 0xFF;
					int b = rgb &0xFF;
					int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);//Luminance formula for accuracy
					int grayRGB = (gray<<16)|(gray<<8)|gray;
					GrayScaleImage.setRGB(x, y, grayRGB);
				}
			}
			//ImageIO.write(GrayScaleImage,"jpg",new File("Gray.jpg"));
			SobelOperator.SobelOperatorApplier(GaussianBlurApplier.ConvertToBlur(GrayScaleImage));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Something went wrong while trying to read the image");
		}
		return SobelOperator.SobelOperatorApplier(GaussianBlurApplier.ConvertToBlur(GrayScaleImage));
	}
}
