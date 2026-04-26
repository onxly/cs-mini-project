package EXEcutioners.GUI.UserInterfaces;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import EXEcutioners.GUI.HelperClasses.WindowHandler;

public class SelectionInterface extends BorderPane {

	private Button SimilarButton= new Button("Similarity");
	private Button CompareButton= new Button("Comparison");
	private Button SettingsButton= new Button("Settings");
	private Button CreditsButton= new Button("Credits");
	private Button ExitButton= new Button("Exit");
	
	private SimilarityInterface Similar= null;
	private ComparisonInterface Compare= null;
	private SettingsInterface  Settings=null;
	private CreditsInterface  Credits=null;

	
	private Stage MainStage = new Stage(); 
	private StackPane Container;
	public SelectionInterface(Stage s, StackPane container)
	{
		
		
		this.MainStage = s;
		Container=container;
		Alloc();
		isClicked();
		
	
	}
	private void Alloc()
	{
		VBox CompareXSimilar = new VBox(20);
		CompareButton.setPrefSize(400, 50);
		SimilarButton.setPrefSize(400, 50);
		SettingsButton.setPrefSize(400,  50);
		CreditsButton.setPrefSize(400,  50);
		ExitButton.setPrefSize(400,  50);
		CompareXSimilar.getChildren().addAll(SimilarButton,CompareButton, SettingsButton, CreditsButton, ExitButton);
		//this.getChildren().add(LeecherXSeeder);
		CompareXSimilar.setAlignment(Pos.CENTER);
		
	
		this.setCenter(CompareXSimilar);
		
	}
	
	private void isClicked()
	{
		
		SimilarButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				 Similar = new SimilarityInterface();
	             Similar.getPreviousScene(Container, SelectionInterface.this, MainStage);
	             WindowHandler.HandleSwitch(Container, SelectionInterface.this, Similar, "right");
	             MainStage.setTitle("Similarity Mode");		
			}
		});
		
		CompareButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				Compare = new ComparisonInterface();
	            Compare.getPreviousScene(Container, SelectionInterface.this, MainStage);
	            WindowHandler.HandleSwitch(Container, SelectionInterface.this, Compare, "left");
	            MainStage.setTitle("Similarity Mode");		
				
			}
		});
		SettingsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Settings =  new  SettingsInterface();
	            Settings.getPreviousScene(Container, SelectionInterface.this, MainStage);
	            WindowHandler.HandleSwitch(Container, SelectionInterface.this, Settings, "left");
	            MainStage.setTitle("Similarity Mode");		
			}
		});
		CreditsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Credits = new CreditsInterface();
	            Credits.getPreviousScene(Container, SelectionInterface.this, MainStage);
	            WindowHandler.HandleSwitch(Container, SelectionInterface.this, Credits, "left");
	            MainStage.setTitle("Similarity Mode");		
				
			}
		});
	}
	
}

