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
import java.util.Collections;
import java.util.Iterator;

import javax.imageio.ImageIO;

import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.graph.AnimalSignature;
import EXEcutioners.adts.graph.Classifier;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEntry;
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
        File folder = new File(directory);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory: " + directory);
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // Recurse into subfolder
                buildAndSaveGraphs(file.getAbsolutePath());
            } else if (file.isFile()) {
                // Process image and pass the name of the parent folder
                String parentFolderName = file.getParentFile().getName();
                processImageFile(file, parentFolderName);
            }
        }
    }

    private static void processImageFile(File file, String folderName) {
        // Basic filter to ensure we are reading images
        String name = file.getName().toLowerCase();
        if (!(name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"))) {
            return; 
        }

        System.out.println("Processing: " + file.getName() + " [Category: " + folderName + "]");
        
        try {
            BufferedImage img = ImageIO.read(file);
            if (img == null) return;

            BufferedImage imgScaled = ImageRescaler.rescale(img, 300, 300);
            BufferedImage imgBlurred = ImageBlurrer.applyBlur(imgScaled, 10);
            BufferedImage imgFocused = SaliencyDetector.computeSaliency(imgBlurred);
            int[][] pixels = ImageToArray.getPixels2D(imgFocused);

            ImageGraph graph = new ImageGraph();
            graph.buildGraph(pixels, 100, 10);
            
            // Use the folder name as the Animal Name
            graph.setAnimalName(folderName.trim());
            
            // Save the .dat file using the original filename
            String fileNameOnly = file.getName().split("\\.")[0];
            saveGraph(graph, "graphs", fileNameOnly + ".dat");
            
        } catch (IOException ex) {
            ex.printStackTrace();
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
    
    public static ArrayList<IEntry<String, double[]>> readHistogramsFromDirectory(String directory){
    	
    	Classifier classify = new Classifier();
    	
    	ArrayList<IEntry<String, double[]>> histoGrams = new ArrayList<IEntry<String, double[]>>();

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
                    AnimalSignature sig = classify.generateSignature(graph);
                    
                    IEntry<String, double[]> entry = new PQEntry<String, double[]>(graph.getAnimalName().trim(), sig.getHistogram());
                    //System.out.println("Graph Added!!!");
                    histoGrams.add(entry);
                }
            }
        }
    	
    	return histoGrams;
    }
    
    public static ArrayList<String> readNamesFromFolders(String dir){
    	
    	ArrayList<String> names = new ArrayList<String>();

        File folder = new File(dir);

        if (!folder.exists() || !folder.isDirectory()) {
        	System.err.println("No Folder named: " + dir);
            return null;
        }

        File[] files = folder.listFiles();

        if (files == null) {
        	System.err.println("No Files in: " + dir);
            return null;
        }

        for (File file : files) {
            if (file.isDirectory()) {
            	
            	names.add(file.getName().trim());
            	
            }
        }
    	
        Collections.sort(names);
    	return names;
    }
    
    public static ArrayList<String> readNames(String dir){
    	
    	ArrayList<String> names = new ArrayList<String>();

        File folder = new File(dir);

        if (!folder.exists() || !folder.isDirectory()) {
        	System.err.println("No Folder named: " + dir);
            return null;
        }

        File[] files = folder.listFiles();

        if (files == null) {
        	System.err.println("No Files in: " + dir);
            return null;
        }

        for (File file : files) {
            if (file.isFile()) {
            	
            	names.add(file.getName().split("[^A-Za-z]+")[0].trim());
            	
            }
        }
    	
    	
    	return names;
    }

}
