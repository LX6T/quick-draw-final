package nz.ac.auckland.se206;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

/**
 * This class is a controller for the sign-up page, where users can create new profiles. Users can
 * choose a username and a profile image.
 */
public class SignUpController implements Initializable {

  @FXML private AnchorPane paneOnOne;

  @FXML private AnchorPane paneOnTwo;

  @FXML private AnchorPane paneOnThree;

  @FXML private AnchorPane paneOnFour;

  @FXML private AnchorPane paneOnFive;

  @FXML private AnchorPane paneOnSix;

  @FXML private AnchorPane paneOnSeven;

  @FXML private AnchorPane paneOnEight;

  @FXML private AnchorPane paneOnNine;

  @FXML private ImageView imageOnZoom;

  @FXML private JFXButton buttonOnBack;

  @FXML private TextField userName;

  @FXML private Label labelOnMessage;

  @FXML private AnchorPane masterPane;

  private final URL linkOne = App.class.getResource("/images/" + "icons8-character-85 (3).png");

  private final Image imageOne;

  {
    assert linkOne != null;
    imageOne = new Image(linkOne.toExternalForm());
  }

  private final URL linkTwo = App.class.getResource("/images/" + "icons8-character-85 (5).png");

  private final Image imageTwo;

  {
    assert linkTwo != null;
    imageTwo = new Image(linkTwo.toExternalForm());
  }

  private final URL linkThree = App.class.getResource("/images/" + "icons8-character-85 (2).png");

  private final Image imageThree;

  {
    assert linkThree != null;
    imageThree = new Image(linkThree.toExternalForm());
  }

  private final URL linkFour = App.class.getResource("/images/" + "icons8-character-85.png");

  private final Image imageFour;

  {
    assert linkFour != null;
    imageFour = new Image(linkFour.toExternalForm());
  }

  private final URL linkFive = App.class.getResource("/images/" + "icons8-character-85 (4).png");

  private final Image imageFive;

  {
    assert linkFive != null;
    imageFive = new Image(linkFive.toExternalForm());
  }

  private final URL linkSix = App.class.getResource("/images/" + "icons8-character-64.png");

  private final Image imageSix;

  {
    assert linkSix != null;
    imageSix = new Image(linkSix.toExternalForm());
  }

  private final URL linkSeven = App.class.getResource("/images/" + "icons8-character-85 (1).png");

  private final Image imageSeven;

  {
    assert linkSeven != null;
    imageSeven = new Image(linkSeven.toExternalForm());
  }

  private final URL linkEight = App.class.getResource("/images/" + "icons8-ninja-64 (1).png");

  private final Image imageEight;

  {
    assert linkEight != null;
    imageEight = new Image(linkEight.toExternalForm());
  }

  private final Bloom bloom = new Bloom(0.3);

  private final Glow glow = new Glow(1.0);

  /**
   * this method will be executed before any other methods are called and set up the transition
   * animation for this scene
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    masterPane.setOpacity(0.2);
    fadeIn();
  }

  /** this method will set up the fade in transition animation whenever the scene is initialized */
  private void fadeIn() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 0.2, 1);
    ft.play();
  }

  /**
   * this method will set up the fade out animation in transition between different scenes (sign up
   * scene and the user scene)
   */
  private void fadeOutToUser() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    // when fade animation finished load the next scene
    ft.setOnFinished((ActionEvent eventTwo) -> loadUserScene());
    // play the animation
    ft.play();
  }

  /**
   * this method will set up the fade out animation in transition between different scenes (sign up
   * scene and the user scene) and add the set on finished tasks
   */
  private void loadUserScene() {
    Scene sceneButtonIsIn = buttonOnBack.getScene();
    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("user"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane one
   */
  @FXML
  private void onClickOne() {
    // reset the size of the image for hovering effect
    paneOnOne.setScaleX(1);
    paneOnOne.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(glow);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageOne);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane two
   */
  @FXML
  private void onClickTwo() {
    // reset the size of the image for hovering effect
    paneOnTwo.setScaleX(1);
    paneOnTwo.setScaleY(1);

    // set another effect on glow
    paneOnTwo.setEffect(glow);
    // disable the effect of all other images
    paneOnOne.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageTwo);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane three
   */
  @FXML
  private void onClickThree() {
    // reset the size of the image for hovering effect
    paneOnThree.setScaleX(1);
    paneOnThree.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(glow);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageThree);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane four
   */
  @FXML
  private void onClickFour() {
    // reset the size of the image for hovering effect
    paneOnFour.setScaleX(1);
    paneOnFour.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(glow);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageFour);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane Five
   */
  @FXML
  private void onClickFive() {
    // reset the size of the image for hovering effect
    paneOnFive.setScaleX(1);
    paneOnFive.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(glow);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageFive);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane six
   */
  @FXML
  private void onClickSix() {
    // reset the size of the image for hovering effect
    paneOnSix.setScaleX(1);
    paneOnSix.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(glow);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageSix);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane seven
   */
  @FXML
  private void onClickSeven() {
    // reset the size of the image for hovering effect
    paneOnSeven.setScaleX(1);
    paneOnSeven.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(glow);
    paneOnEight.setEffect(null);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageSeven);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane eight
   */
  @FXML
  private void onClickEight() {
    // reset the size of the image for hovering effect
    paneOnEight.setScaleX(1);
    paneOnEight.setScaleY(1);

    // set another effect on glow
    paneOnOne.setEffect(null);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(glow);
    paneOnNine.setEffect(null);
    // set the zoomed image to the current one
    imageOnZoom.setImage(imageEight);
  }

  /**
   * This method will animate the click action for this particular pane number and disable all the
   * other useless effect for pane nine
   */
  @FXML
  private void onClickNine() {
    // reset the size of the image for hovering effect
    paneOnNine.setScaleX(1);
    paneOnNine.setScaleY(1);

    // set another effect on glow
    paneOnNine.setEffect(glow);
    // disable the effect of all other images
    paneOnTwo.setEffect(null);
    paneOnThree.setEffect(null);
    paneOnFour.setEffect(null);
    paneOnFive.setEffect(null);
    paneOnSix.setEffect(null);
    paneOnSeven.setEffect(null);
    paneOnEight.setEffect(null);
    paneOnOne.setEffect(null);
    // set the zoomed image to the current one

    int a = getRandomNumber();
    // generate a random number

    switch (a) {
      case 1:
        imageOnZoom.setImage(imageOne);
        // in case its one
        break;
      case 2:
        imageOnZoom.setImage(imageTwo);
        // in case its two
        break;
      case 3:
        imageOnZoom.setImage(imageThree);
        // in case its three
        break;
      case 4:
        imageOnZoom.setImage(imageFour);
        // in case its four
        break;
      case 5:
        imageOnZoom.setImage(imageFive);
        // in case its five
        break;
      case 6:
        imageOnZoom.setImage(imageSix);
        break;
      case 7:
        imageOnZoom.setImage(imageSeven);
        break;
      case 8:
        imageOnZoom.setImage(imageEight);
        break;
        // generate respective images based on the different numbers generated randomly
    }
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * one
   */
  @FXML
  private void onPressOne() {
    setPressedEffect(paneOnOne);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * two
   */
  @FXML
  private void onPressTwo() {
    setPressedEffect(paneOnTwo);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * three
   */
  @FXML
  private void onPressThree() {
    setPressedEffect(paneOnThree);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * four
   */
  @FXML
  private void onPressFour() {
    setPressedEffect(paneOnFour);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * five
   */
  @FXML
  private void onPressFive() {
    setPressedEffect(paneOnFive);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * six
   */
  @FXML
  private void onPressSix() {
    setPressedEffect(paneOnSix);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * seven
   */
  @FXML
  private void onPressSeven() {
    setPressedEffect(paneOnSeven);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * eight
   */
  @FXML
  private void onPressEight() {
    setPressedEffect(paneOnEight);
  }

  /**
   * this method will set the animation for each anchor pane that the mouse is clicked on for pane
   * nine
   */
  @FXML
  private void onPressNine() {
    setPressedEffect(paneOnNine);
  }

  /** this method will sets up the hover effect on enter for pane one when the mouse is entered */
  @FXML
  private void onEnterOne() {
    setHoverEffectOnEnter(paneOnOne);
  }

  /**
   * this method will call the random class to give a random number from 1 to 8
   *
   * @return an integer of a random number from 1 to 8
   */
  private int getRandomNumber() {
    return (int) ((Math.random() * 7) + 1);
  }

  /** this method will sets up the hover effect on enter for pane one when the mouse is leaved */
  @FXML
  private void onLeaveOne() {
    setHoverEffectOnLeave(paneOnOne);
  }

  /** this method will sets up the hover effect on enter for pane two when the mouse is entered */
  @FXML
  private void onEnterTwo() {
    setHoverEffectOnEnter(paneOnTwo);
  }

  /** this method will sets up the hover effect on enter for pane two when the mouse is leaved */
  @FXML
  private void onLeaveTwo() {
    setHoverEffectOnLeave(paneOnTwo);
  }

  /** this method will sets up the hover effect on enter for pane three when the mouse is entered */
  @FXML
  private void onEnterThree() {
    setHoverEffectOnEnter(paneOnThree);
  }

  /** this method will sets up the hover effect on enter for pane three when the mouse is leaved */
  @FXML
  private void onLeaveThree() {
    setHoverEffectOnLeave(paneOnThree);
  }

  /** this method will sets up the hover effect on enter for pane four when the mouse is entered */
  @FXML
  private void onEnterFour() {
    setHoverEffectOnEnter(paneOnFour);
  }

  /** this method will sets up the hover effect on enter for pane four when the mouse is leaved */
  @FXML
  private void onLeaveFour() {
    setHoverEffectOnLeave(paneOnFour);
  }

  /** this method will sets up the hover effect on enter for pane five when the mouse is entered */
  @FXML
  private void onEnterFive() {
    setHoverEffectOnEnter(paneOnFive);
  }

  /** this method will sets up the hover effect on enter for pane five when the mouse is leaved */
  @FXML
  private void onLeaveFive() {
    setHoverEffectOnLeave(paneOnFive);
  }

  /** this method will sets up the hover effect on enter for pane six when the mouse is entered */
  @FXML
  private void onEnterSix() {
    setHoverEffectOnEnter(paneOnSix);
  }

  /** this method will sets up the hover effect on enter for pane six when the mouse is leaved */
  @FXML
  private void onLeaveSix() {
    setHoverEffectOnLeave(paneOnSix);
  }

  /** this method will sets up the hover effect on enter for pane seven when the mouse is entered */
  @FXML
  private void onEnterSeven() {
    setHoverEffectOnEnter(paneOnSeven);
  }

  /** this method will sets up the hover effect on enter for pane seven when the mouse is leaved */
  @FXML
  private void onLeaveSeven() {
    setHoverEffectOnLeave(paneOnSeven);
  }

  /** this method will sets up the hover effect on enter for pane eight when the mouse is entered */
  @FXML
  private void onEnterEight() {
    setHoverEffectOnEnter(paneOnEight);
  }

  /** this method will sets up the hover effect on enter for pane eight when the mouse is leaved */
  @FXML
  private void onLeaveEight() {
    setHoverEffectOnLeave(paneOnEight);
  }

  /** this method will sets up the hover effect on enter for pane nine when the mouse is entered */
  @FXML
  private void onEnterNine() {
    setHoverEffectOnEnter(paneOnNine);
  }

  /** this method will sets up the hover effect on enter for pane nine when the mouse is leaved */
  @FXML
  private void onLeaveNine() {
    setHoverEffectOnLeave(paneOnNine);
  }

  /**
   * this method will load the next scene for the App which is the user scene, and will add
   * transition animation
   */
  @FXML
  private void onBack() {
    fadeOutToUser();
  }

  /**
   * this method will confirm the sign up phase of users taking the selected profile photo and
   * entered user name
   */
  @FXML
  private void onConfirm() {
    String name = userName.getText();
    // get the hash map from the local repository
    HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();
    if (hashMap.keySet().size() < 3) {
      // if the user already exists show error message
      if (hashMap.containsKey(name)) {
        labelOnMessage.setTextFill(Color.ROSYBROWN);
        labelOnMessage.setText("User Already Exists!");
      } else {
        String photoPath;
        // store the photo path as image one if the images are the same
        if (imageOnZoom.getImage().equals(imageOne)) {
          photoPath = "/images/" + "icons8-character-85 (3).png";
        }
        // store the photo path as image Two if the images are the same
        else if (imageOnZoom.getImage().equals(imageTwo)) {
          photoPath = "/images/" + "icons8-character-85 (5).png";
        }
        // store the photo path as image three if the images are the same
        else if (imageOnZoom.getImage().equals(imageThree)) {
          photoPath = "/images/" + "icons8-character-85 (2).png";
        }
        // store the photo path as image four if the images are the same
        else if (imageOnZoom.getImage().equals(imageFour)) {
          photoPath = "/images/" + "icons8-character-85.png";
        }
        // store the photo path as image five if the images are the same
        else if (imageOnZoom.getImage().equals(imageFive)) {
          photoPath = "/images/" + "icons8-character-85 (4).png";
        }
        // store the photo path as image six if the images are the same
        else if (imageOnZoom.getImage().equals(imageSix)) {
          photoPath = "/images/" + "icons8-character-64.png";
        }
        // store the photo path as image seven if the images are the same
        else if (imageOnZoom.getImage().equals(imageSeven)) {
          photoPath = "/images/" + "icons8-character-85 (1).png";
        }
        // store the photo path as image eight if the images are the same
        else {
          photoPath = "/images/" + "icons8-ninja-64 (1).png";
        }
        // save the new user to hash map and update it on local repository
        hashMap.put(name, new UserProfile(name, photoPath));
        ProfileRepository.updateHashMap(hashMap);
        ProfileRepository.updateProfiles();
        labelOnMessage.setTextFill(Color.GREEN);
        // always load the newest version
        labelOnMessage.setText("User Created Successfully");
      }
    } else {
      labelOnMessage.setTextFill(Color.ROSYBROWN);
      labelOnMessage.setText("Maximum number of users reached!");
    }
  }

  /**
   * this method sets up the basic parameter methods for any of the specific methods to have hover
   * effect on
   *
   * @param anchorPane any panes
   */
  private void setHoverEffectOnEnter(AnchorPane anchorPane) {
    anchorPane.setScaleX(1.1);
    anchorPane.setScaleY(1.1);
    anchorPane.setEffect(bloom);
  }

  /**
   * this method sets up the basic parameter methods for any of the specific methods to have hover
   * effect on when mouse is leaved
   *
   * @param anchorPane any panes
   */
  private void setHoverEffectOnLeave(AnchorPane anchorPane) {
    anchorPane.setScaleX(1);
    anchorPane.setScaleY(1);
    anchorPane.setEffect(null);
  }

  /**
   * this method sets up the basic parameter methods for any of the specific methods to have hover
   * effect on when mouse is pressed
   *
   * @param pane any panes
   */
  private void setPressedEffect(AnchorPane pane) {
    pane.setScaleX(0.9);
    pane.setScaleY(0.9);
  }
}
