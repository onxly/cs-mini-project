package EXEcutioners.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import EXEcutioners.adts.graph.AnimalSignature;
import EXEcutioners.adts.graph.Classifier;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.imagehandling.ImageBlurrer;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.ImageRescaler;
import EXEcutioners.imagehandling.ImageToArray;
import EXEcutioners.imagehandling.SaliencyDetector;
import EXEcutioners.imagehandling.backgroundMasker;


public class GraphFileHelper {
	
    public static void saveGraph(ImageGraph graph, String directory, String fileName) {
        File folder = new File(directory);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName);

        try (FileOutputStream fos = new FileOutputStream(file);
        	BufferedOutputStream bos = new BufferedOutputStream(fos);
        	ObjectOutputStream out = new ObjectOutputStream(bos)){
        		
            out.writeObject(graph);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void buildAndSaveGraphs(String directory) {

    	System.out.println("Looking for: " + directory);
    	
        File folder = new File(directory);

        if (!folder.exists() || !folder.isDirectory()) {
        	System.out.println("No folder named " + directory);
            return;
        }

        System.out.println("Found: " + directory);
        
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
            	
            	System.out.println("Found Image: " + file.getName());
            	try {
            	BufferedImage img = ImageIO.read(file);
    			BufferedImage imgScaled = ImageRescaler.rescale(img, 300, 300);
    			BufferedImage imgBlurred = ImageBlurrer.applyBlur(imgScaled, 10);
    			BufferedImage imgFocused = SaliencyDetector.computeSaliency(imgBlurred);
    			int[][] pixels = ImageToArray.getPixels2D(imgFocused);
    			ImageGraph graph = new ImageGraph();
    			graph.buildGraph(pixels, 100, 10);
    			String fileName = file.getName().split("\\.")[0];
    			
    			graph.setAnimalName(fileName.substring(0, fileName.length() - 1));
    			
    			saveGraph(graph, "graphs", file.getName().split("\\.")[0] + ".dat");
    			
            	} catch(IOException ex) {
            		ex.printStackTrace();
            	}
    			
            	
            	
            	/*LinkedRegionalList ImageRegionList = new LinkedRegionalList();
            	ImageRegionList.AddImageRegions(ImageProcessor.processImg(backgroundMasker.MaskBackground(file.getAbsolutePath())));
            	
            	Iterator<Iterable<GraphNode>> iteList = ImageRegionList.getGraphNodeList().iterator();
        		Iterable<GraphNode> listofRegions = null;
        		ImageGraph IG = new ImageGraph(3);
        		
        		if (iteList.hasNext()) {
        			listofRegions = iteList.next();
        		}
        		
        		IG.buildGraph(listofRegions);
        		
        		String fileName = file.getName().split("\\.")[0];
        		
        		String name = fileName.substring(0, fileName.length() - 1);
        		
        		IG.setAnimalName(name);
        		
        		System.out.println("Saved a: " + name);

                saveGraph(IG, "graphs", fileName + ".dat");*/
            }else {
            	System.out.println("This is not a file: " + file.getName());
            }
            
            
        }
    }


    public static ImageGraph readGraph(File file) {
        try (FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream in = new ObjectInputStream(bis)) {
        	
            Object obj = in.readObject();

            if (obj instanceof ImageGraph) {
                return (ImageGraph) obj;
            } else {
                System.err.println("File does not contain a Graph: " + file.getName());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<ImageGraph> readGraphsFromDirectory(String directory) {
    	ArrayList<ImageGraph> graphs = new ArrayList<ImageGraph>();

        File folder = new File(directory);

        if (!folder.exists() || !folder.isDirectory()) {
        	System.err.println("No Folder named: " + directory);
            return null;
        }

        File[] files = folder.listFiles();

        if (files == null) {
        	System.err.println("No Files in: " + directory);
            return null;
        }

        for (File file : files) {
            if (file.isFile()) {
            	//System.out.println("File Found: " + file.getName());
            	
            	ImageGraph graph = readGraph(file);

                if (graph != null) {
                	//System.out.println("Adding Graph...");
                    graphs.add(graph);
                    //System.out.println("Graph Added!!!");
                }
            }
        }

        return graphs;
    }

}
