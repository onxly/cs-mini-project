package EXEcutioners.imagehandling;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GaussianBlurApplier {
	private static double[][] Kernal;
	
	
	public static void Alloc()
	{
		
		Kernal = new double[][] {
				{1/16.0, 2/16.0, 1/16.0},
				{2/16.0, 4/16.0, 2/16.0},
				{1/16.0, 2/16.0, 1/16.0}
		};
	}
	public static BufferedImage ConvertToBlur(BufferedImage Bimage)
	{
		Alloc();
		int Width= Bimage.getWidth();
		int Height=Bimage.getHeight();
		BufferedImage BlurredImage = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		
		for(int y =0; y<Height;y++)
		{
			for(int x=0;x<Width;x++)
			{
				double Rsum = 0;
				double Gsum = 0;
				double Bsum = 0;
				for(int j=-1;j<=1;j++)
				{
					for(int i=-1;i<=1;i++)
					{
						if(x + i >= 0 && x + i < Width && y + j >= 0 && y + j < Height)
						{
							int AroundRGB= Bimage.getRGB(x+i, y+j);
							Rsum +=((AroundRGB>>16)&0xFF)*Kernal[j+1][i+1];
							Gsum +=((AroundRGB>>8)&0xFF)*Kernal[j+1][i+1];
							Bsum +=((AroundRGB)&0xFF)*Kernal[j+1][i+1];
						}
							
					}
				}
				
				int newBlurRGB= ((int)Rsum<<16)| ((int)Gsum<<8)|(int)Bsum;
				BlurredImage.setRGB(x, y, newBlurRGB);
			}
		}
		try {
			ImageIO.write(BlurredImage, "jpg", new File("Blur.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BlurredImage;
	}
}
