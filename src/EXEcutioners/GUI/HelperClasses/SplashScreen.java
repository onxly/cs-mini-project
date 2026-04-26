package EXEcutioners.GUI.HelperClasses;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SplashScreen extends StackPane {

    private Runnable OnFinished;

    public SplashScreen() {
        Label title = new Label("I Cook");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");

        this.setStyle("-fx-background-color: #1a1a2e;");
        this.getChildren().add(title);

        // Wait 2 seconds, then fade out
        PauseTransition delay = new PauseTransition(Duration.millis(2000));
        delay.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(800), this);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(f -> {
                if (OnFinished != null)
                {
                	OnFinished.run();
                };
            });
            fade.play();
        });
        delay.play();
    }

    public void setOnFinished(Runnable onFinished) {
        this.OnFinished = onFinished;
    }
}