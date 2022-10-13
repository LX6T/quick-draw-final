package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

public class PageController implements Initializable{

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
	private AnchorPane masterPane;
	@FXML
	private JFXSlider sliderOnBrightness;
	@FXML
	private JFXSlider sliderOnVolume;

	private ColorAdjust colorAdjust = new ColorAdjust();
	
	boolean constant = false;
	
	URL musicURL = App.class.getResource("/sounds/" + "ForestWalk-320bit.mp3");
	Media backgroundMusic = new Media(musicURL.toExternalForm());
	private MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusic);

	@FXML
	private void exitGame() {
		Platform.exit();
		System.exit(0);
	}
	
	public void music() {
		
	}
	
	@FXML
	private void dragValueOfBrightness() {
		constant = true;
		colorAdjust.setBrightness((sliderOnBrightness.getValue() - 50) / 50);
		masterPane.setEffect(colorAdjust);
		
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
			fadeOutTwo(event);

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
		fadeOut(event);

	}

	private void fadeOut(ActionEvent event) {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(1);
		ft.setToValue(0.2);
		ft.setOnFinished((ActionEvent eventTwo) -> {
			loadNextScene(event);
		});
		ft.play();

	}

	private void fadeOutTwo(ActionEvent event) {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(1);
		ft.setToValue(0.2);
		ft.setOnFinished((ActionEvent eventTwo) -> {
			UserProfile currentUser = ProfileRepository.get(userName.getText());
			ProfileRepository.setCurrentUser(currentUser);
			Button button = (Button) event.getSource();
			Scene sceneButtonIsIn = button.getScene();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StatsController statsController = loader.getController();
			// get the controller from the stats controller
			statsController.setStats(currentUser);
			sceneButtonIsIn.setRoot(root);
			// james will see what the root is
		});
		ft.play();

	}

	private void loadNextScene(ActionEvent event) {
		Button button = (Button) event.getSource();
		Scene sceneButtonIsIn = button.getScene();

		try {
			// load the canvas scene when press this button
			sceneButtonIsIn.setRoot(App.loadFxml("user"));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		masterPane.setEffect(colorAdjust);
		mediaPlayer.play();

	}
}
