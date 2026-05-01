package EXEcutioners.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import EXEcutioners.adts.graph.ImageGraph;


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
            return null;
        }

        File[] files = folder.listFiles();

        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isFile()) {
            	ImageGraph graph = readGraph(file);

                if (graph != null) {
                    graphs.add(graph);
                }
            }
        }

        return graphs;
    }

}
