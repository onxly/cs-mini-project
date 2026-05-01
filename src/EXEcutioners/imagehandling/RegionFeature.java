package EXEcutioners.imagehandling;

import java.awt.image.BufferedImage;

import EXEcutioners.adts.graph.GraphNode;
import javafx.geometry.Pos;


public class RegionFeature {
	private double AvgAllRed=0;
	private double AvgAllGreen=0;
	private double AvgAllBlue=0;
	private double EdgeDensity=0.0;
	private double[] vector;
	private int x=0;
	private int y=0;
	

	public void setSobelImage(BufferedImage sobelImage) {
		SobelImage = sobelImage;
	}
	private BufferedImage Origionalimage=null;
	private BufferedImage SobelImage=null;
	
	public RegionFeature(BufferedImage Origionalimage,BufferedImage SobelImage, int x, int y){
		this.Origionalimage=Origionalimage;	
		this.SobelImage=SobelImage;	
		this.x=x;
		this.y=y;
		CalcSumOfRgb();
	}
	
	public void CalcSumOfRgb()
	{
		int pixelCount=0;
		int NumWhite=0;//the bright parts..counts them
		
		for(int y=0; y<Origionalimage.getHeight();y++)
		{
			for(int x =0;x<Origionalimage.getWidth();x++)
			{
				pixelCount++;
				int origRGB = (Origionalimage.getRGB(x, y));
				int SobelRGB = (SobelImage.getRGB(x, y));
				
				int r=(SobelRGB>>16)&0xFF;
				int g=(SobelRGB>>8)&0xFF;
				int b=(SobelRGB)&0xFF;
				
				int R=(origRGB>>16)&0xFF;
				int G=(origRGB>>8)&0xFF;
				int B=(origRGB)&0xFF;
				
				
				if(r>=128&& g>=128&& b>=128)//bright enough
				{
					NumWhite++;
				}
				
				AvgAllRed+=R;
				AvgAllGreen+=G;
				AvgAllBlue+=B;
			}
		}
		AvgAllRed=AvgAllRed/pixelCount;	
		AvgAllGreen=AvgAllGreen/pixelCount;	
		AvgAllBlue=AvgAllBlue/pixelCount;
		EdgeDensity=(double)NumWhite/(double)pixelCount;
		vector= new double[] {AvgAllRed,AvgAllGreen,AvgAllBlue,EdgeDensity};
		
	}
	
	public GraphNode getGraphNode()
	{
		Double[] Dvector = new Double[vector.length];
		
		int i =0;
		for (double d : vector) {
			Dvector[i] = (Double) d;
			i++;
		}
		String Positions=x+""+y;
		GraphNode GN = new GraphNode(Positions, Dvector);
		return GN;	
	}
	
	public double[] getVector() {
		return vector;
	}

	public void setVector(double[] vector) {
		this.vector = vector;
	}

	public BufferedImage getOrigionalimage() {
		return Origionalimage;
	}

	public void setOrigionalimage(BufferedImage origionalimage) {
		Origionalimage = origionalimage;
	}

	public BufferedImage getSobelImage() {
		return SobelImage;
	}
	public double getAvgAllRed() {
		return AvgAllRed;
	}

	public void setAvgAllRed(double avgAllRed) {
		AvgAllRed = avgAllRed;
	}

	public double getAvgAllGreen() {
		return AvgAllGreen;
	}

	public void setAvgAllGreen(double avgAllGreen) {
		AvgAllGreen = avgAllGreen;
	}

	public double getAvgAllBlue() {
		return AvgAllBlue;
	}

	public void setAvgAllBlue(double avgAllBlue) {
		AvgAllBlue = avgAllBlue;
	}

	public double getEdgeDensity() {
		return EdgeDensity;
	}
	public void setEdgeDensity(double edgeDensity) {
		EdgeDensity = edgeDensity;
		
	}
	@Override
	public String toString()
	{
		return ""+getAvgAllRed()+" "+getAvgAllGreen()+" "+getAvgAllBlue()+" "+getEdgeDensity()+"\n";
	}
	
}
