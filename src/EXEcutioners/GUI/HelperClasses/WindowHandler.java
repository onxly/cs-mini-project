package EXEcutioners.GUI.HelperClasses;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class WindowHandler {

	
	public static void HandleSwitch(StackPane container, Node OldWindow, Node NewWindow, String Direction)
	{
	    double width = container.getWidth();
	    

	    if(Direction.equals("left"))
	    {
	        NewWindow.setTranslateX(width);
	        container.getChildren().add(NewWindow);

	        TranslateTransition slideOut = new TranslateTransition(Duration.millis(400), OldWindow);
	        slideOut.setToX(-width);
	        slideOut.setInterpolator(Interpolator.EASE_IN);

	        TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), NewWindow);
	        slideIn.setToX(0);
	        slideIn.setInterpolator(Interpolator.EASE_OUT);

	        slideOut.setOnFinished(e -> container.getChildren().remove(OldWindow));

	        slideOut.play();
	        slideIn.play();
	    }
	    else if(Direction.equals("right"))
	    {
	        NewWindow.setTranslateX(-width);
	        container.getChildren().add(NewWindow);

	        TranslateTransition slideOut = new TranslateTransition(Duration.millis(400), OldWindow);
	        slideOut.setToX(width);
	        slideOut.setInterpolator(Interpolator.EASE_IN);

	        TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), NewWindow);
	        slideIn.setToX(0);
	        slideIn.setInterpolator(Interpolator.EASE_OUT);

	        slideOut.setOnFinished(e -> container.getChildren().remove(OldWindow));

	        slideOut.play();
	        slideIn.play();
	    }
	}
}
