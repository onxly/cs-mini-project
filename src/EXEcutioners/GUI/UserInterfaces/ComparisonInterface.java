package EXEcutioners.GUI.UserInterfaces;

import EXEcutioners.GUI.interfaces.IPreviousHelper;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.abstractClasses.PQEntry;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.graph.AnimalSignature;
import EXEcutioners.adts.graph.Classifier;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;
import EXEcutioners.algorithms.GraphSimilarity;
import EXEcutioners.imagehandling.GrayBlurSobel;
import EXEcutioners.imagehandling.ImageBlurrer;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.ImageRescaler;
import EXEcutioners.imagehandling.ImageToArray;
import EXEcutioners.imagehandling.Region;
import EXEcutioners.imagehandling.RegionList;
import EXEcutioners.imagehandling.SaliencyDetector;
import EXEcutioners.imagehandling.backgroundMasker;
import EXEcutioners.utils.GraphFileHelper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import EXEcutioners.GUI.HelperClasses.DragFilehandler;
import EXEcutioners.GUI.HelperClasses.WindowHandler;
public class ComparisonInterface extends BorderPane implements IPreviousHelper{
	
	private final int WINDOW_WIDTH = 720;

	private ImageGraph graphA;
	private ImageGraph graphB;
	private AnimalSignature asA;
	private AnimalSignature asB;
	
	private Button SelectLeftImages = new Button("Select Images");
	private Button SelectRightImages = new Button("Select Images");
	
	private TextArea ImageSpotLeft = new TextArea();
	private TextArea ImageSpotRight = new TextArea();
	
	private StackPane Container = null;
	private Button BackButton = new Button("Back"); 
	
	private Node PreviousView = null;
	private Stage MainStage=null;
	//private ScrollPane ImagesScroll= new ScrollPane();
	
	HBox ImagesHolder = new HBox(10);
	HBox ButtonHolder = new HBox(23);
	VBox Combine = new VBox(10);
	
	//////////////////////
	private TextField txtFilePathA = new TextField();
	private TextField txtFilePathB = new TextField();
	
	private Button btnSelectImgA = new Button("Browse");
	private Button btnSelectImgB = new Button("Browse");
	private Button btnCompare = new Button("Compare");
	
	private TextArea txtResult = new TextArea();
	
	private Label lblSelectImgA = new Label("Select Image A:");
	private Label lblSelectImgB = new Label("Select Image B:");
	
	HBox sidePanels = new HBox();
	
	VBox leftPanel = new VBox(10);
	VBox rightPanel = new VBox(10);
	
	HBox selectImageA = new HBox(10);
	HBox selectImageB = new HBox(10);
	
	HBox bottomButtons = new HBox(10);
	
	VBox imagesContainer = new VBox(10);
	
	StackPane imageABox = new StackPane();
	StackPane imageBBox = new StackPane();
	
	ImageView imageA = new ImageView();
	ImageView imageB = new ImageView();
	
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
	
	ScrollPane left = new ScrollPane(), Right= new ScrollPane();
	public ComparisonInterface()
	{
		
		txtFilePathA.setMaxWidth(Double.MAX_VALUE);
	    txtFilePathB.setMaxWidth(Double.MAX_VALUE);

	    HBox.setHgrow(txtFilePathA, Priority.ALWAYS);
	    HBox.setHgrow(txtFilePathB, Priority.ALWAYS);

	    txtFilePathA.setEditable(false);
	    txtFilePathB.setEditable(false);
	    txtResult.setEditable(false);

	    btnSelectImgA.setPrefWidth(120);
	    btnSelectImgB.setPrefWidth(120);

	    btnCompare.setMaxWidth(Double.MAX_VALUE);

	    selectImageA.setSpacing(10);
	    selectImageB.setSpacing(10);

	    selectImageA.setAlignment(Pos.CENTER);
	    selectImageB.setAlignment(Pos.CENTER);

	    selectImageA.getChildren().addAll(txtFilePathA, btnSelectImgA);
	    selectImageB.getChildren().addAll(txtFilePathB, btnSelectImgB);

	    leftPanel.setPrefWidth(WINDOW_WIDTH / 2);
	    leftPanel.setPadding(new Insets(10));
	    leftPanel.setSpacing(10);

	    txtResult.setMaxWidth(Double.MAX_VALUE);
	    txtResult.setMaxHeight(Double.MAX_VALUE);
	    VBox.setVgrow(txtResult, Priority.ALWAYS);
	    
	    bottomButtons.getChildren().addAll(BackButton);

	    leftPanel.getChildren().addAll(lblSelectImgA, selectImageA, lblSelectImgB, selectImageB, btnCompare, txtResult, bottomButtons);

	    imagesContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    imagesContainer.setPadding(new Insets(5));
	    
	    //imageABox.getChildren().setAll(imageA);
	    //imageBBox.getChildren().setAll(imageB);

	    imageABox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    imageBBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

	    VBox.setVgrow(imagesContainer, Priority.ALWAYS);
	    VBox.setVgrow(imageABox, Priority.ALWAYS);
	    VBox.setVgrow(imageBBox, Priority.ALWAYS);

	    imageABox.setStyle("-fx-border-color: gray;" +
	    	    "-fx-border-width: 2px;" +
	    	    "-fx-border-style: dashed;" +
	    	    "-fx-background-color: transparent;");
	    imageBBox.setStyle("-fx-border-color: gray;" +
	    	    "-fx-border-width: 2px;" +
	    	    "-fx-border-style: dashed;" +
	    	    "-fx-background-color: transparent;");
	    
	    btnCompare.setStyle(btnStyle);
		btnSelectImgA.setStyle(btnStyle);
		btnSelectImgB.setStyle(btnStyle);
		BackButton.setStyle(btnStyle);
		
		lblSelectImgA.setStyle("-fx-text-fill: white;");
		lblSelectImgB.setStyle("-fx-text-fill: white;");
		
		txtFilePathA.setStyle("-fx-background-color: #77a58b;");
		txtFilePathB.setStyle("-fx-background-color: #77a58b;");

	    imagesContainer.getChildren().addAll(imageABox, imageBBox);

	    rightPanel.setPrefWidth(WINDOW_WIDTH / 2);
	    rightPanel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    VBox.setVgrow(imagesContainer, Priority.ALWAYS);

	    rightPanel.getChildren().add(imagesContainer);

	    HBox.setHgrow(leftPanel, Priority.ALWAYS);
	    HBox.setHgrow(rightPanel, Priority.ALWAYS);

	    leftPanel.setMaxWidth(Double.MAX_VALUE);
	    rightPanel.setMaxWidth(Double.MAX_VALUE);

	    sidePanels.getChildren().addAll(leftPanel, rightPanel);
	    
	    DragFilehandler.DragFile(imageBBox, this::onRightDroppedFile);
		DragFilehandler.DragFile(imageABox, this::onLeftDroppedFile);

		this.setStyle("-fx-background-color: #2c4734;");
	    this.setCenter(sidePanels);
		isClicked();
		
		/*Alloc();
		this.setHeight(600);
		this.setWidth(400);
		//AddFile.setPrefSize(200, 50);
		ImageSpotLeft.setPrefSize(200, 400);
		ImageSpotLeft.setEditable(false);
		ImageSpotRight.setPrefSize(200, 400);
		ImageSpotRight.setEditable(false);
		BackButton.setPrefSize(400, 50);
		isClicked();*/
		
	}
	private void Alloc()
	{
		
		DragFilehandler.DragFile(imageABox, this::onRightDroppedFile);
		DragFilehandler.DragFile(imageBBox, this::onLeftDroppedFile);
		//ImagesHolder.getChildren().addAll(left,ImageSpotLeft,ImageSpotRight,Right);
		//ButtonHolder.getChildren().addAll(SelectLeftImages,BackButton,SelectRightImages);
		//Combine.getChildren().addAll(ImagesHolder,ButtonHolder);
		//this.setCenter(Combine);
	}
	
	public void compareGraphs() {
		
		if(graphA == null || graphB == null) {
			System.err.println("No graphs to compare!!!");
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Please select images first!");
			alert.showAndWait();
			
			return;
		}
		
		txtResult.clear();
		
		Double score1 = GraphSimilarity.calculateScore(asA, asB);
		Double score2 = GraphSimilarity.calculateScore(asB, asA);
		//Double score2 = GraphSimilarityML.calculateSimilarity(graphB, graphA);
		
		Double score = (score1+score2)/2;
		
		System.out.println("Similarity score: " + score);
		
		txtResult.setText("Similarity: " + String.format("%.2f%%", score));
		
	}
	
	public void onLeftDroppedFile(File file)
	{

		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
			BufferedImage imgScaled = ImageRescaler.rescale(img, 300, 300);
			BufferedImage imgBlurred = ImageBlurrer.applyBlur(imgScaled, 10);
			BufferedImage imgFocused = SaliencyDetector.computeSaliency(imgBlurred);
			int[][] pixels = ImageToArray.getPixels2D(imgFocused);
			ImageGraph graph = new ImageGraph();
			graph.buildGraph(pixels, 100, 10);
			this.graphA = graph;
			Classifier classify = new Classifier();
			
			AnimalSignature unknown = classify.generateSignature(graph);
			unknown.setSpecies("Lion");
			classify.train(unknown);
			this.asA = unknown;
			PopulateBox(imageABox, imageA, file.getAbsolutePath(), txtFilePathA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
				
				
	public void onRightDroppedFile(File file)
	{

		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
			BufferedImage imgScaled = ImageRescaler.rescale(img, 300, 300);
			BufferedImage imgBlurred = ImageBlurrer.applyBlur(imgScaled, 10);
			BufferedImage imgFocused = SaliencyDetector.computeSaliency(imgBlurred);
			int[][] pixels = ImageToArray.getPixels2D(imgFocused);
			ImageGraph graph = new ImageGraph();
			graph.buildGraph(pixels, 100, 10);
			this.graphB = graph;
			Classifier classify = new Classifier();
			
			AnimalSignature unknown = classify.generateSignature(graph);
			unknown.setSpecies("Lion");
			classify.train(unknown);
			this.asB = unknown;
			PopulateBox(imageBBox, imageB, file.getAbsolutePath(), txtFilePathB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void PopulateBox(StackPane Box, ImageView img, String filePath, TextField input)
	{
		input.setText(filePath);
		
		Box.setAlignment(Pos.CENTER);
		//VBox.setVgrow(Box, Priority.ALWAYS);

		img.setImage(new Image("file:" + filePath));

		img.setPreserveRatio(true);
		img.setSmooth(true);

		img.fitWidthProperty().unbind();
		img.fitHeightProperty().unbind();

		img.fitWidthProperty().bind(Box.widthProperty());
		img.fitHeightProperty().bind(Box.heightProperty());

		img.setManaged(false);

		Box.setStyle("-fx-border-color: transparent;" +
	    	    "-fx-background-color: transparent;");
		Box.getChildren().setAll(img);
			
		
	}
	private void isClicked()
	{
		
		
		
		btnSelectImgA.setOnAction(new EventHandler<ActionEvent>()
		{
			
			@Override
			public void handle(ActionEvent arg0)
			{
			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                File file = Choose.showOpenDialog(null);	                
	                
	                if (file != null && file.exists()) {
	                	
	                	onRightDroppedFile(file);
	                }
			}
		});
		
		btnSelectImgB.setOnAction(new EventHandler<ActionEvent>()
		{
			
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
		
		BackButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WindowHandler.HandleSwitch(Container,ComparisonInterface.this, PreviousView, "right");
				MainStage.setTitle("Home");
			
			}
		});
		
		btnCompare.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				compareGraphs();
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
