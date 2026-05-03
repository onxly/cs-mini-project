package EXEcutioners.imagehandling;

import java.awt.Color;
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
	
	private double normR=0;
	private double normG=0;
	private double AvgHue=0;
	private double AvgSat=0;
	private double AvgBright=0;
	private double VarR=0;
	private double VarG=0;
	private double VarB=0;

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
		//int NumWhite=0;//the bright parts..counts them
		double sumH=0, sumS=0, sumBri=0, sumEdge=0;
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
				
				AvgAllRed+=R;
				AvgAllGreen+=G;
				AvgAllBlue+=B;
				
				float[] hsb = Color.RGBtoHSB(R, G, B, null);
				sumH+=hsb[0];
				sumS+=hsb[1];
				sumBri+=hsb[2];
				sumEdge+=Math.sqrt(r*r+g*g+b*b);
				
			}
		}
		AvgAllRed=AvgAllRed/pixelCount;	
		AvgAllGreen=AvgAllGreen/pixelCount;	
		AvgAllBlue=AvgAllBlue/pixelCount;
		AvgHue=sumH/pixelCount;
		AvgSat=sumS/pixelCount;
		AvgBright=sumBri/pixelCount;
		
		double sumAvg= AvgAllRed+AvgAllGreen+AvgAllBlue;
		normR= sumAvg>0 ? (AvgAllRed/sumAvg) : 0.38; 
		normG= sumAvg>0 ? (AvgAllGreen/sumAvg) : 0.38; 
		
		EdgeDensity=(double)sumEdge/(pixelCount*Math.sqrt(3)*255);
		calcVariance(pixelCount);
		
		
	}
	public void calcVariance(int pixelCount)
	{
		double SumSqr_R=0;
		double SumSqr_G=0;
		double SumSqr_B=0;
		for(int y=0;y<Origionalimage.getHeight();y++)
		{
			for(int x=0;x<Origionalimage.getWidth();x++)
			{
				int origRGB=Origionalimage.getRGB(x, y);
				int R =(origRGB>>16)&0xFF;
				int G =(origRGB>>8)&0xFF;
				int B =(origRGB)&0xFF;
				
				SumSqr_R += (R-AvgAllRed)*(R-AvgAllRed);
				SumSqr_G += (G-AvgAllGreen)*(G-AvgAllGreen);
				SumSqr_B += (B-AvgAllBlue)*(B-AvgAllBlue);
				
				
			}
		}
		
		double maxvar=127.5*127.5;
		VarR=(SumSqr_R /pixelCount)/maxvar;
		VarG=(SumSqr_G /pixelCount)/maxvar;
		VarB=(SumSqr_B /pixelCount)/maxvar;
		 
		vector= new double[] {normR,normG,AvgHue,AvgSat,AvgBright,VarR,VarG,VarB,EdgeDensity};
	}
	
	
	
	public GraphNode getGraphNode()
	{
		Double[] Dvector = new Double[vector.length];
		
		int i =0;
		for (double d : vector) {
			Dvector[i] = (Double) d;
			i++;
		}
		
		Integer[] INTPos = new Integer[2];
		INTPos[0] = x;
		INTPos[1] = y;
		
		
		GraphNode GN = new GraphNode(INTPos, Dvector);
		
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
