package EXEcutioners.GUI.UserInterfaces;

import EXEcutioners.GUI.interfaces.IPreviousHelper;
import EXEcutioners.adts.abstractClasses.LinkedPositionalList;
import EXEcutioners.adts.graph.GraphNode;
import EXEcutioners.adts.graph.ImageGraph;
import EXEcutioners.imagehandling.GrayBlurSobel;
import EXEcutioners.imagehandling.ImageProcessor;
import EXEcutioners.imagehandling.LinkedRegionalList;
import EXEcutioners.imagehandling.Region;
import EXEcutioners.imagehandling.RegionFeature;
import EXEcutioners.imagehandling.RegionList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import EXEcutioners.GUI.HelperClasses.DragFilehandler;
import EXEcutioners.GUI.HelperClasses.WindowHandler;
public class ComparisonInterface extends BorderPane implements IPreviousHelper{

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
	
	ScrollPane left = new ScrollPane(), Right= new ScrollPane();
	public ComparisonInterface()
	{
		Alloc();
		this.setHeight(600);
		this.setWidth(400);
		//AddFile.setPrefSize(200, 50);
		ImageSpotLeft.setPrefSize(200, 400);
		ImageSpotLeft.setEditable(false);
		ImageSpotRight.setPrefSize(200, 400);
		ImageSpotRight.setEditable(false);
		BackButton.setPrefSize(400, 50); 
		isClicked();
		
	}
	private void Alloc()
	{
		
		DragFilehandler.DragFile(ImageSpotRight, this::onRightDroppedFile);
		DragFilehandler.DragFile(ImageSpotLeft, this::onLeftDroppedFile);
		ImagesHolder.getChildren().addAll(left,ImageSpotLeft,ImageSpotRight,Right);
		ButtonHolder.getChildren().addAll(SelectLeftImages,BackButton,SelectRightImages);
		Combine.getChildren().addAll(ImagesHolder,ButtonHolder);
		this.setCenter(Combine);
	}
	
	public void onLeftDroppedFile(ArrayList<File> Files)
	{
		
		LinkedRegionalList ImageRegionList = new LinkedRegionalList();

		for (File f: Files ) {
		//per image break up the image into region and add them into the list;
		ImageRegionList.AddImageRegions(ImageProcessor.processImg(f.getAbsolutePath()));//regions of said image;
		System.out.println("got past adding: "+ImageRegionList.getSize());
		
		/*for (Iterator<Iterable<GraphNode>> iterator = ImageRegionList.getGraphNodeList().iterator(); iterator.hasNext();) {
			Iterable<GraphNode> file = (Iterable<GraphNode>) iterator.next();
			
		}*/
		
			
		}
		for (Iterator<Iterable<GraphNode>> iterator = ImageRegionList.getGraphNodeList().iterator(); iterator.hasNext();) {
			ImageGraph IG = new ImageGraph(3);
			Iterable<GraphNode> listofRegions = (Iterable<GraphNode>) iterator.next();
			
			IG.buildGraph(listofRegions);
		}

		
		PopulateBox(ImageSpotLeft, Files, left);
	}
	public void onRightDroppedFile(ArrayList<File> Files)
	{
	
		//ImagesHolder.getChildren().addAll(ImageSpotRight,Right);
		//GrayBlurSobel.GetSobelImage(Files.getFirst().getAbsolutePath());
		PopulateBox(ImageSpotRight,Files, Right);
	}
	public void PopulateBox(TextArea A, ArrayList<File> Files,ScrollPane scroll)
	{
		if(!Files.isEmpty())
		{
			VBox ImgBox = new VBox(5);
			for (File file : Files) {
			
				ImageView IV = new ImageView();
				IV.setImage(new Image("file:"+file.getAbsolutePath()));
				
				IV.setFitWidth(100);	
				IV.setFitHeight(100);
				IV.setPreserveRatio(false);				
	 			ImgBox.getChildren().add(IV);
			    
				
			}
			scroll.setContent(ImgBox);
		}
		
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
		
	}
	@Override
	public void getPreviousScene(StackPane container, Node previousView, Stage mainStage) {
		// TODO Auto-generated method stub
		Container = container;
        PreviousView = previousView;
		MainStage=mainStage;
	
	}
	

}
