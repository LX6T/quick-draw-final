package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
  @FXML private Button buttonStart;
  @FXML private ToggleGroup accuracy;
  @FXML private ToggleGroup words;
  @FXML private ToggleGroup time;
  @FXML private ToggleGroup confidence;
  @FXML private ToggleGroup hiddenWord;
  @FXML private Button buttonOnStats;
  @FXML private Button buttonOnHistory;
  @FXML private Button buttonOnSettings;
  @FXML private AnchorPane paneOnSettings;
  @FXML private AnchorPane paneOnHistory;
  @FXML private GridPane paneOnData;
  @FXML private AnchorPane paneOnBadge;

  private SettingsData settingsData;

  public void initialize() {
    setStats();
    masterPane.setOpacity(0.2);
    fadeIn();
    paneOnData.setVisible(true);
    paneOnData.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
    paneOnBadge.setVisible(false);
  }

  private void fadeIn() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 0.2, 1);
    ft.play();
  }

  @FXML
  private void onChooseSettings() {
    paneOnData.setVisible(false);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(true);
    paneOnSettings.setOpacity(1);
    paneOnBadge.setVisible(false);
  }

  @FXML
  private void onChooseHistory() {
    paneOnData.setVisible(false);
    paneOnHistory.setVisible(true);
    paneOnHistory.setOpacity(1);
    paneOnSettings.setVisible(false);
    paneOnBadge.setVisible(false);
  }

  @FXML
  private void onChooseStats() {
    paneOnData.setVisible(true);
    paneOnData.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
    paneOnBadge.setVisible(false);
  }

  @FXML
  private void onChooseBadge() {
    paneOnData.setVisible(false);
    paneOnBadge.setVisible(true);
    paneOnBadge.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
  }

  @FXML
  protected void setStats() {

    UserProfile user = ProfileRepository.getCurrentUser();

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

    settingsData = user.getPreferredSettings();

    if (settingsData.isComplete()) {
      buttonStart.setDisable(false);
      ObservableList<Toggle> accuracyButtons = accuracy.getToggles();
      ObservableList<Toggle> wordsButtons = words.getToggles();
      ObservableList<Toggle> timeButtons = time.getToggles();
      ObservableList<Toggle> confidenceButtons = confidence.getToggles();
      ObservableList<Toggle> hiddenWordButtons = hiddenWord.getToggles();

      int accuracyDifficultyIndex =
          SettingsData.toDifficultyIndex(settingsData.getAccuracyDifficulty());
      int wordsDifficultyIndex = SettingsData.toDifficultyIndex(settingsData.getWordsDifficulty());
      int timeDifficultyIndex = SettingsData.toDifficultyIndex(settingsData.getTimeDifficulty());
      int confidenceDifficultyIndex =
          SettingsData.toDifficultyIndex(settingsData.getConfidenceDifficulty());
      int hiddenWordModeIndex = SettingsData.toModeIndex(settingsData.isHiddenMode());

      accuracyButtons.get(accuracyDifficultyIndex).setSelected(true);
      wordsButtons.get(wordsDifficultyIndex).setSelected(true);
      timeButtons.get(timeDifficultyIndex).setSelected(true);
      confidenceButtons.get(confidenceDifficultyIndex).setSelected(true);
      hiddenWordButtons.get(hiddenWordModeIndex).setSelected(true);
    }

    hiddenWord
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) hiddenWord.getSelectedToggle();
              if (rb != null) {
                settingsData.setHiddenMode(rb.getText());
              }
            });

    accuracy
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) accuracy.getSelectedToggle();
              if (rb != null) {
                settingsData.setAccuracyDifficulty(rb.getText());
              }
            });

    words
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) words.getSelectedToggle();
              if (rb != null) {
                settingsData.setWordsDifficulty(rb.getText());
              }
            });

    time.selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) time.getSelectedToggle();
              if (rb != null) {
                settingsData.setTimeDifficulty(rb.getText());
              }
            });

    confidence
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) confidence.getSelectedToggle();
              if (rb != null) {
                settingsData.setConfidenceDifficulty(rb.getText());
              }
            });
  }

  @FXML
  private void onBack(ActionEvent event) {
    if (settingsData.isComplete()) {
      ProfileRepository.updateUserSettings(settingsData);
    }
    fadeOutToPage(event);
  }

  @FXML
  private void onStart(ActionEvent event) {
    if (settingsData.isComplete()) {
      ProfileRepository.updateUserSettings(settingsData);
      fadeOutToCanvas(event);
    }
  }

  private void fadeOutToCanvas(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadCanvasScene(event));
    ft.play();
  }

  private void fadeOutToPage(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
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
