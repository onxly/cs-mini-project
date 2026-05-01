import java.util.Iterator;

import EXEcutioners.GUI.HelperClasses.SplashScreen;
import EXEcutioners.GUI.UserInterfaces.SelectionInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import EXEcutioners.imagehandling.*;

public class Main extends Application  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage PrimeStage) throws Exception {
	    PrimeStage.setTitle("Home");

	    StackPane container = new StackPane();
	    SelectionInterface root = new SelectionInterface(PrimeStage, container);


	    SplashScreen splash = new SplashScreen();
	    root.setVisible(false);  

	    splash.setOnFinished(() -> {
	        root.setVisible(true);  
	        container.getChildren().remove(splash);
	        PrimeStage.setTitle("Home");
	    });

	    container.getChildren().addAll(root, splash); // splash sits on top

	    Scene HomeScene = new Scene(container, 720, 480);
	    PrimeStage.setScene(HomeScene);
	    PrimeStage.show();
	}

}
