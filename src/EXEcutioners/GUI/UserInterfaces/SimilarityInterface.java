package EXEcutioners.GUI.UserInterfaces;


import EXEcutioners.GUI.HelperClasses.WindowHandler;
import EXEcutioners.GUI.interfaces.IPreviousHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimilarityInterface extends BorderPane implements IPreviousHelper{

	
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
	
	public SimilarityInterface() 
	{
		
		Alloc(); 
		this.setHeight(600);
		this.setWidth(400);
		isClicked();
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
	
	}
	
	@Override
	public void getPreviousScene(StackPane container, Node previousView, Stage mainStage) {
		// TODO Auto-generated method stub
		Container = container;
        PreviousView = previousView;
		MainStage=mainStage;
	
	}
	
}
