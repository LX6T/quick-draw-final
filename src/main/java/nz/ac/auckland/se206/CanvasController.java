package nz.ac.auckland.se206;

import ai.djl.ModelException;
import ai.djl.modality.Classifications.Classification;
import ai.djl.translate.TranslateException;
import com.opencsv.exceptions.CsvException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.imageio.ImageIO;
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.user.GameData;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.util.TransitionUtils;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;

/**
 * This is the controller of the canvas. You are free to modify this class and the corresponding
 * FXML file as you see fit. For example, you might no longer need the "Predict" button because the
 * DL model should be automatically queried in the background every second.
 *
 * <p>!! IMPORTANT !!
 *
 * <p>Although we added the scale of the image, you need to be careful when changing the size of the
 * drawable canvas and the brush size. If you make the brush too big or too small with respect to
 * the canvas size, the ML model will not work correctly. So be careful. If you make some changes in
 * the canvas and brush sizes, make sure that the prediction works fine.
 */
public class CanvasController {

  private int interval = 59;
  private double currentX;
  private double currentY;
  private final Timer timer = new Timer();
  private Boolean score = false;
  private final javafx.event.EventHandler<MouseEvent> mouseEvent =
      new javafx.event.EventHandler<>() {

        @Override
        public void handle(MouseEvent event) {
          graphic.clearRect(event.getX() - 2.5, event.getY() - 2.5, 10, 10);
        }
      };
  private final javafx.event.EventHandler<MouseEvent> mouseEventTwo =
      event -> CanvasController.this.handle();

  private void handle() {
    canvas.setOnMousePressed(
        e -> {
          currentX = e.getX();
          currentY = e.getY();
        });

    canvas.setOnMouseDragged(
        e -> {
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

  @FXML private Button buttonOnSave;

  @FXML private Button buttonOnReady;

  @FXML private Button buttonOnErase;

  @FXML private Label scoreLabel;

  @FXML private Button buttonOnClear;

  @FXML private Canvas canvas;

  @FXML private Button buttonOnReset;

  private GraphicsContext graphic;

  private DoodlePrediction model;

  private String currentWord;

  @FXML private Label displayText;

  @FXML private Label timerDisplay;

  @FXML private Label textToRefresh;

  @FXML private AnchorPane masterPane;

  // #035526
  /**
   * JavaFX calls this method once the GUI elements are loaded. In our case we create a listener for
   * the drawing, and we load the ML model.
   *
   * @throws IOException If the model cannot be found on the file system.
   * @throws URISyntaxException If a string could not be parsed as a URI reference
   * @throws CsvException If there is a problem with the CSV file
   */
  public void initialize() throws IOException, CsvException, URISyntaxException {
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
    CategorySelector categorySelector = new CategorySelector();
    String randomWord = categorySelector.generateRandomCategory(Difficulty.E);
    // choose difficulty Easy as the start
    this.currentWord = randomWord;
    displayText.setText(randomWord);
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
   * This method executes when the user clicks the "Predict" button. It gets the current drawing,
   * queries the DL model and prints on the console the top 5 predictions of the DL model and the
   * elapsed time of the prediction in milliseconds.
   *
   * @throws TranslateException If there is an error in reading the input/output of the DL model.
   */
  private boolean onPredict() throws TranslateException {
    boolean win;

    // get the initial system time
    List<Classification> predictionResult = model.getPredictions(getCurrentSnapshot(), 10);
    // produce 10 predictions based on the current drawing

    List<Classification> result = model.getPredictions(getCurrentSnapshot(), 3);
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
      if (classification.getClassName().equals(currentWord)) {
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
  private BufferedImage getCurrentSnapshot() {
    final Image snapshot = canvas.snapshot(null, null);
    final BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

    // Convert into a binary image.
    final BufferedImage imageBinary =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

    final Graphics2D graphics = imageBinary.createGraphics();

    graphics.drawImage(image, 0, 0, null);

    // To release memory we dispose.
    graphics.dispose();

    return imageBinary;
  }

  @FXML
  private void backToMenu(ActionEvent event) {
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
    this.interval = 59;
    // this variable is set so that every time this method is called, the timer
    // value can be reset.
    canvas.setDisable(false);
    buttonOnReady.setDisable(true);
    buttonOnErase.setDisable(false);
    buttonOnClear.setDisable(false);
    model = new DoodlePrediction();
    TextToSpeech speaker = new TextToSpeech();
    speaker.speak("The game starts");
    speaker.speak("Try to Draw a " + currentWord);

    // when the ready button is pressed, show text to speech feature

    timer.scheduleAtFixedRate(
        new TimerTask() {
          // set a rate that perform once every one second

          private boolean score;

          @Override
          public void run() {
            if (interval > 0 && !score) {
              // update the text on the Timer Label
              Platform.runLater(() -> timerDisplay.setText(Integer.toString(interval)));
              Platform.runLater(
                  () -> {
                    try {
                      score = onPredict();
                    } catch (TranslateException e) {
                      e.printStackTrace();
                    }
                  });
              // predict to get results refreshing each second
              interval--;
            } else {
              // when the 60 seconds timer is exceeded, everything stops
              timer.cancel();
              canvas.setDisable(true);
              buttonOnSave.setDisable(false);
              buttonOnErase.setDisable(true);
              buttonOnClear.setDisable(true);

              TextToSpeech speaker = new TextToSpeech();
              if (!score) {
                // speak to user when detected result is lost
                speaker.speak("You Have Lost");
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent);
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventTwo);
                Platform.runLater(() -> scoreLabel.setText("LOST"));

              } else {
                // speak to user when detected result is won
                speaker.speak("You have Won");
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent);
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventTwo);
                Platform.runLater(() -> scoreLabel.setText("WON"));
              }

              // Updates the current user's profile with the data from this game
              GameData gameData = new GameData(currentWord, score, 60 - interval);
              ProfileRepository.updateUserData(gameData);
            }
          }
        },
        1000,
        1000);

    graphic = canvas.getGraphicsContext2D();

    // initialize the canvas to only allow user to draw after pressing ready
    CanvasController.this.handle();
  }

  @FXML
  private void resetButton(ActionEvent event) {
    timer.cancel();
    // stop the countdown timer to count down.
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
  private void eraseAction() {
    graphic = canvas.getGraphicsContext2D();
    // get the current canvas graphic

    if (buttonOnErase.getText().equals("Eraser")) {
      // switch the text on the button every time we click on it
      canvas.setOnMousePressed(
          e -> {
            currentX = e.getX();
            currentY = e.getY();
          });

      canvas.setOnMouseDragged(
          e -> {
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

      canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventTwo);
      canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent);
      buttonOnErase.setText("Pencil");
      // update the text on the button
    } else if (buttonOnErase.getText().equals("Pencil")) {
      // remove event to stop erasing
      canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent);
      canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventTwo);

      buttonOnErase.setText("Eraser");
      // update the text on the button
    }
  }

  @FXML
  private void saveToFiles() throws IOException {
    FileChooser fc = new FileChooser();
    Stage stage = new Stage();
    File imageToClassify = fc.showSaveDialog(stage);

    // Save the image to a file.
    ImageIO.write(getCurrentSnapshot(), "bmp", imageToClassify);
  }
}
