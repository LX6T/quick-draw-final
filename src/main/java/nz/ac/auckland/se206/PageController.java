package nz.ac.auckland.se206;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

public class PageController {

	@FXML
	private JFXButton buttonOnStart;
	@FXML
	private JFXButton buttonOnExit;
	@FXML
	private JFXButton buttonOnSignIn;
	@FXML
	private JFXButton buttonOnSignUp;
	@FXML
	private TextField userName;

	@FXML
	private void exitGame() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void signInAction(ActionEvent event) throws InterruptedException {
		if (userName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty username");
			alert.setHeaderText("Please insert a valid username and try again");
			alert.showAndWait();
			// show and wait a bit to let user see
		} else if (ProfileRepository.containsKey(userName.getText())) {
			UserProfile currentUser = ProfileRepository.get(userName.getText());
			ProfileRepository.setCurrentUser(currentUser);
			// always set the current user to the repo
			Button button = (Button) event.getSource();
			Scene sceneButtonIsIn = button.getScene();

			try {
				// load the canvas scene when press this button
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
				Parent root = loader.load();
				StatsController statsController = loader.getController();
				// get the controller from the stats controller
				statsController.setStats(currentUser);
				sceneButtonIsIn.setRoot(root);
				// james will see what the root is
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			// throwing alerts if needed
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
			e.printStackTrace();
		}
	}

	@FXML
	private void onSignUp() {
		if (userName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty username");
			alert.setHeaderText("Please insert a valid username and try again");
			alert.showAndWait();
			// ig the user is blank then pop out alter message
		} else if (ProfileRepository.containsKey(userName.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("User name already exists");
			alert.setHeaderText("Please click on Sign-In to start the game");
			alert.showAndWait();
			// if the user already exist also throw the alter message
		} else {
			// if the user does not exist then sign up
			UserProfile newUser = new UserProfile(userName.getText());
			ProfileRepository.saveProfile(newUser);
			ProfileRepository.updateProfiles();
		}
	}
}
