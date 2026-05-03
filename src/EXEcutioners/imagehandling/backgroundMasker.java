package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class backgroundMasker {

	public static BufferedImage MaskBackground(String file)
	{
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Width = image.getWidth();
		int Height = image.getHeight();
		int BorderCount=0;
		double AvgBorderR=0;
		double AvgBorderG=0;
		double AvgBorderB=0;
		
		for(int x =0; x<Width;x++)
		{
			AvgBorderR+=(image.getRGB(x, 0)>>16)&0xFF;//top 
			AvgBorderG+=(image.getRGB(x, 0)>>8)&0xFF;
			AvgBorderB+=(image.getRGB(x, 0))&0xFF;			
			//bottom
			AvgBorderR+=(image.getRGB(x, Height-1)>>16)&0xFF;
			AvgBorderG+=(image.getRGB(x, Height-1)>>8)&0xFF;
			AvgBorderB+=(image.getRGB(x, Height-1))&0xFF;
			BorderCount+=2;	
		}
		for(int y=1;y<Height;y++)
		{
			//left
			AvgBorderR+=(image.getRGB(0, y)>>16)&0xFF;//top 
			AvgBorderG+=(image.getRGB(0, y)>>8)&0xFF;
			AvgBorderB+=(image.getRGB(0, y))&0xFF;			
			//right
			AvgBorderR+=(image.getRGB(Width-1, y)>>16)&0xFF;
			AvgBorderG+=(image.getRGB(Width-1,y)>>8)&0xFF;
			AvgBorderB+=(image.getRGB(Width-1, y))&0xFF;
			BorderCount+=2;	
		}
		AvgBorderR= (Double)AvgBorderR/ BorderCount;
		AvgBorderG= (Double)AvgBorderG/BorderCount;
		AvgBorderB= (Double)AvgBorderB/BorderCount;
		
		
		return makeMask(Width, Height, image, AvgBorderR, AvgBorderG, AvgBorderB);
		
	}
	private static BufferedImage makeMask(int Width,int Height, BufferedImage image, double bR, double bG,double bB)
	{
		BufferedImage mask = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		
		for(int y= 0; y<Height; y++)
		{
			for(int x=0; x<Width;x++)
			{
				int rgb = image.getRGB(x, y);
				int r = (rgb>>16)&0xFF;
				int g = (rgb>>8)&0xFF;
				int b = (rgb)&0xFF;
				
				double difference = Math.abs(r-bR)+Math.abs(g-bG)+Math.abs(b-bB);
				mask.setRGB(x, y, difference<80? 0x000000 : rgb);
			}
		}
		return mask;
	}
}
