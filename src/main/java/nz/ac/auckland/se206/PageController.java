package nz.ac.auckland.se206;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import nz.ac.auckland.se206.util.TransitionUtils;

/**
 * This class is a controller for the main menu page that you see when you first open the game. From
 * here you can navigate to the user profiles page, play as guest, or exit the game.
 */
public class PageController implements Initializable {
  @FXML private AnchorPane masterPane;
  @FXML private JFXSlider sliderOnBrightness;
  @FXML private JFXSlider sliderOnVolume;
  @FXML private JFXToggleButton buttonOnBrightness;
  @FXML private JFXToggleButton buttonOnVolume;
  @FXML private JFXButton buttonOnMode;

  private static boolean musicIsOn;
  private ColorAdjust colorAdjust = new ColorAdjust();
  private static URL musicURL = App.class.getResource("/sounds/" + "ForestWalk-320bit.mp3");
  private static Media backgroundMusic;

  static {
    assert musicURL != null;
    backgroundMusic = new Media(musicURL.toExternalForm());
  }

  private MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusic);

  /**
   * This method initialises the menu page scene run before any others are executed
   *
   * @param location is the URL location
   * @param resources are the resources needed
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (musicIsOn) {
      masterPane.setOpacity(0.2);
      // set the opacity of the master pane
      fadeIn();
    }
    sliderOnBrightness.setValue(50);
    sliderOnVolume.setValue(50);
    masterPane.setEffect(colorAdjust);
    // this is to prevent mediaPlayer being played twice
    // the media player can only be played once
    if (!musicIsOn) {
      sliderOnVolume
          .valueProperty()
          .addListener(observable -> mediaPlayer.setVolume(sliderOnVolume.getValue() / 100));

      sliderOnBrightness
          .valueProperty()
          .addListener(
              observable -> colorAdjust.setBrightness((sliderOnBrightness.getValue() - 50) / 50));
      mediaPlayer.play();
    }
    musicIsOn = true;
  }

  /** This method exits the game of the platform and the system ending all the events */
  @FXML
  private void onExit() {
    Platform.exit();
    System.exit(0);
  }

  /** This method resets the volume to the default value, 50% which is also 50 on the bar */
  @FXML
  private void onAutoVolume() {
    // set the brightness to 50 when selected
    if (buttonOnVolume.isSelected()) {
      sliderOnVolume.setValue(50);
      mediaPlayer.setVolume(0.5);
    } else {
      mediaPlayer.setVolume(sliderOnVolume.getValue());
    }
  }

  /** This method resets the brightness to the default value, 50% which is also 50 on the bar */
  @FXML
  private void onAutoBrightness() {
    // set the brightness to 50 when selected
    if (buttonOnBrightness.isSelected()) {
      sliderOnBrightness.setValue(50);
      colorAdjust.setBrightness(0);
      masterPane.setEffect(colorAdjust);
    } else {
      masterPane.setEffect(colorAdjust);
    }
  }

  /**
   * This method triggers the transition to the user select screen.
   *
   * @param event is the click event
   */
  @FXML
  private void onSignIn(ActionEvent event) {
    fadeOutToUser(event);
  }

  /**
   * This method transitions the scene to the user select screen.
   *
   * @param event is the click event
   */
  private void fadeOutToUser(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadUserScene(event));
    ft.play();
  }

  /**
   * This method transitions the scene to the zen mode screen
   *
   * @param event is the click event
   */
  private void fadeOutToZen(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadZenScene(event));
    ft.play();
  }

  /**
   * This method loads the zen mode screen.
   *
   * @param event is the click event
   */
  private void loadZenScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("zenMode"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method loads the user select screen.
   *
   * @param event is the click event
   */
  private void loadUserScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      sceneButtonIsIn.setRoot(App.loadFxml("user"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** This scene handles the fade in animation when the scene is loaded */
  private void fadeIn() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 0.2, 1);
    ft.play();
  }

  /**
   * This method triggers the transition to the zen mode screen.
   *
   * @param event is the click event
   */
  @FXML
  private void onLoad(ActionEvent event) {
    fadeOutToZen(event);
  }
}
