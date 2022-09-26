package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

public class PageController {

	private ProfileRepository repo = new ProfileRepository(
			"src/main/java/nz/ac/auckland/se206/user profile repository");
	private HashMap<String, UserProfile> users = repo.getUsers();

	public static String user;

	@FXML
	private Button buttonOnStart;
	@FXML
	private Button buttonOnExit;
	@FXML
	private Button buttonOnSignIn;
	@FXML
	private Button buttonOnSignUp;
	@FXML
	private TextField userName;

	public void initialise() {
		repo.loadProfiles();
	}

	@FXML
	private void exitGame() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void signInAction(ActionEvent event) throws InterruptedException {
		// if the text field is blank, then pop out alert message not allowing the black
		// user name
		if (userName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty username");
			alert.setHeaderText("Please insert a valid username and try again");
			alert.showAndWait();
		} else if (users.containsKey(userName.getText())) {
			// if the text contains the username, then the user will log into the specific
			// account
			user = userName.getText();
			Button button = (Button) event.getSource();
			Scene sceneButtonIsIn = button.getScene();

			try {
				// load the canvas scene when press this button
				sceneButtonIsIn.setRoot(App.loadFxml("profilePage"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if the text is not one of the user names, then alter message pop out
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("User name does not exist");
			alert.setHeaderText("Please click on Sign-Up to create your account");
			alert.showAndWait();
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

	@FXML
	private void onSignUp() {
		if (userName.getText().isBlank()) {
			// if the sign up text is blank, the alter message pop out not allowing blank
			// account
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty username");
			alert.setHeaderText("Please insert a valid username and try again");
			alert.showAndWait();
		} else if (users.containsKey(userName.getText())) {
			// if the account name already exists then such users can not be registered
			// again
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("User name already exists");
			alert.setHeaderText("Please click on Sign-In to start the game");
			alert.showAndWait();
		} else {
			// if the user name is new, then register this account
			UserProfile newUser = new UserProfile(userName.getText());
			repo.saveProfile(newUser);
			repo.updateProfiles();
		}
	}
}
