package EXEcutioners.imagehandling;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageProcessor {
	
	public ImageProcessor()
	{
		
	}
	
	public BufferedImage cropImage(BufferedImage img2crop,int startX, int startY,int endX,int endY)
	{
		BufferedImage crop=img2crop.getSubimage(startX, startY, endX, endY);
		return crop;
	}
	
	
	public static RegionList processImg(String imageName)
	{
		try {
			
			BufferedImage image= ImageIO.read(new File(imageName));
			
			int secHeight=image.getHeight()/4;
			int secWidth=image.getWidth()/4;
			
			RegionList reglist=new RegionList();
			
			for(int c=0;c<(secWidth*4);c+=secWidth)
			{
				for(int r=0;r<(secHeight*4);r+=secHeight)
				{
					ImageProcessor img=new ImageProcessor();
					BufferedImage croppedimg=img.cropImage(image, c,r,secWidth, secHeight);
					Region reg=new Region(croppedimg, c,r,secWidth, secHeight);
					reglist.addRegion(reg);
				}
			}
						
			/*for testing purposes
			 * int i=0;
			for(Region img:reglist)
			{
				++i;
				ImageIO.write(img.image, "png", new File(i+"output.png"));
			}*/

			System.out.println("Image cropping complete");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
