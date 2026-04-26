package EXEcutioners.GUI.HelperClasses;

import javafx.scene.control.TextArea;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
/**
 * Handles drag-and-drop file input for JavaFX nodes.
 * If the node is a TextArea, dropped file paths are displayed in it.
 */
public class DragFilehandler{
	 /**
     * Enables drag-and-drop on a node and stores dropped files.
     *
     * @param <T>  type of JavaFX Node
     * @param Type the node to attach drag functionality to
     * @return list of dropped files (filled after drop event occurs)
     */
	public static <T extends Node>  ArrayList<File> DragFile(T Type, Consumer<ArrayList<File>> FilesDropped)
	{
		ArrayList<File> Images = new ArrayList<File>();
		Type.setOnDragOver(e -> {
			if(e.getDragboard().hasFiles())
			{
				e.acceptTransferModes(TransferMode.COPY);
			}
			e.consume();
		});
		
		Type.setOnDragDropped(e -> {
			Dragboard DB = e.getDragboard();
			boolean Success  = false ;
			if(DB.hasFiles())
			{
				List<File> Content = DB.getFiles();
				for(File f : Content)
				{
					System.out.println("Dropped file : " + f.getAbsolutePath());
					
					if(Type instanceof TextArea)
					{
						TextArea Ta = (TextArea) Type;
						Ta.appendText(f.getAbsolutePath()+ "\n");
					}
					
					
					Images.add(f);
				}	
				 FilesDropped.accept(Images);
				 e.setDropCompleted(true);
				 e.consume();
			}
		});
		return Images;
	}

}

