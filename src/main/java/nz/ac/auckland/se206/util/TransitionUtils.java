package nz.ac.auckland.se206.util;

import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TransitionUtils {

  public static FadeTransition getFadeTransition(AnchorPane masterPane) {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(500));
    ft.setNode(masterPane);
    ft.setFromValue(1);
    ft.setToValue(0.2);
    return ft;
  }
}
