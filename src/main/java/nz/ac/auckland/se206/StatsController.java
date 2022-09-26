package nz.ac.auckland.se206;

import javafx.fxml.FXML;
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
  public void initialise() {
    UserProfile user = PageController.currentUser;
    System.out.println(user.getAccountName());
  }

  @FXML
  private void onStart() {}
}
