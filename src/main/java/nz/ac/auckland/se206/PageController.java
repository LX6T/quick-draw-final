package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

public class PageController {
	
	private ProfileRepository repo;
	private HashMap<String, UserProfile> users;

	@FXML
	private Button buttonOnStart;
	@FXML
	private Button buttonOnExit;
	@FXML
	private Button buttonOnSignIn;
	
	
	
	public void initialise() {
		repo = new ProfileRepository("src/main/java/nz/ac/auckland/se206/profiles/repository");
		repo.loadProfiles();
		this.users = repo.getUsers();
	}

	@FXML
	private void exitGame() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void signInAction(ActionEvent event) throws InterruptedException {
		if(true) {
			Button button = (Button) event.getSource();
			Scene sceneButtonIsIn = button.getScene();

			try {
				// load the canvas scene when press this button
				sceneButtonIsIn.setRoot(App.loadFxml("profilePage"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
