package EXEcutioners.GUI.HelperClasses;

import javafx.scene.control.TextArea;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Handles drag-and-drop for a single file input for JavaFX nodes.
 */
public class DragFilehandler {

    /**
     * Enables drag-and-drop on a node and processes a single dropped file.
     *
     * @param <T>          type of JavaFX Node
     * @param node         the node to attach drag functionality to
     * @param fileDropped  callback that receives the single dropped File
     */
    public static <T extends Node> void DragFile(T node, Consumer<File> fileDropped) {
        
        node.setOnDragOver(e -> {
            if (e.getDragboard().hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
            }
            e.consume();
        });

        node.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            
            if (db.hasFiles()) {
                // Get the list but only take the first element
                List<File> content = db.getFiles();
                if (!content.isEmpty()) {
                    File f = content.get(0);
                    
                    System.out.println("Dropped file: " + f.getAbsolutePath());

                    if (node instanceof TextArea) {
                        TextArea ta = (TextArea) node;
                        // For a single file, we usually want to clear the text first 
                        // or just show the one path.
                        ta.setText(f.getAbsolutePath() + "\n");
                    }

                    // Trigger the callback with ONLY the first file
                    fileDropped.accept(f);
                    success = true;
                }
            }
            e.setDropCompleted(success);
            e.consume();
        });
    }
}