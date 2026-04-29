package EXEcutioners.imagehandling;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class croppedImg {
	private int secWidth,secHeight;
	BufferedImage crop;
	
	public croppedImg(int secWidth,int secHeight)
	{
		this.secHeight=secHeight;
		this.secWidth=secWidth;
		
		this.crop=new BufferedImage(secWidth, secHeight,	BufferedImage.TYPE_INT_RGB);
	}
	
	public BufferedImage retimage(BufferedImage img2crop,int startX, int startY,int endX,int endY)
	{
		crop=img2crop.getSubimage(startX, startY, endX, endY);

		return crop;
	}

}
