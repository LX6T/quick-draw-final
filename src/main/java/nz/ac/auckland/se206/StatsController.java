package nz.ac.auckland.se206;

import java.io.IOException;
import java.util.Objects;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.UserProfile;

public class StatsController {
	@FXML
	private Label labelHistory;
	@FXML
	private Label labelWins;
	@FXML
	private Label labelLosses;
	@FXML
	private Label labelScore;
	@FXML
	private Label labelRecord;
	@FXML
	private Button buttonOnBack;

	@FXML
	private JFXButton buttonOnStart;

	@FXML
	private AnchorPane masterPane;

	public void initialize() {
		masterPane.setOpacity(0.2);
		fadeIn();
	}

	private void fadeIn() {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(0.2);
		ft.setToValue(1);
		ft.play();
	}

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
			labelRecord.setText(user.getBestRecord() + "s");
		}
	}

	@FXML
	private void back(ActionEvent event) {
		fadeOutTwo(event);

	}

	@FXML
	private void onStart(ActionEvent event) {
		fadeOut(event);

	}

	private void fadeOut(ActionEvent event) {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(1);
		ft.setToValue(0.2);
		ft.setOnFinished((ActionEvent eventTwo) -> {
			loadNextScene(event);
		});
		ft.play();

	}

	private void fadeOutTwo(ActionEvent event) {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(1);
		ft.setToValue(0.2);
		ft.setOnFinished((ActionEvent eventTwo) -> {
			loadNextSceneTwo(event);
		});
		ft.play();

	}
s	private void loadNextScene(ActionEvent event) {
		Button button = (Button) event.getSource();
		Scene sceneButtonIsIn = button.getScene();

		try {
			// load the canvas scene when press this button
			sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadNextSceneTwo(ActionEvent event) {
		Button button = (Button) event.getSource();
		Scene sceneButtonIsIn = button.getScene();

		try {
			// load the canvas scene when press this button
			sceneButtonIsIn.setRoot(App.loadFxml("page"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
