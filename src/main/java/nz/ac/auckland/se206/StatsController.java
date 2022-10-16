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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.SettingsData;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

/**
 * This class is a controller for the user's profile page, where they can view their statistics,
 * badges, and preferred settings.
 */
public class StatsController extends CanvasController {

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
  @FXML private Label labelOnCurrentUser;
  @FXML private ImageView imageOnCurrentUser;

  private SettingsData settingsData;

  /**
   * This method initialises the user statistics scene, updating the user's stats, settings and
   * badges.
   */
  public void initialize() {
    setStats();
    // set the stats to the users
    masterPane.setOpacity(0.2);
    masterPane.setCursor(new ImageCursor(image));
    fadeIn();
    // fade in transition animation
    paneOnData.setVisible(true);
    paneOnData.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
    // set the correct nodes to be visible

    paneOnBadgeOne.setVisible(true);
    paneOnBadgeTwo.setVisible(false);
    paneOnBadgeThree.setVisible(false);
    paneOnBadge.setVisible(false);
    // set the correct badges to be visible
    labelOnCurrentUser.setText(ProfileRepository.getCurrentUser().getAccountName());
    Image image =
        new Image(
            App.class
                .getResource(ProfileRepository.getCurrentUser().getPhotoPath())
                .toExternalForm());
    imageOnCurrentUser.setImage(image);
    // update the label of current user and the image
  }

  /** This method switches tabs to the settings select page. Also allows users to choose settings */
  @FXML
  private void onChooseSettings() {
    // set the correct pane to be visible
    paneOnData.setVisible(false);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(true);
    paneOnSettings.setOpacity(1);
    paneOnBadge.setVisible(false);
  }

  /** This method switches tabs to the word history page. Also allows users to choose settings */
  @FXML
  private void onChooseHistory() {
    // set the correct pane to be visible
    paneOnData.setVisible(false);
    paneOnHistory.setVisible(true);
    paneOnHistory.setOpacity(1);
    paneOnSettings.setVisible(false);
    paneOnBadge.setVisible(false);
  }

  /** Also allows users to choose settings This method switches tabs to the statistics page. */
  @FXML
  private void onChooseStats() {
    // set the correct pane to be visible
    paneOnData.setVisible(true);
    paneOnData.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
    paneOnBadge.setVisible(false);
  }

  /** Also allows users to choose settings This method switches tabs to the badges page */
  @FXML
  private void onChooseBadge() {
    // set the correct pane to be visible
    paneOnData.setVisible(false);
    paneOnBadge.setVisible(true);
    paneOnBadge.setOpacity(1);
    paneOnHistory.setVisible(false);
    paneOnSettings.setVisible(false);
  }

  /**
   * This method initialises all the user's data, filling in the scene. set all the correct stats
   */
  @FXML
  protected void setStats() {
    // set the correct pane to be visible

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
      // finds the radio buttons from each difficulty category
      ObservableList<Toggle> accuracyButtons = accuracy.getToggles();
      ObservableList<Toggle> wordsButtons = words.getToggles();
      ObservableList<Toggle> timeButtons = time.getToggles();
      ObservableList<Toggle> confidenceButtons = confidence.getToggles();
      ObservableList<Toggle> hiddenWordButtons = hiddenWord.getToggles();

      // gets the difficulty levels from the user's preferred settings
      int accuracyDifficultyIndex =
          SettingsData.toDifficultyIndex(settingsData.getAccuracyDifficulty());
      int wordsDifficultyIndex = SettingsData.toDifficultyIndex(settingsData.getWordsDifficulty());
      int timeDifficultyIndex = SettingsData.toDifficultyIndex(settingsData.getTimeDifficulty());
      int confidenceDifficultyIndex =
          SettingsData.toDifficultyIndex(settingsData.getConfidenceDifficulty());
      int hiddenWordModeIndex = SettingsData.toModeIndex(settingsData.isHiddenMode());

      // selects the appropriate difficulty button for each category
      accuracyButtons.get(accuracyDifficultyIndex).setSelected(true);
      wordsButtons.get(wordsDifficultyIndex).setSelected(true);
      timeButtons.get(timeDifficultyIndex).setSelected(true);
      confidenceButtons.get(confidenceDifficultyIndex).setSelected(true);
      hiddenWordButtons.get(hiddenWordModeIndex).setSelected(true);
    }

    // toggles the hidden word mode on/off upon clicking
    hiddenWord
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) hiddenWord.getSelectedToggle();
              if (rb != null) {
                settingsData.setHiddenMode(rb.getText());
              }
            });

    // update the accuracy level upon selecting a new difficulty
    accuracy
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) accuracy.getSelectedToggle();
              if (rb != null) {
                settingsData.setAccuracyDifficulty(rb.getText());
              }
            });

    // update the word level upon selecting a new difficulty
    words
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) words.getSelectedToggle();
              if (rb != null) {
                settingsData.setWordsDifficulty(rb.getText());
              }
            });

    // update the time level upon selecting a new difficulty
    time.selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton rb = (RadioButton) time.getSelectedToggle();
              if (rb != null) {
                settingsData.setTimeDifficulty(rb.getText());
              }
            });

    // update the confidence level upon selecting a new difficulty
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

  /**
   * This method takes the user back to the main menu page.
   *
   * @param event is the click event
   */
  @FXML
  private void onBack(ActionEvent event) {
    if (settingsData.isComplete()) {
      ProfileRepository.updateUserSettings(settingsData);
    }
    fadeOutToPage(event);
  }

  /**
   * This method takes the user to the canvas page.
   *
   * @param event is the click event
   */
  @FXML
  private void onStart(ActionEvent event) {
    if (settingsData.isComplete()) {
      ProfileRepository.updateUserSettings(settingsData);
      fadeOutToCanvas(event);
    }
  }

  /**
   * This method transitions the scene to the canvas scene
   *
   * @param event is the click event
   */
  private void fadeOutToCanvas(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadCanvasScene(event));
    ft.play();
  }

  /**
   * This method transitions the scene to the main menu page
   *
   * @param event is the click event
   */
  private void fadeOutToPage(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadUserScene(event));
    ft.play();
  }

  /**
   * This method loads the canvas scene before the transition
   *
   * @param event is the click event
   */
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

  /**
   * This method loads the page scene before the transition
   *
   * @param event is the click event
   */
  private void loadUserScene(ActionEvent event) {

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    // get the current scene setting

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("user"));
      // set the next scene based on the current scene
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method sets up the correct and earned number of badges of the user and is also responsible
   * of changing the color
   *
   * @param user A userProfile taken as input
   */
  @FXML
  private void setBadges(UserProfile user) {
    ObservableList<Node> page1 = gridPanePage1.getChildren();
    ObservableList<Node> page2 = gridPanePage2.getChildren();
    ObservableList<Node> page3 = gridPanePage3.getChildren();
    // get three different pages of badges

    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setHue(0.2);
    // set color change effect

    Node badge1 = page1.get(0);
    Node badge2 = page1.get(1);
    Node badge3 = page1.get(2);
    Node badge4 = page1.get(3);
    Node badge5 = page1.get(4);
    Node badge6 = page1.get(5);
    Node badge7 = page1.get(6);
    Node badge8 = page1.get(7);
    // allocate badge 1 to 7 all to page 1

    Node badge9 = page2.get(0);
    Node badge10 = page2.get(1);
    Node badge11 = page2.get(2);
    Node badge12 = page2.get(3);
    Node badge13 = page2.get(4);
    Node badge14 = page2.get(5);
    Node badge15 = page2.get(6);
    Node badge16 = page2.get(7);
    // allocate badge 9 to 16 all to page 2

    Node badge17 = page3.get(0);
    Node badge18 = page3.get(1);
    Node badge19 = page3.get(2);
    Node badge20 = page3.get(3);
    Node badge21 = page3.get(4);
    Node badge22 = page3.get(5);
    Node badge23 = page3.get(6);
    Node badge24 = page3.get(7);
    // allocate badges 17 to 24 all to badge 3

    if (user.hasBadge("allModes")) badge1.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge

    if (user.hasBadge("accuracy1")) badge2.setEffect(colorAdjust);
    if (user.hasBadge("accuracy2")) badge3.setEffect(colorAdjust);
    if (user.hasBadge("accuracy3")) badge4.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of accuracy

    if (user.hasBadge("words1")) badge5.setEffect(colorAdjust);
    if (user.hasBadge("words2")) badge6.setEffect(colorAdjust);
    if (user.hasBadge("words3")) badge7.setEffect(colorAdjust);
    if (user.hasBadge("words4")) badge8.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of words

    if (user.hasBadge("time1")) badge9.setEffect(colorAdjust);
    if (user.hasBadge("time2")) badge10.setEffect(colorAdjust);
    if (user.hasBadge("time3")) badge11.setEffect(colorAdjust);
    if (user.hasBadge("time4")) badge12.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of times

    if (user.hasBadge("confidence1")) badge13.setEffect(colorAdjust);
    if (user.hasBadge("confidence2")) badge14.setEffect(colorAdjust);
    if (user.hasBadge("confidence3")) badge15.setEffect(colorAdjust);
    if (user.hasBadge("confidence4")) badge16.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of confidence

    if (user.hasBadge("streak2")) badge17.setEffect(colorAdjust);
    if (user.hasBadge("streak3")) badge18.setEffect(colorAdjust);
    if (user.hasBadge("streak5")) badge19.setEffect(colorAdjust);
    if (user.hasBadge("streak10")) badge20.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of streak

    if (user.hasBadge("win5")) badge21.setEffect(colorAdjust);
    if (user.hasBadge("win10")) badge22.setEffect(colorAdjust);
    if (user.hasBadge("win20")) badge23.setEffect(colorAdjust);
    if (user.hasBadge("win50")) badge24.setEffect(colorAdjust);
    // set the respective settings for badges if the user have earned the badge of win numbers
  }

  /**
   * this method will scroll up the page of the badges replacing the pane by another pane that is
   * above it
   */
  @FXML
  private void onScrollUp() {
    // resize the shape of the icon on Up
    iconOnUp.setScaleX(1);
    iconOnUp.setScaleY(1);
    if (paneOnBadgeTwo.isVisible()) {
      // set the correct badges to be visible
      paneOnBadgeOne.setVisible(true);
      paneOnBadgeTwo.setVisible(false);
    } else if (paneOnBadgeThree.isVisible()) {
      // set the correct badges to be invisible
      paneOnBadgeTwo.setVisible(true);
      paneOnBadgeThree.setVisible(false);
    }
  }

  /**
   * this method will scroll up the page of the badges replacing the pane by another pane that is
   * below it
   */
  @FXML
  private void onScrollDown() {
    // resize the shape of the icon on down
    iconOnDown.setScaleX(1);
    iconOnDown.setScaleY(1);
    // set the correct badges to be visible
    if (paneOnBadgeOne.isVisible()) {
      paneOnBadgeOne.setVisible(false);
      paneOnBadgeTwo.setVisible(true);
    } else if (paneOnBadgeTwo.isVisible()) {
      // set the correct badges to be invisible
      paneOnBadgeTwo.setVisible(false);
      paneOnBadgeThree.setVisible(true);
    }
  }

  /** this method sets up the hover effect animation when mouse enters up image for this image */
  @FXML
  private void onEnterUp() {
    iconOnUp.setScaleX(1.1);
    iconOnUp.setScaleY(1.1);
    iconOnUp.setEffect(new Bloom(0.3));
  }

  /** this method sets up the hover effect animation when mouse exits up image for this image */
  @FXML
  private void onExitUp() {
    iconOnUp.setScaleX(1);
    iconOnUp.setScaleY(1);
    iconOnUp.setEffect(null);
  }

  /** this method sets up the hover effect animation when mouse presses up image for this image */
  @FXML
  private void onPressUp() {
    iconOnUp.setScaleX(0.9);
    iconOnUp.setScaleY(0.9);
  }

  /** this method sets up the hover effect animation when mouse enters down image for this image */
  @FXML
  private void onEnterDown() {
    iconOnDown.setScaleX(1.1);
    iconOnDown.setScaleY(1.1);
    iconOnDown.setEffect(new Bloom(0.3));
  }

  /** this method sets up the hover effect animation when mouse exits down image for this image */
  @FXML
  private void onExitDown() {
    iconOnDown.setScaleX(1);
    iconOnDown.setScaleY(1);
    iconOnDown.setEffect(null);
  }

  /** this method sets up the hover effect animation when mouse presses down image for this image */
  @FXML
  private void onPressDown() {
    iconOnDown.setScaleX(0.9);
    iconOnDown.setScaleY(0.9);
  }
}
