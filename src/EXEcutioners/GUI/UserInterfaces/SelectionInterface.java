package EXEcutioners.GUI.UserInterfaces;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

import EXEcutioners.GUI.HelperClasses.WindowHandler;

public class SelectionInterface extends BorderPane {

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
	
	private Button ClassifyButton= new Button("Classify");
	private Button SimilarityButton= new Button("Similarity");
	private Button SettingsButton= new Button("Settings");
	private Button CreditsButton= new Button("Credits");
	private Button ExitButton= new Button("Exit");
	
	private SimilarityInterface Similar= null;
	private ComparisonInterface Compare= null;
	
	private Stage MainStage = new Stage();
	private StackPane Container;
	public SelectionInterface(Stage s, StackPane container)
	{
		ClassifyButton.setStyle(btnStyle);
		ExitButton.setStyle(btnStyle);
		SimilarityButton.setStyle(btnStyle);
		SettingsButton.setStyle(btnStyle);
		CreditsButton.setStyle(btnStyle);
		
		this.setStyle(
			    "-fx-background-image: url('file:background.jpg');" +
			    "-fx-background-size: cover;" +
			    "-fx-background-position: center center;" +
			    "-fx-background-repeat: no-repeat;"
			);
		
		this.MainStage = s;
		Container=container;
		Alloc();
		isClicked();
		
	}
	private void Alloc()
	{
		VBox CompareXSimilar = new VBox(20);
		SimilarityButton.setPrefSize(400, 50);
		ClassifyButton.setPrefSize(400, 50);
		SettingsButton.setPrefSize(400,  50);
		CreditsButton.setPrefSize(400,  50);
		ExitButton.setPrefSize(400,  50);
		CompareXSimilar.getChildren().addAll(ClassifyButton,SimilarityButton);
		//this.getChildren().add(LeecherXSeeder);
		CompareXSimilar.setAlignment(Pos.CENTER);
		
	
		this.setCenter(CompareXSimilar);
		
	}
	
	private void isClicked()
	{
		
		ClassifyButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				 Similar = new SimilarityInterface();
	             Similar.getPreviousScene(Container, SelectionInterface.this, MainStage);
	             WindowHandler.HandleSwitch(Container, SelectionInterface.this, Similar, "right");
	             MainStage.setTitle("Similarity Mode");		
			}
		});
		
		SimilarityButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				Compare = new ComparisonInterface();
	            Compare.getPreviousScene(Container, SelectionInterface.this, MainStage);
	            WindowHandler.HandleSwitch(Container, SelectionInterface.this, Compare, "left");
	            MainStage.setTitle("Similarity Mode");		
				
			}
		});
	}
	
}

