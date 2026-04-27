package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SobelOperator {

	private static double[][] XGradient;
	private static double[][] YGradient;
	
	public static void Alloc()
	{
		XGradient = new double[][] {
			{-1,0,1},
			{-2,0,2},
			{-1,0,1}
		};
		YGradient = new double[][] {
			{-1,-2,-1},
			{0,0,0},
			{1,2,1}
		};
	}
	
	public static BufferedImage SobelOperatorApplier(BufferedImage blurredImage)
	{
		Alloc();
		int Width = blurredImage.getWidth();
		int Height = blurredImage.getHeight();
		BufferedImage Sobelimage = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		
			for(int y =0; y<Height;y++)
			{
				for(int x=0;x<Width;x++)
				{
					double XGrR = 0;
					double XGrG = 0;
					double XGrB = 0;
					
					
					double YGrR = 0;
					double YGrG = 0;
					double YGrB = 0;
					
					
					for(int j=-1;j<=1;j++)
					{
						for(int i=-1;i<=1;i++)
						{
							if(x + i >= 0 && x + i < Width && y + j >= 0 && y + j < Height)
							{
								int neighborRGB= blurredImage.getRGB(x+i, y+j);
								int r = (neighborRGB>>16)& 0xFF;
								int g = (neighborRGB>>8)& 0xFF;
								int b = (neighborRGB)& 0xFF;
								
								//the reds
								XGrR+=r*XGradient[j+1][i+1];
								YGrR+=r*YGradient[j+1][i+1];
								
								//the greens
								XGrG+=g*XGradient[j+1][i+1];
								YGrG+=g*YGradient[j+1][i+1];
								
								//the blues
								XGrB+=b*XGradient[j+1][i+1];
								YGrB+=b*YGradient[j+1][i+1];
							
							}
								
						}
					}
						
					int r = (int) Math.min(255,Math.sqrt(XGrR*XGrR+YGrR*YGrR));
					int g = (int) Math.min(255,Math.sqrt(XGrG*XGrG+YGrG*YGrG));
					int b = (int) Math.min(255,Math.sqrt(XGrB*XGrB+YGrB*YGrB));
					int SobelRGB = (r<<16)|(g<<8)|b;

					Sobelimage.setRGB(x, y, SobelRGB);
				}
			}
			try {
				ImageIO.write(Sobelimage, "jpg", new File("Sobelimage.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Sobelimage;
		}
		
}

