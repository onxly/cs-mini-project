package EXEcutioners.GUI.UserInterfaces;

import EXEcutioners.GUI.interfaces.IPreviousHelper;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.abstractClasses.Vertex;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.adts.interfaces.IEdge;
import EXEcutioners.adts.interfaces.IEntry;
import EXEcutioners.adts.interfaces.IVertex;
import EXEcutioners.algorithms.GraphSimilarity;
import EXEcutioners.imagehandling.GrayBlurSobel;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.LinkedRegionalList;
import EXEcutioners.imagehandling.Region;
import EXEcutioners.imagehandling.RegionFeature;
import EXEcutioners.imagehandling.RegionList;
import EXEcutioners.imagehandling.backgroundMasker;

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
	
	VBox imagesContainer = new VBox(10);
	
	StackPane imageABox = new StackPane();
	StackPane imageBBox = new StackPane();
	
	ImageView imageA = new ImageView();
	ImageView imageB = new ImageView();
	
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

	    leftPanel.getChildren().addAll(lblSelectImgA, selectImageA, lblSelectImgB, selectImageB, btnCompare, txtResult, BackButton);

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
		
		Double score = GraphSimilarity.calculateScore(graphA, graphB);
		
		System.out.println("Similarity score: " + score);
		
		txtResult.setText("Similarity: " + String.format("%.2f%%", score * 100));
		
	}
	
	public void onLeftDroppedFile(ArrayList<File> Files)
	{
		
		LinkedRegionalList ImageRegionList = new LinkedRegionalList();

		for (File f: Files ) {
		//per image break up the image into region and add them into the list;
		ImageRegionList.AddImageRegions(ImageProcessor.processImg(backgroundMasker.MaskBackground(f.getAbsolutePath())));
		System.out.println("got past adding: "+ImageRegionList.getSize());
		}
		
		Iterator<Iterable<GraphNode>> iteList = ImageRegionList.getGraphNodeList().iterator();
		Iterable<GraphNode> listofRegions = null;
		ImageGraph IG = new ImageGraph(3);
		if (iteList.hasNext()) {
			listofRegions = iteList.next();
		}
		IG.buildGraph(listofRegions);
		
		this.graphA = IG;
		
		//IG.drawGraph();
		
		//PopulateBox(ImageSpotLeft, Files, left);
		
		//imageABox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		PopulateBox(imageABox, imageA, Files.get(0).getAbsolutePath(), txtFilePathA);
			
	}
				
				
	public void onRightDroppedFile(ArrayList<File> Files)
	{
	
		//ImagesHolder.getChildren().addAll(ImageSpotRight,Right);
		//GrayBlurSobel.GetSobelImage(Files.getFirst().getAbsolutePath());
		
		LinkedRegionalList ImageRegionList = new LinkedRegionalList();

		for (File f: Files ) {
		//per image break up the image into region and add them into the list;
		ImageRegionList.AddImageRegions(ImageProcessor.processImg(backgroundMasker.MaskBackground(f.getAbsolutePath())));
		System.out.println("got past adding: "+ImageRegionList.getSize());
		}
		
		Iterator<Iterable<GraphNode>> iteList = ImageRegionList.getGraphNodeList().iterator();
		Iterable<GraphNode> listofRegions = null;
		ImageGraph IG = new ImageGraph(3);
		if (iteList.hasNext()) {
			listofRegions = iteList.next();
		}
		
		LinkedPositionalList<LinkedPositionalList<RegionFeature>> things = ImageRegionList.getPerImagelists();
		
		
		for (Iterator iterator = things.iterator(); iterator.hasNext();) {
			LinkedPositionalList<RegionFeature> filesOfThings = (LinkedPositionalList<RegionFeature>) iterator.next();
			
			int i = 1;
			
			for (Iterator iterator2 = filesOfThings.iterator(); iterator2.hasNext();) {
				RegionFeature file = (RegionFeature) iterator2.next();
				try {
					ImageIO.write(file.getSobelImage(), "jpg", new File(i + ".jpg"));
					i++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		IG.buildGraph(listofRegions);
		
		this.graphB = IG;
		
		//IG.drawGraph();
		
		PopulateBox(imageBBox, imageB, Files.get(0).getAbsolutePath(), txtFilePathB);
		
		
		//PopulateBox(ImageSpotRight,Files, Right);
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
		SelectRightImages.setOnAction(new EventHandler<ActionEvent>()
		{
			
			@Override
			public void handle(ActionEvent arg0)
			{
			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                ArrayList<File> files = new ArrayList<>(Choose.showOpenMultipleDialog(null));
	                if (files != null && !files.isEmpty()) {
	                    onRightDroppedFile(files);
	                }
			}
		});
		SelectLeftImages.setOnAction(new EventHandler<ActionEvent>()
		{
			
			@Override
			public void handle(ActionEvent arg0)
			{
			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                ArrayList<File> files = new ArrayList<>(Choose.showOpenMultipleDialog(null));
	                if (files != null && !files.isEmpty()) {
	                    onLeftDroppedFile(files);
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
		
		btnSelectImgA.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				ArrayList<File> files = new ArrayList<>();
			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                File file = Choose.showOpenDialog(null);
	                
	                files.add(file);
	                
	                if (files != null && !files.isEmpty()) {
	                	
	                	onLeftDroppedFile(files);
	                }
			}
			
		});
		
		btnSelectImgB.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				ArrayList<File> files = new ArrayList<>();
			
				FileChooser Choose = new FileChooser();
				Choose.getExtensionFilters().add(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	                );
	                File file = Choose.showOpenDialog(null);
	                
	                files.add(file);
	                
	                if (files != null && !files.isEmpty()) {
	                	
	                	onRightDroppedFile(files);
	                }
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
