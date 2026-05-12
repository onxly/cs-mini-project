package EXEcutioners.algorithms.ml;

import weka.core.*;
import java.util.ArrayList;
import java.io.File;
import weka.core.converters.ArffSaver;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaDataUtility {
	
    public static Instances createHeader(int histogramSize, ArrayList<String> speciesList) {
        ArrayList<Attribute> attributes = new ArrayList<>();

        for (int i = 0; i < histogramSize; i++) {
            attributes.add(new Attribute("bin_" + i));
        }

        attributes.add(new Attribute("species_class", speciesList));

        Instances dataset = new Instances("SpeciesRelation", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);
        return dataset;
    }

    public static void saveDataset(Instances dataset, String path) throws Exception {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataset);
        saver.setFile(new File(path));
        saver.writeBatch();
    }
    
    public static Instances loadDataset(String path) throws Exception {
        DataSource source = new DataSource(path);
        Instances dataset = source.getDataSet();
        
        if (dataset.classIndex() == -1) {
            dataset.setClassIndex(dataset.numAttributes() - 1);
        }
        
        return dataset;
    }

}
