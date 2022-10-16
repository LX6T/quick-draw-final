package nz.ac.auckland.se206.util;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/** This class supplies fade transitions for in between scenes. */
public class TransitionUtils {
  public static FadeTransition getFadeTransition(Node node, int duration, double from, double to) {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(duration));
    ft.setNode(node);
    ft.setFromValue(from);
    ft.setToValue(to);
    return ft;
  }
}
