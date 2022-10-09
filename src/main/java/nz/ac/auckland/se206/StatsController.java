package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

public class StatsController {
  @FXML private Label labelHistory;
  @FXML private Label labelWins;
  @FXML private Label labelLosses;
  @FXML private Label labelScore;
  @FXML private Label labelRecord;

  @FXML private AnchorPane masterPane;

  public void initialize() {
    masterPane.setOpacity(0.2);
    fadeIn();
  }

  private void fadeIn() {
    FadeTransition ft = new FadeTransition();
    // set fade in animation
    ft.setDuration(Duration.millis(500));
    // interval is 500 ms
    ft.setNode(masterPane);
    ft.setFromValue(0.2);
    // increase from 0.2 opacity to 1
    ft.setToValue(1);
    ft.play();
  }

  @FXML
  public void setStats(UserProfile user) {
    if (Objects.equals(user.getWordsHistory(), "")) {
      // if this username is inside the words history
      labelHistory.setText("N/A");
      // set the label to not available
    } else {
      labelHistory.setText(user.getWordsHistory());
    }

    labelWins.setText(user.getNumOfWin().toString());
    // set the number of wins
    labelLosses.setText(user.getNumOfLost().toString());
    // set the number of loss
    labelScore.setText(user.getScore().toString());
    // set the text of the score

    if (user.getBestRecord() > 60) {
      // if there is not a record yet display N/A
      labelRecord.setText("N/A");
    } else {
      labelRecord.setText(user.getBestRecord() + "s");
    }
  }

  @FXML
  private void back(ActionEvent event) {
    fadeOutTwo(event);
  }

  @FXML
  private void onStart(ActionEvent event) {
    fadeOut(event);
  }

  private void fadeOut(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane);
    ft.setOnFinished((ActionEvent eventTwo) -> loadCanvasScene(event));
    ft.play();
  }

  private void fadeOutTwo(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane);
    ft.setOnFinished((ActionEvent eventTwo) -> loadPageScene(event));
    ft.play();
  }

  private void loadCanvasScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    // get the next scene and set the next scene

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
      // set the scene from the current scene
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadPageScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    // get the current scene setting

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("page"));
      // set the next scene based on the current scene
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
