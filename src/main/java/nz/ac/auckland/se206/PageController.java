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

public class PageController implements Initializable {
  @FXML private AnchorPane masterPane;
  @FXML private JFXSlider sliderOnBrightness;
  @FXML private JFXSlider sliderOnVolume;
  @FXML private JFXToggleButton buttonOnBrightness;
  @FXML private JFXToggleButton buttonOnVolume;
  @FXML private JFXButton buttonOnMode;

  private static boolean musicIsOn;
  private static ColorAdjust colorAdjust = new ColorAdjust();
  private static URL musicURL = App.class.getResource("/sounds/" + "ForestWalk-320bit.mp3");
  private static Media backgroundMusic;

  static {
    assert musicURL != null;
    backgroundMusic = new Media(musicURL.toExternalForm());
  }

  private static MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusic);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (musicIsOn) {
      masterPane.setOpacity(0.2);
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

  @FXML
  private void onExit() {
    Platform.exit();
    System.exit(0);
  }

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

  @FXML
  private void onSignIn(ActionEvent event) {
    fadeOutToUser(event);
  }

  private void fadeOutToUser(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadUserScene(event));
    ft.play();
  }

  private void fadeOutToZen(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadZenScene(event));
    ft.play();
  }

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

  private void loadUserScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      sceneButtonIsIn.setRoot(App.loadFxml("user"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fadeIn() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 0.2, 1);
    ft.play();
  }

  @FXML
  private void onLoad(ActionEvent event) {
    fadeOutToZen(event);
  }
}
