package nz.ac.auckland.se206;

import ai.djl.ModelException;
import ai.djl.modality.Classifications.Classification;
import ai.djl.translate.TranslateException;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
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
import nz.ac.auckland.se206.ml.DoodlePrediction;
import nz.ac.auckland.se206.speech.TextToSpeech;
import nz.ac.auckland.se206.words.CategorySelector;
import nz.ac.auckland.se206.words.CategorySelector.Difficulty;

public class ZenModeController extends CanvasController implements Initializable {

  @FXML private ImageView imageOnBack;
  @FXML private ImageView imageOnClear;
  @FXML private Canvas canvas;
  @FXML private Label textToRefresh;
  @FXML private AnchorPane masterPane;
  @FXML private ImageView imageOnRubber;
  @FXML private ImageView imageOnSave;
  @FXML private ImageView imageOnReset;
  @FXML private ImageView imageOnOne;
  @FXML private ImageView imageOnTwo;
  @FXML private ImageView imageOnThree;
  @FXML private ImageView imageOnFour;
  @FXML private ImageView imageOnFive;
  @FXML private ImageView imageOnSix;
  @FXML private ImageView imageOnSeven;
  @FXML private ImageView imageOnEight;
  @FXML private ImageView imageOnNine;
  @FXML private ImageView imageOnTen;
  @FXML private ImageView imageOnEleven;
  @FXML private ImageView imageOnTwelve;
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

  private final Bloom bloom = new Bloom(0.3);

  private GraphicsContext graphic;

  private double currentX;
  private double currentY;
  private DoodlePrediction model = null;

  URL cursorURL = App.class.getResource("/images/" + "middle-ages-custom-cursor.png");

  Image image;

  {
    assert cursorURL != null;
    image = new Image(cursorURL.toExternalForm());
  }

  private final URL cursorURLOne = App.class.getResource("/images/" + "icons8-pen-99.png");

  private final Image imageOne;

  {
    assert cursorURLOne != null;
    imageOne = new Image(cursorURLOne.toExternalForm());
  }

  private final URL cursorURLTwo = App.class.getResource("/images/" + "icons8-pen-99 (2).png");

  private final Image imageTwo;

  {
    assert cursorURLTwo != null;
    imageTwo = new Image(cursorURLTwo.toExternalForm());
  }

  private final URL cursorURLThree = App.class.getResource("/images/" + "icons8-pen-99 (1).png");

  private final Image imageThree;

  {
    assert cursorURLThree != null;
    imageThree = new Image(cursorURLThree.toExternalForm());
  }

  private final URL cursorURLFour = App.class.getResource("/images/" + "icons8-pen-99 (7).png");

  private final Image imageFour;

  {
    assert cursorURLFour != null;
    imageFour = new Image(cursorURLFour.toExternalForm());
  }

  private final URL cursorURLFive = App.class.getResource("/images/" + "icons8-pen-99 (6).png");

  private final Image imageFive;

  {
    assert cursorURLFive != null;
    imageFive = new Image(cursorURLFive.toExternalForm());
  }

  private final URL cursorURLSix = App.class.getResource("/images/" + "icons8-pen-99 (8).png");

  private final Image imageSix;

  {
    assert cursorURLSix != null;
    imageSix = new Image(cursorURLSix.toExternalForm());
  }

  private final URL cursorURLSeven = App.class.getResource("/images/" + "icons8-pen-99 (3).png");

  private final Image imageSeven;

  {
    assert cursorURLSeven != null;
    imageSeven = new Image(cursorURLSeven.toExternalForm());
  }

  private final URL cursorURLEight = App.class.getResource("/images/" + "icons8-pen-99 (10).png");

  private final Image imageEight;

  {
    assert cursorURLEight != null;
    imageEight = new Image(cursorURLEight.toExternalForm());
  }

  private final URL cursorURLNine = App.class.getResource("/images/" + "icons8-pen-99 (4).png");

  private final Image imageNine;

  {
    assert cursorURLNine != null;
    imageNine = new Image(cursorURLNine.toExternalForm());
  }

  private final URL cursorURLTen = App.class.getResource("/images/" + "icons8-pen-99 (9).png");

  private final Image imageTen;


  {
    assert cursorURLTen != null;
    imageTen = new Image(cursorURLTen.toExternalForm());
  }

  private final URL cursorURLEleven = App.class.getResource("/images/" + "icons8-pen-99 (5).png");

  private final Image imageEleven;

  {
    assert cursorURLEleven != null;
    imageEleven = new Image(cursorURLEleven.toExternalForm());
  }

  private final URL cursorURLTwelve = App.class.getResource("/images/" + "icons8-pen-99 (11).png");

  private final Image imageTwelve;

  {
    assert cursorURLTwelve != null;
    imageTwelve = new Image(cursorURLTwelve.toExternalForm());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    masterPane.setCursor(new ImageCursor(image, 2.5, 2.5));
    canvas.setCursor(new ImageCursor(imageOne, 2.5, 2.5));
    // set cursors for master pane and canvas

    CategorySelector categorySelector = null;
    try {
      categorySelector = new CategorySelector();
    } catch (IOException | CsvException | URISyntaxException e1) {
      // throws exceptions
      e1.printStackTrace();
    }
    assert categorySelector != null;
    String randomWord = categorySelector.generateRandomCategory(Difficulty.E);
    // choose difficulty Easy as the start
    textToRefresh.setText(randomWord);

    graphic = canvas.getGraphicsContext2D();
    // get the graphic content from the canvas to initialize
    canvas.setOnMousePressed(
        e -> {
          currentX = e.getX();
          currentY = e.getY();
        });

    canvas.setOnMouseDragged(
        e -> {
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
    Timer timer = new Timer(true);

    try {
      // initiate the machine learning model
      model = new DoodlePrediction();
    } catch (ModelException | IOException e1) {
      e1.printStackTrace();
    }
    timer.scheduleAtFixedRate(
        new TimerTask() {

          @Override
          public void run() {
            Platform.runLater(
                () -> {
                  try {
                    // access the javafx thread to run the timer task
                    List<Classification> predictionResult =
                        model.getPredictions(getCurrentSnapshot(), 10);
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
                    e.printStackTrace();
                  }
                });
          }
        },
        1000,
        1000);
    // perform at once per second

  }

  /**
   * this method will change the scene of the app and adjust the size of the image due to hover
   * effect
   */
  @FXML
  private void onClickBack() {
    setZoomOnClick(imageOnBack);
    Scene scene = imageOnBack.getScene();
    // this will get the current scene the image is in and set this scene to be the
    // next scene
    try {
      scene.setRoot(App.loadFxml("page"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the back image
   * view when entered
   */
  @FXML
  private void onEnterBack() {
    setZoomOnEnter(imageOnBack);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the back image
   * view when leaved
   */
  @FXML
  private void onLeaveBack() {
    setZoomOnLeave(imageOnBack);
  }

  /** this method will set up the hover effect animation when the back button is pressed */
  @FXML
  private void onPressBack() {
    setZoomOnPress(imageOnBack);
  }

  /**
   * this method will change the clear the canvas and adjust the size of the image on due to hover
   * effect
   */
  @FXML
  private void onClickClear() {
    setZoomOnClick(imageOnClear);
    graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  /** this method will set up the hover effect animation when the clear button is pressed */
  @FXML
  private void onPressClear() {
    setZoomOnPress(imageOnClear);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the clear image
   * view when entered
   */
  @FXML
  private void onEnterClear() {
    setZoomOnEnter(imageOnClear);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the clear image
   * view when leaved
   */
  @FXML
  private void onExitClear() {
    setZoomOnLeave(imageOnClear);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the rubber image
   * view when clicked
   */
  @FXML
  private void onClickRubber() {
    setZoomOnClick(imageOnRubber);
    graphic.setStroke(Color.WHITE);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the rubber image
   * view when entered
   */
  @FXML
  private void onEnterRubber() {
    setZoomOnEnter(imageOnRubber);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the rubber image
   * view when leaved
   */
  @FXML
  private void onExitRubber() {
    setZoomOnLeave(imageOnRubber);
  }

  /**
   * this is the actual event handler function that will handle the hover effect of the rubber image
   * view when pressed
   */
  @FXML
  private void onPressRubber() {
    setZoomOnPress(imageOnRubber);
  }

  /**
   * this method is the saving function of the app and is also responsible for completing the hover
   * effect for save button
   *
   * @throws IOException if an I/O operation fails
   */
  @FXML
  private void onClickSave() throws IOException {
    setZoomOnClick(imageOnSave);
    Task<Void> backgroundTask =
        new Task<>() {

          @Override
          protected Void call() {

            TextToSpeech speaker = new TextToSpeech();
            speaker.speak("Choose your file path");

            return null;
          }
        };
    Thread backgroundThread = new Thread(backgroundTask);
    backgroundThread.start();
    saveToFiles();
  }

  /**
   * this method is the one that is responsible for the hover effect for save button when mouse
   * enters
   */
  @FXML
  private void onEnterSave() {
    setZoomOnEnter(imageOnSave);
  }

  /**
   * this method is the one that is responsible for the hover effect for save button when mouse
   * leaves
   */
  @FXML
  private void onExitSave() {
    setZoomOnLeave(imageOnSave);
  }

  /**
   * this method is the one that is responsible for the hover effect for save button when mouse
   * presses
   */
  @FXML
  private void onPressSave() {
    setZoomOnPress(imageOnSave);
  }

  /**
   * this method will reset the text to refresh label and generate a new word based on that and is
   * also responsible for hovering effect
   */
  @FXML
  private void onClickReset() {
    setZoomOnClick(imageOnReset);
    String randomWord = null;
    CategorySelector categorySelector = null;
    try {
      categorySelector = new CategorySelector();
    } catch (IOException | CsvException | URISyntaxException e1) {
      // throws exceptions
      e1.printStackTrace();
    }
    try {
      assert categorySelector != null;
      randomWord = categorySelector.generateRandomCategory(Difficulty.E);
    } catch (Exception e) {
      // throws exceptions
      e.printStackTrace();
    }
    // choose difficulty Easy as the start
    textToRefresh.setText(randomWord);
  }

  /** this method sets up the hovering effect for the reset button when mouse enters */
  @FXML
  private void onEnterReset() {
    setZoomOnEnter(imageOnReset);
  }

  /** this method sets up the hovering effect for the reset button when mouse leaves */
  @FXML
  private void onExitReset() {
    setZoomOnLeave(imageOnReset);
  }

  /** this method sets up the hovering effect for the reset button when mouse presses */
  @FXML
  private void onPressReset() {
    setZoomOnPress(imageOnReset);
  }

  /** this method sets up the hover effect animation for pen 1 when mouse is pressed */
  @FXML
  private void onPressOne() {
    setZoomOnPress(imageOnOne);
  }

  /** this method sets up the hover effect animation for pen 1 when mouse is entered */
  @FXML
  private void onEnterOne() {
    setZoomOnEnter(imageOnOne);
  }

  /** this method sets up the hover effect animation for pen 1 when mouse is exited */
  @FXML
  private void onExitOne() {
    setZoomOnLeave(imageOnOne);
  }

  /** this method sets up the hover effect animation for pen 2 when mouse is pressed */
  @FXML
  private void onPressTwo() {
    setZoomOnPress(imageOnTwo);
  }

  /** this method sets up the hover effect animation for pen 2 when mouse is entered */
  @FXML
  private void onEnterTwo() {
    setZoomOnEnter(imageOnTwo);
  }

  /** this method sets up the hover effect animation for pen 2 when mouse is exited */
  @FXML
  private void onExitTwo() {
    setZoomOnLeave(imageOnTwo);
  }

  /** this method sets up the hover effect animation for pen 3 when mouse is pressed */
  @FXML
  private void onPressThree() {
    setZoomOnPress(imageOnThree);
  }

  /** this method sets up the hover effect animation for pen 3 when mouse is entered */
  @FXML
  private void onEnterThree() {
    setZoomOnEnter(imageOnThree);
  }

  /** this method sets up the hover effect animation for pen 3 when mouse is exited */
  @FXML
  private void onExitThree() {
    setZoomOnLeave(imageOnThree);
  }

  /** this method sets up the hover effect animation for pen 4 when mouse is pressed */
  @FXML
  private void onPressFour() {
    setZoomOnPress(imageOnFour);
  }

  /** this method sets up the hover effect animation for pen 4 when mouse is entered */
  @FXML
  private void onEnterFour() {
    setZoomOnEnter(imageOnFour);
  }

  /** this method sets up the hover effect animation for pen 4 when mouse is exited */
  @FXML
  private void onExitFour() {
    setZoomOnLeave(imageOnFour);
  }

  /** this method sets up the hover effect animation for pen 5 when mouse is pressed */
  @FXML
  private void onPressFive() {
    setZoomOnPress(imageOnFive);
  }

  /** this method sets up the hover effect animation for pen 5 when mouse is entered */
  @FXML
  private void onEnterFive() {
    setZoomOnEnter(imageOnFive);
  }

  /** this method sets up the hover effect animation for pen 5 when mouse is exited */
  @FXML
  private void onExitFive() {
    setZoomOnLeave(imageOnFive);
  }

  /** this method sets up the hover effect animation for pen 6 when mouse is pressed */
  @FXML
  private void onPressSix() {
    setZoomOnPress(imageOnSix);
  }

  /** this method sets up the hover effect animation for pen 6 when mouse is entered */
  @FXML
  private void onEnterSix() {
    setZoomOnEnter(imageOnSix);
  }

  /** this method sets up the hover effect animation for pen 6 when mouse is exited */
  @FXML
  private void onExitSix() {
    setZoomOnLeave(imageOnSix);
  }

  /** this method sets up the hover effect animation for pen 7 when mouse is pressed */
  @FXML
  private void onPressSeven() {
    setZoomOnPress(imageOnSeven);
  }

  /** this method sets up the hover effect animation for pen 7 when mouse is entered */
  @FXML
  private void onEnterSeven() {
    setZoomOnEnter(imageOnSeven);
  }

  /** this method sets up the hover effect animation for pen 7 when mouse is exited */
  @FXML
  private void onExitSeven() {
    setZoomOnLeave(imageOnSeven);
  }

  /** this method sets up the hover effect animation for pen 8 when mouse is pressed */
  @FXML
  private void onPressEight() {
    setZoomOnPress(imageOnEight);
  }

  /** this method sets up the hover effect animation for pen 8 when mouse is entered */
  @FXML
  private void onEnterEight() {
    setZoomOnEnter(imageOnEight);
  }

  /** this method sets up the hover effect animation for pen 8 when mouse is exited */
  @FXML
  private void onExitEight() {
    setZoomOnLeave(imageOnEight);
  }

  /** this method sets up the hover effect animation for pen 9 when mouse is pressed */
  @FXML
  private void onPressNine() {
    setZoomOnPress(imageOnNine);
  }

  /** this method sets up the hover effect animation for pen 89 when mouse is entered */
  @FXML
  private void onEnterNine() {
    setZoomOnEnter(imageOnNine);
  }

  /** this method sets up the hover effect animation for pen 9 when mouse is exited */
  @FXML
  private void onExitNine() {
    setZoomOnLeave(imageOnNine);
  }

  /** this method sets up the hover effect animation for pen 10 when mouse is pressed */
  @FXML
  private void onPressTen() {
    setZoomOnPress(imageOnTen);
  }

  /** this method sets up the hover effect animation for pen 10 when mouse is entered */
  @FXML
  private void onEnterTen() {
    setZoomOnEnter(imageOnTen);
  }

  /** this method sets up the hover effect animation for pen 10 when mouse is exited */
  @FXML
  private void onExitTen() {
    setZoomOnLeave(imageOnTen);
  }

  /** this method sets up the hover effect animation for pen 11 when mouse is pressed */
  @FXML
  private void onPressEleven() {
    setZoomOnPress(imageOnEleven);
  }

  /** this method sets up the hover effect animation for pen 11 when mouse is entered */
  @FXML
  private void onEnterEleven() {
    setZoomOnEnter(imageOnEleven);
  }

  /** this method sets up the hover effect animation for pen 11 when mouse is exited */
  @FXML
  private void onExitEleven() {
    setZoomOnLeave(imageOnEleven);
  }

  /** this method sets up the hover effect animation for pen 12 when mouse is pressed */
  @FXML
  private void onPressTwelve() {
    setZoomOnPress(imageOnTwelve);
  }

  /** this method sets up the hover effect animation for pen 12 when mouse is entered */
  @FXML
  private void onEnterTwelve() {
    setZoomOnEnter(imageOnTwelve);
  }

  /** this method sets up the hover effect animation for pen 12 when mouse is exited */
  @FXML
  private void onExitTwelve() {
    setZoomOnLeave(imageOnTwelve);
  }

  /** this method set up the hover animation and click animation and color selection for pen 1 */
  @FXML
  private void onClickOne() {
    setZoomOnClick(imageOnOne);
    canvas.setCursor(new ImageCursor(imageOne, 2.5, 2.5));
    graphic.setStroke(Color.BLACK);
  }

  /** this method set up the hover animation and click animation and color selection for pen 2 */
  @FXML
  private void onClickTwo() {
    setZoomOnClick(imageOnTwo);
    canvas.setCursor(new ImageCursor(imageTwo, 2.5, 2.5));
    graphic.setStroke(Color.ORANGE);
  }

  /** this method set up the hover animation and click animation and color selection for pen 3 */
  @FXML
  private void onClickThree() {
    setZoomOnClick(imageOnThree);
    canvas.setCursor(new ImageCursor(imageThree, 2.5, 2.5));
    graphic.setStroke(Color.RED);
  }

  /** this method set up the hover animation and click animation and color selection for pen 4 */
  @FXML
  private void onClickFour() {
    setZoomOnClick(imageOnFour);
    canvas.setCursor(new ImageCursor(imageFour, 2.5, 2.5));
    graphic.setStroke(Color.AQUA);
  }

  /** this method set up the hover animation and click animation and color selection for pen 5 */
  @FXML
  private void onClickFive() {
    setZoomOnClick(imageOnFive);
    canvas.setCursor(new ImageCursor(imageFive, 2.5, 2.5));
    graphic.setStroke(Color.PURPLE);
  }

  /** this method set up the hover animation and click animation and color selection for pen 6 */
  @FXML
  private void onClickSix() {
    setZoomOnClick(imageOnSix);
    canvas.setCursor(new ImageCursor(imageSix, 2.5, 2.5));
    graphic.setStroke(Color.DARKCYAN);
  }

  /** this method set up the hover animation and click animation and color selection for pen 7 */
  @FXML
  private void onClickSeven() {
    setZoomOnClick(imageOnSeven);
    canvas.setCursor(new ImageCursor(imageSeven, 2.5, 2.5));
    graphic.setStroke(Color.YELLOW);
  }

  /** this method set up the hover animation and click animation and color selection for pen 8 */
  @FXML
  private void onClickEight() {
    setZoomOnClick(imageOnEight);
    canvas.setCursor(new ImageCursor(imageEight, 2.5, 2.5));
    graphic.setStroke(Color.GREY);
  }

  /** this method set up the hover animation and click animation and color selection for pen 9 */
  @FXML
  private void onClickNine() {
    setZoomOnClick(imageOnNine);
    canvas.setCursor(new ImageCursor(imageNine, 2.5, 2.5));
    graphic.setStroke(Color.LIGHTGREEN);
  }

  /** this method set up the hover animation and click animation and color selection for pen 10 */
  @FXML
  private void onClickTen() {
    setZoomOnClick(imageOnTen);
    canvas.setCursor(new ImageCursor(imageTen, 2.5, 2.5));
    graphic.setStroke(Color.DARKGREY);
  }

  /** this method set up the hover animation and click animation and color selection for pen 101 */
  @FXML
  private void onClickEleven() {
    setZoomOnClick(imageOnEleven);
    canvas.setCursor(new ImageCursor(imageEleven, 2.5, 2.5));
    graphic.setStroke(Color.CADETBLUE);
  }

  /** this method set up the hover animation and click animation and color selection for pen 12 */
  @FXML
  private void onClickTwelve() {
    setZoomOnClick(imageOnTwelve);
    canvas.setCursor(new ImageCursor(imageTwelve, 2.5, 2.5));
    graphic.setStroke(Color.LIGHTPINK);
  }

  /**
   * this method will zoom in the image when the mouse is entered and add bloom effect onto it
   *
   * @param image An image view
   */
  private void setZoomOnEnter(ImageView image) {
    image.setScaleX(1.1);
    image.setScaleY(1.1);
    image.setEffect(bloom);
  }

  /**
   * this method will zoom out the image when the mouse is exited and remove bloom effect that is on
   * it
   *
   * @param image an image view
   */
  private void setZoomOnLeave(ImageView image) {
    image.setScaleX(1);
    image.setScaleY(1);
    image.setEffect(null);
  }

  /**
   * this method will zoom out the image when the mouse is pressed and no changes to the effect
   *
   * @param image an image view
   */
  private void setZoomOnPress(ImageView image) {
    image.setScaleX(0.9);
    image.setScaleY(0.9);
  }

  /**
   * this method will return the original size of the image and do no changes to the effect
   *
   * @param image an image view
   */
  private void setZoomOnClick(ImageView image) {
    image.setScaleX(1);
    image.setScaleY(1);
  }
}
