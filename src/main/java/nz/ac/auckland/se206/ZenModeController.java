package nz.ac.auckland.se206;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.opencsv.exceptions.CsvException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;

public class ZenModeController extends CanvasController implements Initializable {

	@FXML
	private ImageView imageOnBack;
	@FXML
	private ImageView imageOnClear;
	@FXML
	private Canvas canvas;
	@FXML
	private Label textToRefresh;
	@FXML
	private AnchorPane masterPane;
	@FXML
	private ImageView imageOnRubber;
	@FXML
	private ImageView imageOnSave;
	@FXML
	private ImageView imageOnReset;
	@FXML
	private ImageView imageOnOne;
	@FXML
	private ImageView imageOnTwo;
	@FXML
	private ImageView imageOnThree;
	@FXML
	private ImageView imageOnFour;
	@FXML
	private ImageView imageOnFive;
	@FXML
	private ImageView imageOnSix;
	@FXML
	private ImageView imageOnSeven;
	@FXML
	private ImageView imageOnEight;
	@FXML
	private ImageView imageOnNine;
	@FXML
	private ImageView imageOnTen;
	@FXML
	private ImageView imageOnEleven;
	@FXML
	private ImageView imageOnTwelve;

	private Bloom bloom = new Bloom(0.3);

	private GraphicsContext graphic;

	private double currentX;
	private double currentY;

	URL cursorURL = App.class.getResource("/images/" + "middle-ages-custom-cursor.png");

	Image image = new Image(cursorURL.toExternalForm());

	private URL cursorURLOne = App.class.getResource("/images/" + "icons8-pen-99.png");

	private Image imageOne = new Image(cursorURLOne.toExternalForm());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		masterPane.setCursor(new ImageCursor(image, 2.5, 2.5));
		canvas.setCursor(new ImageCursor(imageOne, 2.5, 2.5));
		// set cursors for master pane and canvas

		CategorySelector categorySelector = null;
		try {
			categorySelector = new CategorySelector();
		} catch (IOException e1) {
			// throws exceptions
			e1.printStackTrace();
		} catch (CsvException e1) {
			// throws exceptions
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// throws exceptions
			e1.printStackTrace();
		}
		String randomWord = categorySelector.generateRandomCategory(Difficulty.E);
		// choose difficulty Easy as the start
		textToRefresh.setText(randomWord);

		graphic = canvas.getGraphicsContext2D();
		// get the graphic content from the canvas to initialize
		canvas.setOnMousePressed(e -> {
			currentX = e.getX();
			currentY = e.getY();
		});

		canvas.setOnMouseDragged(e -> {
			// Brush size (you can change this, it should not be too small or too large).
			final double size = 4;

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

	/**
	 * this method will change the scene of the app and adjust the size of the image
	 * due to hover effect
	 */
	@FXML
	private void onClickBack() {
		setZoomOnClick(imageOnBack);
		Scene scene = imageOnBack.getScene();
		// this will get the current scene the image is in and set this scene to be the
		// next scene
		try {
			scene.setRoot(App.loadFxml("profilePage"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the back image view when entered
	 */
	@FXML
	private void onEnterBack() {
		setZoomOnEnter(imageOnBack);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the back image view when leaved
	 */
	@FXML
	private void onLeaveBack() {
		setZoomOnLeave(imageOnBack);

	}

	/**
	 * this method will set up the hover effect animation when the back button is
	 * pressed
	 */
	@FXML
	private void onPressBack() {
		setZoomOnPress(imageOnBack);

	}

	/**
	 * this method will change the clear the canvas and adjust the size of the image
	 * on due to hover effect
	 */
	@FXML
	private void onClickClear() {
		setZoomOnClick(imageOnClear);
		graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

	}

	/**
	 * this method will set up the hover effect animation when the clear button is
	 * pressed
	 */
	@FXML
	private void onPressClear() {
		setZoomOnPress(imageOnClear);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the clear image view when entered
	 */
	@FXML
	private void onEnterClear() {
		setZoomOnEnter(imageOnClear);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the clear image view when leaved
	 */
	@FXML
	private void onExitClear() {
		setZoomOnLeave(imageOnClear);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the rubber image view when clicked
	 */
	@FXML
	private void onClickRubber() {
		setZoomOnClick(imageOnRubber);
		graphic.setStroke(Color.WHITE);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the rubber image view when entered
	 */
	@FXML
	private void onEnterRubber() {
		setZoomOnEnter(imageOnRubber);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the rubber image view when leaved
	 */
	@FXML
	private void onExitRubber() {
		setZoomOnLeave(imageOnRubber);
	}

	/**
	 * this is the actual event handler function that will handle the hover effect
	 * of the rubber image view when pressed
	 */
	@FXML
	private void onPressRubber() {
		setZoomOnPress(imageOnRubber);
	}

	/**
	 * this method is the saving function of the app and is also responsible for
	 * completing the hover effect for save button
	 * 
	 * @return a File which will be stored in your local drive
	 * @throws IOException
	 */
	@FXML
	private File onClickSave() throws IOException {
		setZoomOnClick(imageOnSave);
		Task<Void> backgroundTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				TextToSpeech speaker = new TextToSpeech();
				speaker.speak("Saved to T M P Files");

				return null;
			}
		};
		Thread backgroundThread = new Thread(backgroundTask);
		backgroundThread.start();
		return saveCurrentSnapshotOnFile();
	}

	/**
	 * this method is the one that is responsible of the hover effect for save
	 * button when mouse enters
	 */
	@FXML
	private void onEnterSave() {
		setZoomOnEnter(imageOnSave);
	}

	/**
	 * this method is the one that is responsible of the hover effect for save
	 * button when mouse leaves
	 */
	@FXML
	private void onExitSave() {
		setZoomOnLeave(imageOnSave);
	}

	/**
	 * this method is the one that is responsible of the hover effect for save
	 * button when mouse presses
	 */
	@FXML
	private void onPressSave() {
		setZoomOnPress(imageOnSave);
	}

	/**
	 * this method will reset the text to refresh label and generate a new word
	 * based on that and is also responsible for hovering effect
	 */
	@FXML
	private void onClickReset() {
		setZoomOnClick(imageOnReset);
		String randomWord = null;
		CategorySelector categorySelector = null;
		try {
			categorySelector = new CategorySelector();
		} catch (IOException e1) {
			// throws exceptions
			e1.printStackTrace();
		} catch (CsvException e1) {
			// throws exceptions
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// throws exceptions
			e1.printStackTrace();
		}
		try {
			randomWord = categorySelector.generateRandomCategory(Difficulty.E);
		} catch (Exception e) {
			// throws exceptions
			e.printStackTrace();
		}
		// choose difficulty Easy as the start
		textToRefresh.setText(randomWord);
	}

	/**
	 * this method sets up the hovering effect for the reset button when mouse
	 * enters
	 */
	@FXML
	private void onEnterReset() {
		setZoomOnEnter(imageOnReset);
	}

	/**
	 * this method sets up the hovering effect for the reset button when mouse
	 * leaves
	 */
	@FXML
	private void onExitReset() {
		setZoomOnLeave(imageOnReset);
	}

	/**
	 * this method sets up the hovering effect for the reset button when mouse
	 * presses
	 */
	@FXML
	private void onPressReset() {
		setZoomOnPress(imageOnReset);
	}

	/**
	 * this method will zoom in the image when the mouse is entered and add bloom
	 * effect onto it
	 * 
	 * @param image An image view
	 */
	private void setZoomOnEnter(ImageView image) {
		image.setScaleX(1.1);
		image.setScaleY(1.1);
		image.setEffect(bloom);

	}

	/**
	 * this method will zoom out the image when the mouse is exited and remove bloom
	 * effect that is on it
	 * 
	 * @param image an image view
	 */
	private void setZoomOnLeave(ImageView image) {
		image.setScaleX(1);
		image.setScaleY(1);
		image.setEffect(null);
	}

	/**
	 * this method will zoom out the image when the mouse is pressed and no changes
	 * to the effect
	 * 
	 * @param image an image view
	 */
	private void setZoomOnPress(ImageView image) {
		image.setScaleX(0.9);
		image.setScaleY(0.9);
	}

	/**
	 * this method will return the original size of the image and do no changes to
	 * the effect
	 * 
	 * @param image an image view
	 */
	private void setZoomOnClick(ImageView image) {
		image.setScaleX(1);
		image.setScaleY(1);
	}

}
