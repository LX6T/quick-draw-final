package nz.ac.auckland.se206;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.opencsv.exceptions.CsvException;

import ai.djl.ModelException;
import ai.djl.modality.Classifications.Classification;
import ai.djl.translate.TranslateException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import nz.ac.auckland.se206.dictionary.Dictionary;
import nz.ac.auckland.se206.dictionary.WordNotFoundException;
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.user.GameData;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.SettingsData;
import nz.ac.auckland.se206.util.TransitionUtils;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;

/**
 * This is the controller of the canvas. You are free to modify this class and
 * the corresponding FXML file as you see fit. For example, you might no longer
 * need the "Predict" button because the DL model should be automatically
 * queried in the background every second.
 *
 * <p>
 * !! IMPORTANT !!
 *
 * <p>
 * Although we added the scale of the image, you need to be careful when
 * changing the size of the drawable canvas and the brush size. If you make the
 * brush too big or too small with respect to the canvas size, the ML model will
 * not work correctly. So be careful. If you make some changes in the canvas and
 * brush sizes, make sure that the prediction works fine.
 */
public class CanvasController {

	private int interval = 59;
	private double currentX;
	private double currentY;

	private Timer timer = new Timer();
	private Boolean score = false;
	private javafx.event.EventHandler<MouseEvent> onRunEvent = new javafx.event.EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			graphic.clearRect(event.getX() - 5 / 2, event.getY() - 5 / 2, 10, 10);
		}
	};
	private javafx.event.EventHandler<MouseEvent> onRunEventTwo = new javafx.event.EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			canvas.setOnMousePressed(e -> {
				currentX = e.getX();
				currentY = e.getY();
			});

			canvas.setOnMouseDragged(e -> {
				// Brush size (you can change this, it should not be too small or too large).
				final double size = 6;

				final double x = e.getX() - size / 2;
				final double y = e.getY() - size / 2;

				// This is the colour of the brush.
				graphic.setFill(Color.BLACK);
				graphic.setLineWidth(size);

				// Create a line that goes from the point (currentX, currentY) and (x,y)
				graphic.strokeLine(currentX, currentY, x, y);

				// update the coordinates
				currentX = x;
				currentY = y;
			});
		}
	};

	@FXML
	private Button buttonOnSave;

	@FXML
	private Button buttonOnReady;

	@FXML
	private Button buttonOnErase;

	@FXML
	private Label scoreLabel;

	@FXML
	private Button buttonOnClear;

	@FXML
	private Canvas canvas;

	@FXML
	private Button buttonOnReset;

	private GraphicsContext graphic;

	private DoodlePrediction model;

	private String currentWord;
	private int accuracyDifficulty;
	private String wordDifficulty;
	private int timeDifficulty;
	private double confidenceDifficulty;
	private boolean hiddenWordMode;

	@FXML
	private Button buttonOnBack;

	@FXML
	private Label displayText;

	@FXML
	private Label displayTextDefinition;

	@FXML
	private Label timerDisplay;

	@FXML
	private Button readyButton;

	@FXML
	private Label textToRefresh;

	@FXML
	private AnchorPane masterPane;

	@FXML
	private Button buttonHint;

	// #035526
	/**
	 * JavaFX calls this method once the GUI elements are loaded. In our case we
	 * create a listener for the drawing, and we load the ML model.
	 *
	 * @throws ModelException        If there is an error in reading the
	 *                               input/output of the DL model.
	 * @throws IOException           If the model cannot be found on the file
	 *                               system.
	 * @throws URISyntaxException
	 * @throws CsvException
	 * @throws WordNotFoundException
	 */
	public void initialize()
			throws ModelException, IOException, CsvException, URISyntaxException, WordNotFoundException {
		masterPane.setOpacity(0.2);
		// set the starting opacity setting
		fadeIn();
		// set the fade in method

		canvas.setDisable(true);
		buttonOnReset.setDisable(false);
		buttonOnSave.setDisable(true);
		buttonOnErase.setDisable(true);
		buttonOnClear.setDisable(true);

		// disable all the buttons that are shouldn't be used at the start

		SettingsData settings = ProfileRepository.getSettings();

		accuracyDifficulty = setAccuracy(settings.getAccuracyDifficulty());
		wordDifficulty = settings.getWordsDifficulty();
		timeDifficulty = setTime(settings.getTimeDifficulty());
		confidenceDifficulty = setConfidence(settings.getConfidenceDifficulty());
		hiddenWordMode = settings.isHiddenMode();
		buttonHint.setVisible(hiddenWordMode);
		buttonHint.setDisable(true);

		timerDisplay.setText(Integer.toString(timeDifficulty));
		setNewWord();
		if (hiddenWordMode) {
			String definition = Dictionary.searchDefinition(currentWord);
			displayTextDefinition.setText(definition);
		} else {
			displayText.setText(currentWord);
		}
	}

	private int setAccuracy(String difficulty) {
		// Easy -> correct word must be in the top 3 words
		int accuracy = 3;

		switch (difficulty) {

		// Medium -> correct word must be in the top 2 words
		case "Medium":
			accuracy = 2;
			break;

		// Hard -> correct word must be the top word
		case "Hard":
			accuracy = 1;
			break;
		}

		return accuracy;
	}

	private int setTime(String difficulty) {
		// Easy -> 60 seconds to draw
		int time = 60;

		switch (difficulty) {

		// Medium -> 45 seconds to draw
		case "Medium":
			time = 45;
			break;

		// Hard -> 30 seconds to draw
		case "Hard":
			time = 30;
			break;

		// Master -> 15 seconds to draw
		case "Master":
			time = 15;
			break;
		}

		return time;
	}

	private double setConfidence(String difficulty) {
		// Easy -> Minimum 1% confidence to win
		double confidence = 0.01;

		switch (difficulty) {

		// Medium -> Minimum 10% confidence to win
		case "Medium":
			confidence = 0.1;
			break;

		// Hard -> Minimum 25% confidence to win
		case "Hard":
			confidence = 0.25;
			break;

		// Master -> Minimum 50% confidence to win
		case "Master":
			confidence = 0.5;
			break;
		}

		return confidence;
	}

	@FXML
	private void setNewWord() throws IOException, URISyntaxException, CsvException, WordNotFoundException {
		int randInt;
		CategorySelector categorySelector = new CategorySelector();
		Random rand = new Random();

		// Easy difficulty allows only easy words
		Difficulty difficulty = Difficulty.E;

		switch (wordDifficulty) {

		// Medium difficulty allows easy and medium words
		case "Medium":
			randInt = rand.nextInt(2);
			if (randInt == 1) {
				difficulty = Difficulty.M;
			}
			break;

		// Hard difficulty allows easy, medium and hard words
		case "Hard":
			randInt = rand.nextInt(3);
			if (randInt == 1) {
				difficulty = Difficulty.M;
			} else if (randInt == 2) {
				difficulty = Difficulty.H;
			}
			break;

		// Master difficulty allows only hard words
		case "Master":
			difficulty = Difficulty.H;
			break;
		}

		// set and display the randomly chosen word
		currentWord = categorySelector.generateRandomCategory(difficulty);
	}

	private void fadeIn() {
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		ft.setFromValue(0.2);
		ft.setToValue(1);
		ft.play();
	}

	/** This method is called when the "Clear" button is pressed. */
	@FXML
	private void onClear() {
		graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * This method executes when the user clicks the "Predict" button. It gets the
	 * current drawing, queries the DL model and prints on the console the top 5
	 * predictions of the DL model and the elapsed time of the prediction in
	 * milliseconds.
	 *
	 * @throws TranslateException If there is an error in reading the input/output
	 *                            of the DL model.
	 */
	private boolean onPredict() throws TranslateException {
		boolean win;

		// get the initial system time
		List<Classification> predictionResult = model.getPredictions(getCurrentSnapshot(), 10);
		// produce 10 predictions based on the current drawing

		List<Classification> result = model.getPredictions(getCurrentSnapshot(), accuracyDifficulty);
		StringBuilder sb = DoodlePrediction.givePredictions(predictionResult);
		// use a string builder class to build the string
		textToRefresh.setText(sb.toString().replaceAll("_", " "));
		// replace underscores with spaces
		win = isWin(result);
		if (win) {
			this.score = true;
			// calculate the score
		}

		return this.score;
	}

	private boolean isWin(List<Classification> classifications) {
		// this method will tell whether the current prediction has won or not
		for (Classification classification : classifications) {
			// Prediction must both match current word and be above the minimum confidence
			// probability
			if (classification.getClassName().equals(currentWord)
					&& classification.getProbability() >= confidenceDifficulty) {
				return true;
			}
		}
		return false;
		// return false if lost and return true if won
	}

	/**
	 * Get the current snapshot of the canvas.
	 *
	 * @return The BufferedImage corresponding to the current canvas content.
	 */
	protected BufferedImage getCurrentSnapshot() {
		final Image snapshot = canvas.snapshot(null, null);
		final BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

		// Convert into a binary image.
		final BufferedImage imageBinary = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

		final Graphics2D graphics = imageBinary.createGraphics();

		graphics.drawImage(image, 0, 0, null);

		// To release memory we dispose.
		graphics.dispose();

		return imageBinary;
	}

	@FXML
	private void onBack(ActionEvent event) {
		timer.cancel();
		// stop the tasks that are allocated to the timer
		fadeOutTwo(event);
	}

	private void fadeOutTwo(ActionEvent event) {
		FadeTransition ft = TransitionUtils.getFadeTransition(masterPane);
		ft.setOnFinished((ActionEvent eventTwo) -> loadPageScene(event));
		ft.play();
	}

	private void loadPageScene(ActionEvent event) {
		Button button = (Button) event.getSource();
		Scene sceneButtonIsIn = button.getScene();

		try {
			// load the canvas scene when press this button
			sceneButtonIsIn.setRoot(App.loadFxml("page"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onReady() throws ModelException, IOException {
		this.interval = timeDifficulty - 1;
		// this variable is set so that every time this method is called, the timer
		// value can be reset.
		canvas.setDisable(false);
		buttonOnReady.setDisable(true);
		buttonOnErase.setDisable(false);
		buttonOnClear.setDisable(false);
		buttonHint.setDisable(!hiddenWordMode);
		model = new DoodlePrediction();
		TextToSpeech speaker = new TextToSpeech();
		speaker.speak("The game starts");
		speaker.speak("Good Luck");

		// when the ready button is pressed, show text to speech feature

		timer.scheduleAtFixedRate(new TimerTask() {
			// set a rate that perform once every one second

			private boolean score;

			@Override
			public void run() {
				if (interval > 0 && !score) {
					// update the text on the Timer Label
					Platform.runLater(() -> timerDisplay.setText(Integer.toString(interval)));
					Platform.runLater(() -> {
						try {
							score = onPredict();
						} catch (TranslateException e) {
							e.printStackTrace();
						}
					});
					// predict to get results refreshing each second
					interval--;
				} else {
					// when the time runs out, everything stops
					timer.cancel();
					canvas.setDisable(true);
					buttonOnSave.setDisable(false);
					buttonOnErase.setDisable(true);
					buttonOnClear.setDisable(true);

					TextToSpeech speaker = new TextToSpeech();
					if (!score) {
						// speak to user when detected result is lost
						speaker.speak("You Have Lost");
						canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEvent);
						canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEventTwo);
						Platform.runLater(() -> scoreLabel.setText("LOST"));

					} else {
						// speak to user when detected result is won
						speaker.speak("You have Won");
						canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEvent);
						canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEventTwo);
						Platform.runLater(() -> scoreLabel.setText("WON"));
					}

					// Updates the current user's profile with the data from this game
					GameData gameData = new GameData(currentWord, score, timeDifficulty - interval);
					ProfileRepository.updateUserStats(gameData);
				}
			}
		}, 1000, 1000);

		graphic = canvas.getGraphicsContext2D();

		// initialize the canvas to only allow user to draw after pressing ready
		canvas.setOnMousePressed(e -> {
			currentX = e.getX();
			currentY = e.getY();
		});

		canvas.setOnMouseDragged(e -> {
			// Brush size (you can change this, it should not be too small or too large).
			final double size = 6;

			final double x = e.getX() - size / 2;
			final double y = e.getY() - size / 2;

			// This is the colour of the brush.
			graphic.setFill(Color.BLACK);
			graphic.setLineWidth(size);

			// Create a line that goes from the point (currentX, currentY) and (x,y)
			graphic.strokeLine(currentX, currentY, x, y);

			// update the coordinates
			currentX = x;
			currentY = y;
		});
	}

	@FXML
	private void onReset(ActionEvent event) {
		timer.cancel();
		// stop the count down timer to count down.
		Button button = (Button) event.getSource();
		Scene sceneButtonIsIn = button.getScene();
		try {
			sceneButtonIsIn.setRoot(App.loadFxml("canvas"));
			// reload the scene and get everything refreshed.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onErase(ActionEvent event) {
		graphic = canvas.getGraphicsContext2D();
		// get the current canvas graphic

		if (buttonOnErase.getText().equals("Eraser")) {
			// switch the text on the button every time we click on it
			canvas.setOnMousePressed(e -> {
				currentX = e.getX();
				currentY = e.getY();
			});

			canvas.setOnMouseDragged(e -> {
				// Brush size (you can change this, it should not be too small or too large).
				final double size = 6;

				final double x = e.getX() - size / 2;
				final double y = e.getY() - size / 2;

				// This is the colour of the brush.
				graphic.clearRect(currentX - 2.5, currentY - 2.5, 10, 10);

				// update the coordinates
				currentX = x;
				currentY = y;
			});

			canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEventTwo);
			canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEvent);
			buttonOnErase.setText("Pencil");
			// update the text on the button
		} else if (buttonOnErase.getText().equals("Pencil")) {
			// remove event to stop erasing
			canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEvent);
			canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, onRunEventTwo);

			buttonOnErase.setText("Eraser");
			// update the text on the button
		}
	}

	@FXML
	private void onProvideHint() {
		displayTextDefinition.setText("");
		displayText.setText(currentWord);
	}

	@FXML
	protected void saveToFiles() throws IOException {
		FileChooser fc = new FileChooser();
		Stage stage = new Stage();
		File imageToClassify = fc.showSaveDialog(stage);

		// Save the image to a file.
		ImageIO.write(getCurrentSnapshot(), "bmp", imageToClassify);
	}
}
