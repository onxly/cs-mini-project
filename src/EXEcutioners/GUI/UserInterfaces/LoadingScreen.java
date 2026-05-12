package EXEcutioners.GUI.UserInterfaces;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class LoadingScreen {

    public static Scene create(String msg) {
        ProgressIndicator spinner = new ProgressIndicator();

        Label label = new Label(msg);
        label.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-font-family: 'Segoe UI';" +
            "-fx-font-weight: bold;"
        );
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(spinner, label);

        root.setStyle(
            "-fx-background-color: #2c4734;"
        );

        return new Scene(root, 720, 480);
    }
}
