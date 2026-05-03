package EXEcutioners.GUI.UserInterfaces;
import java.io.File;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.Viewer;

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
import EXEcutioners.algorithms.GraphVisualiser;
import EXEcutioners.GUI.HelperClasses.WindowHandler;
public class SettingsInterface extends BorderPane implements IPreviousHelper {
	
	private StackPane Container = null;

	private Button BackButton = new Button("Back"); 
	 private Node PreviousView = null;
	private Stage MainStage=null;
	
	////
	private Stage graphStage;
	private FxViewer viewer;
	private FxDefaultView graphView;
	
	private String styles =
		    "graph {" +
		    "   fill-color: #111827;" +
		    "   padding: 70px;" +
		    "}" +

		    "node {" +
		    "   size: 24px;" +
		    "   fill-color: #60a5fa;" +
		    "   stroke-mode: plain;" +
		    "   stroke-color: white;" +
		    "   stroke-width: 2px;" +
		    "   text-size: 14px;" +
		    "   text-color: white;" +
		    "   text-style: bold;" +
		    "   text-background-mode: rounded-box;" +
		    "   text-background-color: #00000099;" +
		    "   text-padding: 4px, 2px;" +
		    "   text-offset: 0px, -20px;" +
		    "}" +

		    "node.query {" +
		    "   size: 34px;" +
		    "   fill-color: #facc15;" +
		    "   stroke-color: white;" +
		    "   stroke-width: 3px;" +
		    "}" +

		    "node.classA {" +
		    "   fill-color: #60a5fa;" +
		    "}" +

		    "node.classB {" +
		    "   fill-color: #34d399;" +
		    "}" +

		    "node.classC {" +
		    "   fill-color: #f87171;" +
		    "}" +

		    "edge {" +
		    "   size: 2px;" +
		    "   fill-color: #9ca3af;" +
		    "   arrow-shape: none;" +
		    "}";
	
	public SettingsInterface()
	{
	    
	    this.setBottom(BackButton);
		
		isClicked();
	}
	private void Alloc()
	{
		HBox AddXList = new HBox(10);
		VBox Combine = new VBox(10);
		Combine.getChildren().addAll(AddXList, BackButton);
		this.setTop(Combine);
	
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
	
	
	private void isClicked()
	{
		BackButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WindowHandler.HandleSwitch(Container,SettingsInterface.this, PreviousView, "right");
				
				//MainStage.setScene(PreviousScene);
				closeGraphWindow();
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
