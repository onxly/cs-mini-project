package EXEcutioners.GUI.UserInterfaces;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.Viewer;

import EXEcutioners.GUI.HelperClasses.DragFilehandler;
import EXEcutioners.GUI.HelperClasses.WindowHandler;
import EXEcutioners.GUI.interfaces.IPreviousHelper;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.graph.AnimalSignature;
import EXEcutioners.adts.graph.Classifier;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.algorithms.ml.SpeciesClassifier;
import EXEcutioners.algorithms.ml.SpeciesPredictor;
import EXEcutioners.algorithms.ml.WekaDataUtility;
import EXEcutioners.imagehandling.ImageBlurrer;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.ImageRescaler;
import EXEcutioners.imagehandling.ImageToArray;
import EXEcutioners.imagehandling.SaliencyDetector;
import EXEcutioners.imagehandling.backgroundMasker;
import EXEcutioners.utils.GraphFileHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import weka.core.DenseInstance;
import weka.core.Instances;

public class SimilarityInterface extends BorderPane implements IPreviousHelper{

	private Classifier classify = new Classifier();
	private SpeciesPredictor sp;
	private File pendingFile;
	
	private final int WINDOW_WIDTH = 720;
	private ImageGraph graphA;
	private AnimalSignature sig;
	
	private Stage graphStage;
	private FxViewer viewer;
	private FxDefaultView graphView;
	
	private TextField SeederAddress = new TextField();
	private TextField SeederPort = new TextField();
	private Button Connect= new Button("Connect");
	private static TextArea FileList = new TextArea();
	
	private Button Retrieve= new Button("Retrieve");
	private TextField FileNumber = new TextField();
	

	private StackPane Container = null;
	private Button BackButton = new Button("Back"); 
	private Node PreviousView = null;
	private Stage MainStage=null;
	
	///////////////
	private TextField txtFilePathA = new TextField();
	
	private Button btnSelectImgA = new Button("Browse");
	private Button btnCompare = new Button("Classify");
	private Button btnVisualise = new Button("Visualise");
	
	private TextArea txtResult = new TextArea();
	
	private Label lblSelectImgA = new Label("Select Image:");
	
	HBox sidePanels = new HBox();
	
	VBox leftPanel = new VBox(10);
	VBox rightPanel = new VBox(10);
	
	HBox selectImageA = new HBox(10);
	HBox bottomButtons = new HBox(10);
	
	VBox imagesContainer = new VBox(10);
	
	StackPane imageABox = new StackPane();
	
	ImageView imageA = new ImageView();
	
	private String btnStyle = "-fx-background-color: #1d2f23;" +
		    "-fx-text-fill: white;" +
		    "-fx-font-size: 14px;" +
		    "-fx-font-weight: bold;" +
		    "-fx-padding: 10px 22px;" +
		    "-fx-background-radius: 12px;" +
		    "-fx-border-radius: 12px;" +
		    "-fx-cursor: hand;" +
		    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 8, 0.2, 0, 3);" +
		    "-fx-font-family: 'Arial';";
	
	public SimilarityInterface() 
	{
		System.setProperty("org.graphstream.ui", "javafx");
		
		txtFilePathA.setMaxWidth(Double.MAX_VALUE);

	    HBox.setHgrow(txtFilePathA, Priority.ALWAYS);

	    txtFilePathA.setEditable(false);
	    txtResult.setEditable(false);

	    btnSelectImgA.setPrefWidth(120);

	    btnCompare.setMaxWidth(Double.MAX_VALUE);
	    
	    btnVisualise.setDisable(true);

	    selectImageA.setSpacing(10);

	    selectImageA.setAlignment(Pos.CENTER);

	    selectImageA.getChildren().addAll(txtFilePathA, btnSelectImgA);

	    leftPanel.setPrefWidth(WINDOW_WIDTH / 2);
	    leftPanel.setPadding(new Insets(10));
	    leftPanel.setSpacing(10);

	    txtResult.setMaxWidth(Double.MAX_VALUE);
	    txtResult.setMaxHeight(Double.MAX_VALUE);
	    VBox.setVgrow(txtResult, Priority.ALWAYS);
	    
	    bottomButtons.getChildren().addAll(BackButton, btnVisualise);

	    leftPanel.getChildren().addAll(lblSelectImgA, selectImageA, btnCompare, txtResult, bottomButtons);

	    imagesContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    imagesContainer.setPadding(new Insets(5));

	    imageABox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

	    VBox.setVgrow(imagesContainer, Priority.ALWAYS);
	    VBox.setVgrow(imageABox, Priority.ALWAYS);

	    imageABox.setStyle("-fx-border-color: gray;" +
	    	    "-fx-border-width: 2px;" +
	    	    "-fx-border-style: dashed;" +
	    	    "-fx-background-color: transparent;");

	    imagesContainer.getChildren().addAll(imageABox);

	    rightPanel.setPrefWidth(WINDOW_WIDTH / 2);
	    rightPanel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    VBox.setVgrow(imagesContainer, Priority.ALWAYS);

	    rightPanel.getChildren().add(imagesContainer);

	    HBox.setHgrow(leftPanel, Priority.ALWAYS);
	    HBox.setHgrow(rightPanel, Priority.ALWAYS);

	    leftPanel.setMaxWidth(Double.MAX_VALUE);
	    rightPanel.setMaxWidth(Double.MAX_VALUE);
	    
	    btnCompare.setStyle(btnStyle);
		btnSelectImgA.setStyle(btnStyle);
		btnVisualise.setStyle(btnStyle);
		BackButton.setStyle(btnStyle);
		
		lblSelectImgA.setStyle("-fx-text-fill: white;");
		txtFilePathA.setStyle("-fx-background-color: #77a58b;");
		

	    sidePanels.getChildren().addAll(leftPanel, rightPanel);
	    
		DragFilehandler.DragFile(imageABox, this::onLeftDroppedFile);

		this.setStyle("-fx-background-color: #2c4734;");
		
		Platform.runLater(() -> {
	        Stage stage = (Stage) this.getScene().getWindow();
	        Scene originalScene = this.getScene();
	        
	        Scene loadingScene = LoadingScreen.create("Processing...");
	        stage.setScene(loadingScene);

	        Thread thread = new Thread(() -> {
	            try {
	                isClicked(); 
	                
	                Platform.runLater(() -> {
	                    this.setCenter(sidePanels);
	                    stage.setScene(originalScene);
	                });
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });

	        thread.setDaemon(true);
	        thread.start();
	    });
		
	}
	
	public void classifyGraph() {
	    if (pendingFile == null) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("No image selected!");
	        alert.showAndWait();
	        return;
	    }

	    Stage stage = (Stage) this.getScene().getWindow();
	    Scene originalScene = this.getScene();
	    
	    stage.setScene(LoadingScreen.create("Calculating..."));

	    Thread processingThread = new Thread(() -> {
	        try {
	            BufferedImage img = ImageIO.read(pendingFile);
	            BufferedImage imgScaled = ImageRescaler.rescale(img, 300, 300);
	            BufferedImage imgBlurred = ImageBlurrer.applyBlur(imgScaled, 10);
	            BufferedImage imgFocused = SaliencyDetector.computeSaliency(imgBlurred);
	            
	            int[][] pixels = ImageToArray.getPixels2D(imgFocused);
	            ImageGraph graph = new ImageGraph();
	            graph.buildGraph(pixels, 100, 10);
	            
	            this.graphA = graph;
	            this.sig = classify.generateSignature(graph);

	            String response = sp.classify(sig.getHistogram());

	            Platform.runLater(() -> {
	                txtResult.setText(response);
	                btnVisualise.setDisable(false);
	                stage.setScene(originalScene);
	            });

	        } catch (Exception e) {
	            e.printStackTrace();
	            Platform.runLater(() -> {
	                stage.setScene(originalScene);
	                txtResult.setText("Error processing image: " + e.getMessage());
	            });
	        }
	    });

	    processingThread.setDaemon(true);
	    processingThread.start();
	}
	
	public void onLeftDroppedFile(File file) {
	    if (!file.exists()) return;

	    graphA = null;
	    sig = null;
	    txtResult.clear();
	    txtFilePathA.clear();
	    
	    this.pendingFile = file;
	    
	    txtFilePathA.setText(pendingFile.getName());
	    PopulateBox(imageABox, imageA, pendingFile.getAbsolutePath());
	    
	    btnCompare.setDisable(false);
	}
	
	public void PopulateBox(StackPane Box, ImageView img, String filePath) {
	    
	    Box.getChildren().clear();

	    Image newImage = new Image("file:" + this.pendingFile);
	    
	    img.setImage(newImage);
	    img.setPreserveRatio(true);
	    img.setSmooth(true);

	    img.fitWidthProperty().unbind();
	    img.fitHeightProperty().unbind();
	    img.fitWidthProperty().bind(Box.widthProperty());
	    img.fitHeightProperty().bind(Box.heightProperty());

	    img.setManaged(false);

	    Box.getChildren().add(img);
	    
	    Box.applyCss();
	    Box.layout();
	    
	    Box.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
	}
	
	public void setUpML() {
		
		ArrayList<IEntry<String, double[]>> histoGrams = GraphFileHelper.readHistogramsFromDirectory("graphs");
		
		Instances dataset = null;
		
		File modelFile = new File("model/wildgraph.model");
		
		File arffFile = new File("docs/dataset.arff");
		
		boolean reset = false;
			
		if(histoGrams == null || histoGrams.isEmpty()) {
			GraphFileHelper.buildAndSaveGraphs("animals");
			reset = true;
		}
		
		histoGrams = GraphFileHelper.readHistogramsFromDirectory("graphs");
		
		if(!arffFile.exists() || reset) {
			
			ArrayList<String> names = GraphFileHelper.readNamesFromFolders("animals");
		
			dataset = WekaDataUtility.createHeader(histoGrams.get(0).getValue().length, names);
							
			for (IEntry<String, double[]> h : histoGrams) {
			    double[] values = new double[dataset.numAttributes()];
			    double[] histogramData = h.getValue();

			    for (int i = 0; i < histogramData.length; i++) {
			        values[i] = histogramData[i];
			    }

			    int classIndex = names.indexOf(h.getKey());
			    			    
			    if (classIndex == -1) {
			        System.err.println("Species '" + h.getKey() + "' was not found in the header's species list.");
			    }

			    values[dataset.numAttributes() - 1] = classIndex;

			    dataset.add(new DenseInstance(1.0, values));
			}
			
			try {
				WekaDataUtility.saveDataset(dataset, "docs/dataset.arff");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
			
		try {
			dataset = WekaDataUtility.loadDataset(arffFile.getPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
				
		if(!modelFile.exists() || reset) {
			SpeciesClassifier.train(dataset);
		}
		
		try {
			this.sp = new SpeciesPredictor(modelFile.getAbsolutePath(), dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	
	private void isClicked()
	{
						
		setUpML();
		
		Connect.setOnAction(new EventHandler<ActionEvent>()
		{	
			@Override
			public void handle(ActionEvent arg0)
			{
		
				
					 
			}
		});
		Retrieve.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0)
			{
				
				
			}
		});
		BackButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WindowHandler.HandleSwitch(Container,SimilarityInterface.this, PreviousView, "left");
				MainStage.setTitle("Home");
			}
		});
		
		btnSelectImgA.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                File file = Choose.showOpenDialog(null);	                
	                
	                if (file != null && file.exists()) {
	                	
	                	onLeftDroppedFile(file);
	                }
			}
			
		});
		
		btnCompare.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				classifyGraph();
			}
			
		});
		
		btnVisualise.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				
				ArrayList<PQEntry<String, ImageGraph>> graphs = new ArrayList<PQEntry<String,ImageGraph>>();
				
				PQEntry<String,ImageGraph> imgGraph = new PQEntry<String, ImageGraph>("Classify", graphA);
				
				graphs.add(imgGraph);
				
			}
			
		});
	
	}
	
	@Override
	public void getPreviousScene(StackPane container, Node previousView, Stage mainStage) {
		// TODO Auto-generated method stub
		Container = container;
        PreviousView = previousView;
		MainStage=mainStage;
	
	}
	
}
