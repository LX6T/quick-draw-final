package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.user.UserProfile;

public class StatsController {
  @FXML private Label labelHistory;
  @FXML private Label labelWins;
  @FXML private Label labelLosses;
  @FXML private Label labelScore;
  @FXML private Label labelRecord;

  @FXML private Button buttonStart;

  @FXML
  public void setStats(UserProfile user) {
    if (Objects.equals(user.getWordsHistory(), "")) {
      labelHistory.setText("N/A");
    } else {
      labelHistory.setText(user.getWordsHistory());
    }

    labelWins.setText(user.getNumOfWin().toString());
    labelLosses.setText(user.getNumOfLost().toString());
    labelScore.setText(user.getScore().toString());

    if (user.getBestRecord() == null) {
      labelRecord.setText("N/A");
    } else {
      labelRecord.setText(user.getBestRecord());
    }
  }

  @FXML
  private void onStart(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
