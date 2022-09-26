package nz.ac.auckland.se206;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class PageController {
	
	  @FXML private Button buttonOnStart;
	  @FXML private Button buttonOnExit;
	  
	  @FXML
	  private void exitGame() {
	    Platform.exit();
	    System.exit(0);
	  }
	  
	  @FXML
	  private void startNewGame(ActionEvent event) throws InterruptedException {

	    Button button = (Button) event.getSource();
	    Scene sceneButtonIsIn = button.getScene();

	    try {
	      // load the canvas scene when press this button
	      sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }
	

}
