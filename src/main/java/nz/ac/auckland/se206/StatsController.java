package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.SettingsData;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

public class StatsController {
  @FXML private Label labelHistory;
  @FXML private Label labelWins;
  @FXML private Label labelLosses;
  @FXML private Label labelScore;
  @FXML private Label labelRecord;
  @FXML private AnchorPane masterPane;

  @FXML private RadioButton radioAccuracyEasy;
  @FXML private RadioButton radioAccuracyMedium;
  @FXML private RadioButton radioAccuracyHard;
  @FXML private RadioButton radioWordsEasy;
  @FXML private RadioButton radioWordsMedium;
  @FXML private RadioButton radioWordsHard;
  @FXML private RadioButton radioWordsMaster;
  @FXML private RadioButton radioTimeEasy;
  @FXML private RadioButton radioTimeMedium;
  @FXML private RadioButton radioTimeHard;
  @FXML private RadioButton radioTimeMaster;
  @FXML private RadioButton radioConfidenceEasy;
  @FXML private RadioButton radioConfidenceMedium;
  @FXML private RadioButton radioConfidenceHard;
  @FXML private RadioButton radioConfidenceMaster;

  ToggleGroup accuracy;
  ToggleGroup words;
  ToggleGroup time;
  ToggleGroup confidence;

  SettingsData settingsData;

  public void initialize() {
    accuracy = new ToggleGroup();
    words = new ToggleGroup();
    time = new ToggleGroup();
    confidence = new ToggleGroup();
    settingsData = new SettingsData();

    radioAccuracyEasy.setToggleGroup(accuracy);
    radioAccuracyMedium.setToggleGroup(accuracy);
    radioAccuracyHard.setToggleGroup(accuracy);

    accuracy
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) accuracy.getSelectedToggle();
              if (rb != null) {
                settingsData.setAccuracyDifficulty(rb.getText());
              }
            });

    radioWordsEasy.setToggleGroup(words);
    radioWordsMedium.setToggleGroup(words);
    radioWordsHard.setToggleGroup(words);
    radioWordsMaster.setToggleGroup(words);

    words
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) words.getSelectedToggle();
              if (rb != null) {
                settingsData.setWordsDifficulty(rb.getText());
              }
            });

    radioTimeEasy.setToggleGroup(time);
    radioTimeMedium.setToggleGroup(time);
    radioTimeHard.setToggleGroup(time);
    radioTimeMaster.setToggleGroup(time);

    time.selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) time.getSelectedToggle();
              if (rb != null) {
                settingsData.setTimeDifficulty(rb.getText());
              }
            });

    radioConfidenceEasy.setToggleGroup(confidence);
    radioConfidenceMedium.setToggleGroup(confidence);
    radioConfidenceHard.setToggleGroup(confidence);
    radioConfidenceMaster.setToggleGroup(confidence);

    confidence
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) confidence.getSelectedToggle();
              if (rb != null) {
                settingsData.setConfidenceDifficulty(rb.getText());
              }
            });

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

    SettingsData preferredSettings = user.getPreferredSettings();
    if (preferredSettings != null) {
      ObservableList<Toggle> accuracyButtons = accuracy.getToggles();
      ObservableList<Toggle> wordsButtons = words.getToggles();
      ObservableList<Toggle> timeButtons = time.getToggles();
      ObservableList<Toggle> confidenceButtons = confidence.getToggles();

      int accuracyDifficultyIndex = SettingsData.toIndex(preferredSettings.getAccuracyDifficulty());
      int wordsDifficultyIndex = SettingsData.toIndex(preferredSettings.getWordsDifficulty());
      int timeDifficultyIndex = SettingsData.toIndex(preferredSettings.getTimeDifficulty());
      int confidenceDifficultyIndex =
          SettingsData.toIndex(preferredSettings.getConfidenceDifficulty());

      accuracyButtons.get(accuracyDifficultyIndex).setSelected(true);
      wordsButtons.get(wordsDifficultyIndex).setSelected(true);
      timeButtons.get(timeDifficultyIndex).setSelected(true);
      confidenceButtons.get(confidenceDifficultyIndex).setSelected(true);
    }
  }

  @FXML
  private void back(ActionEvent event) {
    ProfileRepository.updateUserData(settingsData);
    fadeOutTwo(event);
  }

  @FXML
  private void onStart(ActionEvent event) {
    if (settingsData.isComplete()) {
      ProfileRepository.updateUserData(settingsData);
      fadeOut(event);
    }
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
