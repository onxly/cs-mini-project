package EXEcutioners.GUI.UserInterfaces;
import java.io.File;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import EXEcutioners.GUI.interfaces.IPreviousHelper;
import EXEcutioners.GUI.HelperClasses.WindowHandler;
public class SettingsInterface extends BorderPane implements IPreviousHelper {
	
	private StackPane Container = null;

	private Button BackButton = new Button("Back"); 
	 private Node PreviousView = null;
	private Stage MainStage=null;
	
	public SettingsInterface()
	{
		BackButton.setPrefSize(400, 50); 
		Alloc();
		isClicked();
	}
	private void Alloc()
	{
		HBox AddXList = new HBox(10);
		VBox Combine = new VBox(10);
		Combine.getChildren().addAll(AddXList, BackButton);
		this.setTop(Combine);
	
	}
	private void isClicked()
	{
		BackButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WindowHandler.HandleSwitch(Container,SettingsInterface.this, PreviousView, "right");
				
				//MainStage.setScene(PreviousScene);
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
