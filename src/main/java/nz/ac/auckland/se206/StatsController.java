package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.SettingsData;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;


public class StatsController extends App{

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
  @FXML private AnchorPane paneOnBadgeOne;
  @FXML private AnchorPane paneOnBadgeTwo;
  @FXML private AnchorPane paneOnBadgeThree;
  @FXML private GridPane gridPanePage1;
  @FXML private GridPane gridPanePage2;
  @FXML private GridPane gridPanePage3;
  @FXML private ImageView iconOnDown;
  @FXML private ImageView iconOnUp;

  private SettingsData settingsData;

  public void initialize() {
    setStats();
    masterPane.setOpacity(0.2);
    masterPane.setCursor(new ImageCursor(image));
    fadeIn();
    paneOnData.setVisible(true);
    paneOnData.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);

    paneOnBadgeOne.setVisible(true);
    paneOnBadgeTwo.setVisible(false);
    paneOnBadgeThree.setVisible(false);
    paneOnBadge.setVisible(false);
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

    setBadges(user);
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

  @FXML
  private void setBadges(UserProfile user) {
    ObservableList<Node> page1 = gridPanePage1.getChildren();
    ObservableList<Node> page2 = gridPanePage2.getChildren();
    ObservableList<Node> page3 = gridPanePage3.getChildren();

    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setHue(0.2);

    Node badge1 = page1.get(0);
    Node badge2 = page1.get(1);
    Node badge3 = page1.get(2);
    Node badge4 = page1.get(3);
    Node badge5 = page1.get(4);
    Node badge6 = page1.get(5);
    Node badge7 = page1.get(6);
    Node badge8 = page1.get(7);

    Node badge9 = page2.get(0);
    Node badge10 = page2.get(1);
    Node badge11 = page2.get(2);
    Node badge12 = page2.get(3);
    Node badge13 = page2.get(4);
    Node badge14 = page2.get(5);
    Node badge15 = page2.get(6);
    Node badge16 = page2.get(7);

    Node badge17 = page3.get(0);
    Node badge18 = page3.get(1);
    Node badge19 = page3.get(2);
    Node badge20 = page3.get(3);
    Node badge21 = page3.get(4);
    Node badge22 = page3.get(5);
    Node badge23 = page3.get(6);
    Node badge24 = page3.get(7);

    if (user.hasBadge("allModes")) badge1.setEffect(colorAdjust);

    if (user.hasBadge("accuracy1")) badge2.setEffect(colorAdjust);
    if (user.hasBadge("accuracy2")) badge3.setEffect(colorAdjust);
    if (user.hasBadge("accuracy3")) badge4.setEffect(colorAdjust);

    if (user.hasBadge("words1")) badge5.setEffect(colorAdjust);
    if (user.hasBadge("words2")) badge6.setEffect(colorAdjust);
    if (user.hasBadge("words3")) badge7.setEffect(colorAdjust);
    if (user.hasBadge("words4")) badge8.setEffect(colorAdjust);

    if (user.hasBadge("time1")) badge9.setEffect(colorAdjust);
    if (user.hasBadge("time2")) badge10.setEffect(colorAdjust);
    if (user.hasBadge("time3")) badge11.setEffect(colorAdjust);
    if (user.hasBadge("time4")) badge12.setEffect(colorAdjust);

    if (user.hasBadge("confidence1")) badge13.setEffect(colorAdjust);
    if (user.hasBadge("confidence2")) badge14.setEffect(colorAdjust);
    if (user.hasBadge("confidence3")) badge15.setEffect(colorAdjust);
    if (user.hasBadge("confidence4")) badge16.setEffect(colorAdjust);

    if (user.hasBadge("streak2")) badge17.setEffect(colorAdjust);
    if (user.hasBadge("streak3")) badge18.setEffect(colorAdjust);
    if (user.hasBadge("streak5")) badge19.setEffect(colorAdjust);
    if (user.hasBadge("streak10")) badge20.setEffect(colorAdjust);

    if (user.hasBadge("win5")) badge21.setEffect(colorAdjust);
    if (user.hasBadge("win10")) badge22.setEffect(colorAdjust);
    if (user.hasBadge("win20")) badge23.setEffect(colorAdjust);
    if (user.hasBadge("win50")) badge24.setEffect(colorAdjust);
  }

  @FXML
  private void onScrollUp() {
	  iconOnUp.setScaleX(1);
	  iconOnUp.setScaleY(1);
    if (paneOnBadgeTwo.isVisible()) {
      paneOnBadgeOne.setVisible(true);
      paneOnBadgeTwo.setVisible(false);
    } else if (paneOnBadgeThree.isVisible()) {
      paneOnBadgeTwo.setVisible(true);
      paneOnBadgeThree.setVisible(false);
    }
  }

  @FXML
  private void onScrollDown() {
	  iconOnDown.setScaleX(1);
	  iconOnDown.setScaleY(1);
    if (paneOnBadgeOne.isVisible()) {
      paneOnBadgeOne.setVisible(false);
      paneOnBadgeTwo.setVisible(true);
    } else if (paneOnBadgeTwo.isVisible()) {
      paneOnBadgeTwo.setVisible(false);
      paneOnBadgeThree.setVisible(true);
    }
  }
  
  @FXML
  private void onEnterUp() {
	  iconOnUp.setScaleX(1.1);
	  iconOnUp.setScaleY(1.1);
	  iconOnUp.setEffect(new Bloom(0.3));
  }
  
  @FXML
  private void onExitUp() {
	  iconOnUp.setScaleX(1);
	  iconOnUp.setScaleY(1);
	  iconOnUp.setEffect(null);
  }
  
  @FXML
  private void onPressUp() {
	  iconOnUp.setScaleX(0.9);
	  iconOnUp.setScaleY(0.9);
	  
  }
  
  @FXML  
  private void onEnterDown() {
	  iconOnDown.setScaleX(1.1);
	  iconOnDown.setScaleY(1.1);
	  iconOnDown.setEffect(new Bloom(0.3));
  }
  
  @FXML
  private void onExitDown() {
	  iconOnDown.setScaleX(1);
	  iconOnDown.setScaleY(1);
	  iconOnDown.setEffect(null);
  }
  
  @FXML
  private void onPressDown() {
	  iconOnDown.setScaleX(0.9);
	  iconOnDown.setScaleY(0.9);
	 
  }
}
