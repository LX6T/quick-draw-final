package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

public class PageController {

  @FXML private TextField userName;
  @FXML private AnchorPane masterPane;

  @FXML
  private void exitGame() {
    Platform.exit();
    System.exit(0);
  }

  @FXML
  private void signInAction(ActionEvent event) {
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
  private void startNewGame(ActionEvent event) {
    fadeOut(event);
  }

  private void fadeOut(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane);
    ft.setOnFinished((ActionEvent eventTwo) -> loadCanvasScene(event));
    ft.play();
  }

  private void fadeOutTwo(ActionEvent event) {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(500));
    ft.setNode(masterPane);
    ft.setFromValue(1);
    ft.setToValue(0.2);
    ft.setOnFinished(
        (ActionEvent eventTwo) -> {
          UserProfile currentUser = ProfileRepository.get(userName.getText());
          ProfileRepository.setCurrentUser(currentUser);
          Button button = (Button) event.getSource();
          Scene sceneButtonIsIn = button.getScene();
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
          Parent root = null;
          try {
            root = loader.load();
          } catch (IOException e) {
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

  private void loadCanvasScene(ActionEvent event) {
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
