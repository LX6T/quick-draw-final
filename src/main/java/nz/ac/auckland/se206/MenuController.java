package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MenuController {

  @FXML private Button buttonOnStart;
  @FXML private Button buttonOnExit;
  @FXML private Circle circleOne;
  @FXML private Circle circleTwo;
  @FXML private Circle circleThree;
  @FXML private Circle circleFour;

  public void initialize() {

    // rotate these circles once initialize the whole menu scene

    setRotate(circleOne, 90, 0, 5, 1);
    setRotate(circleTwo, 180, 0, 5, 2);
    setRotate(circleThree, 360, 0, 5, 3);
    setRotate(circleFour, 360, 0, 5, 3);
  }

  @FXML
  private void exitGame() {
    Platform.exit();
    System.exit(0);
  }

  @FXML
  private void startNewGame(ActionEvent event) throws InterruptedException {

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void setRotate(Circle circle, int angle, int delay, int duration, int rate) {
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), circle);
    // initialize the animation class

    // set the parameters for the animation
    rotateTransition.setAutoReverse(true);
    // reverse of the rotation
    rotateTransition.setRate(rate);
    // rate of rotation
    rotateTransition.setCycleCount(7);
    rotateTransition.setDelay(Duration.seconds(delay));
    // delay for the rotation
    rotateTransition.setByAngle(angle);
    rotateTransition.play();
    // start the animation
  }
}
