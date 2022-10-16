package nz.ac.auckland.se206;

import ai.djl.ModelException;
import ai.djl.modality.Classifications.Classification;
import ai.djl.translate.TranslateException;
import com.jfoenix.controls.JFXButton;
import com.opencsv.exceptions.CsvException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
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
 * This class is a controller for the canvas page, where the game is played. The user can draw on,
 * erase from, and reset the canvas, as well as a few other functions.
 */
public class CanvasController extends App {

  private int interval = 59;
  private double currentX;
  private double currentY;

  private final Timer timer = new Timer();
  private Boolean score = false;
  private final javafx.event.EventHandler<MouseEvent> onRunEvent =
      new javafx.event.EventHandler<>() {
        // this event handler should clear the canvas acting as a rubber

        @Override
        public void handle(MouseEvent event) {
          graphic.clearRect(event.getX() - 2.5, event.getY() - 2.5, 10, 10);
        }
      };
  private final javafx.event.EventHandler<MouseEvent> onRunEventTwo =
      new javafx.event.EventHandler<>() {
        // this event handler should write to the canvas acting like a pen

        @Override
        public void handle(MouseEvent event) {
          canvas.setOnMousePressed(
              e -> {
                currentX = e.getX();
                currentY = e.getY();
              });
          // get the current coordinate

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
      };

  @FXML private JFXButton buttonOnSave;

  @FXML private ImageView buttonOnHint;

  @FXML private JFXButton buttonOnReady;

  @FXML private JFXButton buttonOnErase;

  @FXML private Label scoreLabel;

  @FXML private JFXButton buttonOnClear;

  @FXML private Canvas canvas;

  @FXML private JFXButton buttonOnReset;

  private GraphicsContext graphic;

  private DoodlePrediction model;
  private URL soundURL = App.class.getResource("/sounds/" + "rclick-13693.mp3");
  private Media soundMusic = new Media(soundURL.toExternalForm());

  private String currentWord;
  private int accuracyDifficulty;
  private String wordDifficulty;
  private int timeDifficulty;
  private double confidenceDifficulty;
  private boolean hiddenWordMode;
  private String definition;
  private int historyPosition;
  private int numberOfWords;
  private List<String> predictions = new ArrayList<>();

  @FXML private JFXButton buttonOnBack;

  @FXML private Label labelText;

  @FXML private Label labelTextDefinition;

  @FXML private Label timerDisplay;

  @FXML private JFXButton readyButton;

  @FXML private Label textToRefresh;

  @FXML private AnchorPane masterPane;

  @FXML private Label labelOne;
  @FXML private Label labelTwo;
  @FXML private Label labelThree;
  @FXML private Label labelFour;
  @FXML private Label labelFive;
  @FXML private Label labelSix;
  @FXML private Label labelSeven;
  @FXML private Label labelEight;
  @FXML private Label labelNine;
  @FXML private Label labelTen;
  @FXML private ImageView imageOnLoading;
  @FXML private ImageView imageSmile;
  @FXML private ImageView imageSad;

  // #035526
  /**
   * JavaFX calls this method once the GUI elements are loaded. In our case we create a listener for
   * the drawing, and we load the ML model.
   *
   * @throws ModelException If there is an error in reading the input/output of the DL model.
   * @throws IOException If the model cannot be found on the file system.
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
    // disable the correct things
    buttonOnErase.setDisable(true);
    buttonOnClear.setDisable(true);
    imageOnLoading.setVisible(false);
    imageSad.setVisible(false);
    imageSmile.setVisible(false);

    // disable all the buttons that are shouldn't be used at the start

    SettingsData settings = ProfileRepository.getSettings();

    // get all the game settings from the local repository and change the scene
    // according to these
    // settings chosen

    accuracyDifficulty = setAccuracy(settings.getAccuracyDifficulty());
    wordDifficulty = settings.getWordsDifficulty();
    timeDifficulty = setTime(settings.getTimeDifficulty());
    confidenceDifficulty = setConfidence(settings.getConfidenceDifficulty());
    hiddenWordMode = settings.isHiddenMode();
    // get the settings
    buttonOnHint.setDisable(true);
    buttonOnHint.setVisible(hiddenWordMode);

    CategorySelector cs = new CategorySelector();
    numberOfWords = cs.calculateNumOfWordsInDifficulty(wordDifficulty);
    historyPosition = numberOfWords - 1;

    timerDisplay.setText(Integer.toString(timeDifficulty));
    setNewWord();
    // set new word based on the settings

    Task<Void> backgroundTask =
        // declare a background task that performs the dictionary searching task
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            if (hiddenWordMode) {
              // show the loading image when the dictionary hasn't been loaded
              Platform.runLater(
                  () -> {
                    imageOnLoading.setVisible(true);
                  });
              definition = Dictionary.searchDefinition(currentWord);
              // search based on the dictionary
            }
            Platform.runLater(
                () -> {
                  if (hiddenWordMode) {
                    // after the dictionary has finished searching, turn the loading image into
                    // invisble
                    imageOnLoading.setVisible(false);
                    labelTextDefinition.setText(definition);
                    // set definition of the word if it is the hidden word mode
                  } else {
                    labelText.setText(currentWord);
                    // set text of the word if it is not the hidden word mode
                  }
                });
            return null;
          }
        };
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
  }

  /**
   * set accuracy which is the number of predictions allowed to win the game based on the settings
   *
   * @param difficulty a string based on the settings
   * @return an integer number of how many are allowed
   */
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

  /**
   * set the amount of time allowed in a game based on the settings in local repository
   *
   * @param difficulty which is a string from repository
   * @return an integer of the amount of time allowed
   */
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

  /**
   * this method sets the confidence level of the game based on the settings in repository
   *
   * @param difficulty which is a string from local repository
   * @return a double number of confidence level
   */
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

  /**
   * this method sets up a new word based on the difficulty settings chosen from the profile scene
   *
   * @throws IOException
   * @throws URISyntaxException
   * @throws CsvException
   * @throws WordNotFoundException
   */
  @FXML
  private void setNewWord()
      throws IOException, URISyntaxException, CsvException, WordNotFoundException {
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

  /** this methods sets up the basic fade in transition animation between different scenes */
  protected void fadeIn() {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(300));
    // duration is 300
    ft.setNode(masterPane);
    // set the node to be the master pane to perform the transition
    ft.setFromValue(0.2);
    ft.setToValue(1);
    ft.play();
  }

  /** This method is called when the "Clear" button is pressed. */
  @FXML
  private void onClear() {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
    graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /**
   * This method executes when the user clicks the "Predict" button. It gets the current drawing,
   * queries the DL model and prints on the console the top 10 predictions of the DL model and the
   * elapsed time of the prediction in milliseconds.
   *
   * @throws TranslateException If there is an error in reading the input/output of the DL model.
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

  /**
   * this method tells the user whether the game is won based on the result prediction list given
   *
   * @param classifications the prediction list
   * @return a boolean indicating yes or no
   */
  private boolean isWin(List<Classification> classifications) {
    // this method will tell whether the current prediction has won or not
    for (Classification classification : classifications) {
      // Prediction must both match current word and be above the minimum confidence
      // probability
      if (classification.getClassName().equals(currentWord)
          && classification.getProbability() >= confidenceDifficulty) {
        System.out.println(classification.getProbability());

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
    final BufferedImage imageBinary =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

    final Graphics2D graphics = imageBinary.createGraphics();

    graphics.drawImage(image, 0, 0, null);

    // To release memory we dispose.
    graphics.dispose();

    return imageBinary;
  }

  /**
   * this method sets up the fade out transition animation from the current scene to the previous
   * scene
   *
   * @param event ongoing action event
   */
  @FXML
  private void onBack(ActionEvent event) {
    timer.cancel();
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
    // stop the tasks that are allocated to the timer
    fadeOutToStatsScene(event);
  }

  /**
   * this method sets up the basic parameter for the current scene to fade out to the stats scene
   *
   * @param event an ongoing action event
   */
  private void fadeOutToStatsScene(ActionEvent event) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadStatsScene(event));
    ft.play();
  }

  /**
   * this method loads the stats scene and is used when the fade out transition animation is
   * finished
   *
   * @param event an ongoing action event
   */
  private void loadStatsScene(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();

    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("profilePage"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method sets up the timer and refreshing prediction list per second for the GUI and also
   * automatically update when the user has won or lost
   *
   * @throws ModelException
   * @throws IOException
   */
  @FXML
  private void onReady() throws ModelException, IOException {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
    this.interval = timeDifficulty - 1;
    // this variable is set so that every time this method is called, the timer
    // value can be reset.
    historyPosition = numberOfWords - 1;
    predictions.clear();
    canvas.setDisable(false);
    buttonOnReady.setDisable(true);
    buttonOnErase.setDisable(false);
    buttonOnClear.setDisable(false);
    buttonOnHint.setDisable(!hiddenWordMode);
    model = new DoodlePrediction();
    // sets up the prediction model
    Task<Void> backgroundTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            TextToSpeech speaker = new TextToSpeech();
            speaker.speak("The game starts");
            speaker.speak("Good Luck");
            // text to speech feature incorporated

            return null;
          }
        };
    // sets up the background task
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
    // start a background thread

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
              // update the timer for count down feature
              Platform.runLater(
                  () -> {
                    try {
                      score = onPredict();
                      // automatically update on the game status
                    } catch (TranslateException e) {
                      e.printStackTrace();
                    }
                  });
              // access to the platform thread
              Platform.runLater(
                  () -> {
                    try {
                      // access the javafx thread to run the timer task
                      List<Classification> predictionResult =
                          model.getPredictions(getCurrentSnapshot(), 10);
                      List<Classification> predictions =
                          model.getPredictions(getCurrentSnapshot(), numberOfWords);

                      for (Classification classification : predictions) {
                        CanvasController.this.predictions.add(classification.getClassName());
                      }
                      int currentPosition = CanvasController.this.predictions.indexOf(currentWord);
                      noticeUser(currentPosition);

                      labelOne.setText(predictionResult.get(0).getClassName());
                      // set the respective corresponding label to be the correct order in the
                      // prediction list
                      labelTwo.setText(predictionResult.get(1).getClassName());
                      labelThree.setText(predictionResult.get(2).getClassName());
                      labelFour.setText(predictionResult.get(3).getClassName());
                      labelFive.setText(predictionResult.get(4).getClassName());
                      labelSix.setText(predictionResult.get(5).getClassName());
                      labelSeven.setText(predictionResult.get(6).getClassName());
                      labelEight.setText(predictionResult.get(7).getClassName());
                      labelNine.setText(predictionResult.get(8).getClassName());
                      labelTen.setText(predictionResult.get(9).getClassName());
                      // there are exactly 10 elements in the list
                    } catch (TranslateException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                    }
                  });
              // predict to get results refreshing each second
              interval--;
              historyPosition = CanvasController.this.predictions.indexOf(currentWord);
              predictions.clear();
            } else {
              // when the time runs out, everything stops
              timer.cancel();
              canvas.setDisable(true);
              buttonOnSave.setDisable(false);
              buttonOnErase.setDisable(true);
              buttonOnClear.setDisable(true);
              // disable the respective buttons when the timer is finished

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
        },
        1000,
        1000);
    // perform at a fixed rate of one second

    graphic = canvas.getGraphicsContext2D();

    // initialize the canvas to only allow user to draw after pressing ready
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

  /**
   * this method resets the scene and the timer to get a new word of the same category
   *
   * @param event an ongoing action event
   */
  @FXML
  private void onReset(ActionEvent event) {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
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

  /**
   * this method sets up the eraser feature of the canvas and supports the switch between pencil and
   * eraser
   *
   * @param event an ongoing action event
   */
  @FXML
  private void onErase(ActionEvent event) {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
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

  /**
   * this method allows the current screen shots to be saved to the files of a destination we want
   *
   * @throws IOException
   */
  @FXML
  protected void onSave() throws IOException {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
    FileChooser fc = new FileChooser();
    Stage stage = new Stage();
    File imageToClassify = fc.showSaveDialog(stage);

    // Save the image to a file.
    ImageIO.write(getCurrentSnapshot(), "bmp", imageToClassify);
  }

  /** this method sets up the click animation of a hint when the mouse is clicked on it */
  @FXML
  private void onClickHint() {
    MediaPlayer soundPlayer = new MediaPlayer(soundMusic);
    // create Media Player
    soundPlayer.play();
    // play the sound
    buttonOnHint.setScaleX(1);
    buttonOnHint.setScaleY(1);
    if (labelText.getText() == "") {
      labelText.setText(currentWord);
      labelTextDefinition.setText("");
      // set text to be blank
    } else if (labelTextDefinition.getText() == "") {
      // give the word
      labelText.setText("");
      labelTextDefinition.setText(definition);
      // if the text does not have definitions
    }
  }

  /** this method sets up the click animation of a hint when the mouse is entered on it */
  @FXML
  private void onEnterHint() {
    buttonOnHint.setScaleX(1.1);
    buttonOnHint.setScaleY(1.1);
    buttonOnHint.setEffect(new Bloom(0.3));
  }

  /** this method sets up the click animation of a hint when the mouse is exited on it */
  @FXML
  private void onExitHint() {
    buttonOnHint.setScaleX(1);
    buttonOnHint.setScaleY(1);
    buttonOnHint.setEffect(null);
  }

  /** this method sets up the click animation of a hint when the mouse is pressed on it */
  @FXML
  private void onPressHint() {
    buttonOnHint.setScaleX(0.9);
    buttonOnHint.setScaleY(0.9);
  }

  /**
   * this methods notice the user by setting the visibility of the smile faces to inform about their
   * drawings
   *
   * @param currentPosition an integer of the position in the prediction list
   */
  private void noticeUser(int currentPosition) {

    if (currentPosition <= historyPosition) {

      // if the current position is less than the history position then its good
      imageSad.setVisible(false);
      imageSmile.setVisible(true);
    } else {
      imageSad.setVisible(true);
      // set respective visibility
      imageSmile.setVisible(false);
    }
  }
}
