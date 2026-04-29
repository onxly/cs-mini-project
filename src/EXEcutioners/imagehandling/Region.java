package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;

public class Region {
	int x;
	int y;
	int width;
	int height;
	BufferedImage image;
	
	public Region(BufferedImage image,int x,int y,int width,int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.image=image;
	}
}
