package EXEcutioners.GUI.UserInterfaces;


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
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.algorithms.GraphVisualiser;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.LinkedRegionalList;
import EXEcutioners.imagehandling.RegionFeature;
import EXEcutioners.imagehandling.backgroundMasker;
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

public class SimilarityInterface extends BorderPane implements IPreviousHelper{

	private final int WINDOW_WIDTH = 720;
	private ImageGraph graph;
	
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
	
	private TextArea txtResult = new TextArea();
	
	private Label lblSelectImgA = new Label("Select Image:");
	
	HBox sidePanels = new HBox();
	
	VBox leftPanel = new VBox(10);
	VBox rightPanel = new VBox(10);
	
	HBox selectImageA = new HBox(10);
	
	VBox imagesContainer = new VBox(10);
	
	StackPane imageABox = new StackPane();
	
	ImageView imageA = new ImageView();
	
	public SimilarityInterface() 
	{
		System.setProperty("org.graphstream.ui", "javafx");
		
		txtFilePathA.setMaxWidth(Double.MAX_VALUE);

	    HBox.setHgrow(txtFilePathA, Priority.ALWAYS);

	    txtFilePathA.setEditable(false);
	    txtResult.setEditable(false);

	    btnSelectImgA.setPrefWidth(120);

	    btnCompare.setMaxWidth(Double.MAX_VALUE);

	    selectImageA.setSpacing(10);

	    selectImageA.setAlignment(Pos.CENTER);

	    selectImageA.getChildren().addAll(txtFilePathA, btnSelectImgA);

	    leftPanel.setPrefWidth(WINDOW_WIDTH / 2);
	    leftPanel.setPadding(new Insets(10));
	    leftPanel.setSpacing(10);

	    txtResult.setMaxWidth(Double.MAX_VALUE);
	    txtResult.setMaxHeight(Double.MAX_VALUE);
	    VBox.setVgrow(txtResult, Priority.ALWAYS);

	    leftPanel.getChildren().addAll(lblSelectImgA, selectImageA, btnCompare, txtResult, BackButton);

	    imagesContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    imagesContainer.setPadding(new Insets(5));
	    
	    //imageABox.getChildren().setAll(imageA);
	    //imageBBox.getChildren().setAll(imageB);

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

	    sidePanels.getChildren().addAll(leftPanel, rightPanel);
	    
		DragFilehandler.DragFile(imageABox, this::onLeftDroppedFile);

	    this.setCenter(sidePanels);
	    isClicked();
		
		/*Alloc(); 
		this.setHeight(600);
		this.setWidth(400);
		isClicked();*/
	}
	private void Alloc()
	{
		VBox AddressXPort = new VBox(10);
		SeederAddress.setPrefSize(100, 50);
		SeederPort.setPrefSize(100, 50);
		FileList.setPrefSize(400, 100);
		BackButton.setPrefSize(400, 50); 
		AddressXPort.getChildren().addAll(SeederAddress,SeederPort, BackButton);
		
		HBox SeederXConnect =new HBox(10);
		FileNumber.setPrefSize(50, 50);
		
		SeederXConnect.getChildren().addAll(AddressXPort,Connect,Retrieve,FileNumber);
		
		
		this.setTop(SeederXConnect);
		this.setBottom(FileList);
	}
	
	private void openGraphWindow(Graph graph) {
	    if (graphStage != null && graphStage.isShowing()) {
	        graphStage.toFront();
	        return;
	    }

	    viewer = new FxViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
	    viewer.enableAutoLayout();

	    graphView = (FxDefaultView) viewer.addDefaultView(false);

	    BorderPane root = new BorderPane();
	    root.setCenter(graphView);

	    Scene scene = new Scene(root, 800, 600);

	    graphStage = new Stage();
	    graphStage.setTitle("Graph Viewer");
	    graphStage.setScene(scene);

	    graphStage.setOnCloseRequest(e -> closeGraphWindow());

	    graphStage.show();
	}
	
	private void closeGraphWindow() {
	    if (viewer != null) {
	        viewer.disableAutoLayout();
	        viewer.close();
	        viewer = null;
	    }

	    if (graphStage != null) {
	        graphStage.close();
	        graphStage = null;
	    }

	    graphView = null;
	}
	
	public void classifyGraph() {
		
		if(graph == null) {
			System.err.println("No graph to classify!!!");
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Please select an image first!");
			alert.showAndWait();
			
			return;
		}
		
		Graph graphV = GraphVisualiser.drawGraph("testing", this.graph);
		
	    openGraphWindow(graphV);
		
		txtResult.clear();
		txtResult.setText("That's an animal... but I'm not sure...");
		
		this.graph.drawGraph();
		
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
		
		this.graph = IG;
		
		//IG.drawGraph();
		
		//PopulateBox(ImageSpotLeft, Files, left);
		
		//imageABox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		PopulateBox(imageABox, imageA, Files.get(0).getAbsolutePath(), txtFilePathA);
			
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
		
		btnCompare.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0)
			{
				classifyGraph();
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
