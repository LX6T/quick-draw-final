package nz.ac.auckland.se206;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;
import nz.ac.auckland.se206.util.TransitionUtils;

/**
 * This class is a controller for the user select page, where users can select their profile to view
 * it before playing a game. From here you can also create a new user profile.
 */
public class UserController {

  @FXML private AnchorPane masterPane;

  @FXML private AnchorPane paneOnUserOne;

  @FXML private JFXCheckBox boxOnEnable;

  @FXML private AnchorPane paneOnUserTwo;

  @FXML private AnchorPane paneOnUserThree;

  @FXML private ImageView imageOnStartUserOne;

  @FXML private ImageView imageOnDeleteUserOne;

  @FXML private ImageView imageOnStartUserTwo;

  @FXML private ImageView imageOnDeleteUserTwo;

  @FXML private ImageView imageOnStartUserThree;

  @FXML private ImageView imageOnDeleteUserThree;

  @FXML private ImageView imageOnBack;

  @FXML private Label labelOnUserOne;

  @FXML private Label labelOnUserTwo;

  @FXML private Label labelOnUserThree;

  @FXML private ImageView imageOnUserOne;

  @FXML private ImageView imageOnUserTwo;

  @FXML private ImageView imageOnUserThree;

  @FXML private JFXButton buttonOnSignUp;

  private int numOfUsersPresentInTheFile = 0;

  URL cursorURL = App.class.getResource("/images/" + "middle-ages-custom-cursor.png");

  Image image;

  {
    assert cursorURL != null;
    image = new Image(cursorURL.toExternalForm());
  }

  /**
   * This method initialize the user scene and will be called before the start of any operations on
   * this scene
   */
  public void initialize() {
    // set opacity to 0.2 to get the correct transition animation
    masterPane.setOpacity(0.2);
    fadeIn();
    masterPane.setCursor(new ImageCursor(image, 2.5, 2.5));
    // user Profile Repository HashMap to get the number of users present.
    ProfileRepository.loadProfiles();
    HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();
    numOfUsersPresentInTheFile = hashMap.size();

    resetUserCards();
    // reset the number of users as there could be change in file
  }

  /**
   * This method will reset the user cards number and should be called whenever there is a change to
   * the user profile repository
   */
  public void resetUserCards() {
    HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();
    // always reload the profile from repository
    numOfUsersPresentInTheFile = hashMap.size();
    // get the size of the hash map to know how many users have been signed up
    Set<String> userNameSet = hashMap.keySet();
    if (numOfUsersPresentInTheFile == 3) {
      buttonOnSignUp.setDisable(true);
      // disable and enable the specific buttons depending on the number of users
    } else if (numOfUsersPresentInTheFile < 3) {
      buttonOnSignUp.setDisable(false);
    }
    if (numOfUsersPresentInTheFile == 0) {
      paneOnUserOne.setVisible(false);
      paneOnUserTwo.setVisible(false);
      paneOnUserThree.setVisible(false);
      // if there is no user existing in the profile repository then everything should
      // be set to invisible and disabled
      imageOnStartUserOne.setVisible(false);
      imageOnDeleteUserOne.setVisible(false);
      imageOnDeleteUserTwo.setVisible(false);
      imageOnStartUserTwo.setVisible(false);
      imageOnDeleteUserThree.setVisible(false);
      imageOnStartUserThree.setVisible(false);
    }
    // if there is only one user exists
    if (numOfUsersPresentInTheFile == 1) {
      paneOnUserOne.setVisible(true);
      paneOnUserTwo.setVisible(false);
      paneOnUserThree.setVisible(false);
      for (String string : userNameSet) {
        // if there is only one user in the repository then check the only element in the key
        labelOnUserOne.setText(string);
        imageOnUserOne.setImage(
            new Image(
                Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                    .toExternalForm()));
      }
      paneOnUserOne.setOpacity(1);
      // set the respective image views to be visible and others to be invisible and
      // disabled
      imageOnStartUserOne.setVisible(true);
      imageOnDeleteUserOne.setVisible(true);
      imageOnDeleteUserTwo.setVisible(false);
      imageOnStartUserTwo.setVisible(false);
      imageOnDeleteUserThree.setVisible(false);
      imageOnStartUserThree.setVisible(false);
      // add fade in transition animation the respective image views
      fadeIn(imageOnDeleteUserOne);
      fadeIn(imageOnStartUserOne);
    }
    // if there are two users coexist
    else if (numOfUsersPresentInTheFile == 2) {
      int i = 1;
      paneOnUserOne.setVisible(true);
      paneOnUserTwo.setVisible(true);
      paneOnUserThree.setVisible(false);
      paneOnUserOne.setOpacity(1);
      paneOnUserTwo.setOpacity(1);
      // set the name for the user cards
      for (String string : userNameSet) {

        if (i == 1) {
          // if the number of user is one
          labelOnUserOne.setText(string);
          imageOnUserOne.setImage(
              new Image(
                  Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                      .toExternalForm()));
        } else if (i == 2) {
          // if the number of user is two
          labelOnUserTwo.setText(string);
          // always set the respective user image to the correct slots
          imageOnUserTwo.setImage(
              new Image(
                  Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                      .toExternalForm()));
        }
        i++;
        // increment value of i to keep track of the number of users
      }

      // set the respective image views to be visible and others to be invisible
      imageOnStartUserOne.setVisible(true);
      imageOnDeleteUserOne.setVisible(true);
      imageOnDeleteUserTwo.setVisible(true);
      imageOnStartUserTwo.setVisible(true);
      imageOnDeleteUserThree.setVisible(false);
      imageOnStartUserThree.setVisible(false);
      // add fade in transition animation the respective image views
      fadeIn(imageOnDeleteUserOne);
      fadeIn(imageOnStartUserOne);
      fadeIn(imageOnDeleteUserTwo);
      fadeIn(imageOnStartUserTwo);
    }
    // if there are three users coexist
    else if (numOfUsersPresentInTheFile == 3) {
      int i = 1;
      paneOnUserOne.setVisible(true);
      paneOnUserTwo.setVisible(true);
      paneOnUserThree.setVisible(true);
      paneOnUserOne.setOpacity(1);
      paneOnUserTwo.setOpacity(1);
      paneOnUserThree.setOpacity(1);
      // set the name for the user cards
      for (String string : userNameSet) {

        if (i == 1) {
          labelOnUserOne.setText(string);
          // if the number of users is one
          imageOnUserOne.setImage(
              // set the user profile photo
              new Image(
                  Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                      .toExternalForm()));
        } else if (i == 2) {
          // if the number of users is two
          labelOnUserTwo.setText(string);
          // set the user profile photo
          imageOnUserTwo.setImage(
              new Image(
                  Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                      .toExternalForm()));
        } else if (i == 3) {
          // if the number of users is three
          labelOnUserThree.setText(string);
          // set the profile photo for users
          imageOnUserThree.setImage(
              new Image(
                  Objects.requireNonNull(App.class.getResource(hashMap.get(string).getPhotoPath()))
                      .toExternalForm()));
        }
        i++;
      }
      // set the respective image views to be visible and others to be invisible
      imageOnStartUserOne.setVisible(true);
      imageOnDeleteUserOne.setVisible(true);
      imageOnDeleteUserTwo.setVisible(true);
      imageOnStartUserTwo.setVisible(true);
      imageOnDeleteUserThree.setVisible(true);
      imageOnStartUserThree.setVisible(true);
      // add fade in transition animation the respective image views
      fadeIn(imageOnDeleteUserOne);
      fadeIn(imageOnStartUserOne);
      fadeIn(imageOnDeleteUserTwo);
      fadeIn(imageOnStartUserTwo);
      fadeIn(imageOnDeleteUserThree);
      fadeIn(imageOnStartUserThree);
      // set the corresponding user panes to visible and the rest to invisible
      // there is no need to adjust opacity for other ones because the default value
      // is 0
    }
  }

  /**
   * This method will set up the fade in transition animation whenever the scene is initialized has
   * longer duration
   *
   * @param node is the node that the fade will be applied to
   */
  private void fadeIn(Node node) {
    // fade-in transition animation for a particular nodes
    FadeTransition ft = TransitionUtils.getFadeTransition(node, 2000, 0, 1);
    ft.play();
  }

  /** This method will set up the fade in transition animation whenever the scene is initialized */
  private void fadeIn() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 0.2, 1);
    ft.play();
  }

  /**
   * This method will set up the hover effect animation for a leave button for user one when the
   * mouse is entered
   */
  @FXML
  private void onEnterMouseUserOne() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on start button

    imageOnStartUserOne.setScaleX(1.2);
    imageOnStartUserOne.setScaleY(1.2);
    imageOnStartUserOne.setEffect(bloom);
  }

  /**
   * This method will set up the hover effect animation for a leave button for user one when the
   * mouse is leaved
   */
  @FXML
  private void onLeaveMouseUserOne() {
    // set the hovering effect for user one on start button
    imageOnStartUserOne.setScaleX(1);
    imageOnStartUserOne.setScaleY(1);
    imageOnStartUserOne.setEffect(null);
  }

  /**
   * This method will set up the hover effect animation for a leave button for user two when the
   * mouse is entered
   */
  @FXML
  private void onEnterMouseUserTwo() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user two on start button

    imageOnStartUserTwo.setScaleX(1.2);
    imageOnStartUserTwo.setScaleY(1.2);
    imageOnStartUserTwo.setEffect(bloom);
  }

  /**
   * This method will set up the hover effect animation for a leave button for user two when the
   * mouse is leaved
   */
  @FXML
  private void onLeaveMouseUserTwo() {
    // set the hovering effect for user two on start button
    imageOnStartUserTwo.setScaleX(1);
    imageOnStartUserTwo.setScaleY(1);
    imageOnStartUserTwo.setEffect(null);
  }

  /**
   * This method will set up the hover effect animation for a leave button for user three when the
   * mouse is entered
   */
  @FXML
  private void onEnterMouseUserThree() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user three on start button

    imageOnStartUserThree.setScaleX(1.2);
    imageOnStartUserThree.setScaleY(1.2);
    imageOnStartUserThree.setEffect(bloom);
  }

  /**
   * This method will set up the hover effect animation for a leave button for user three when the
   * mouse is leaved
   */
  @FXML
  private void onLeaveMouseUserThree() {
    // set the hovering effect for user three on start button
    imageOnStartUserThree.setScaleX(1);
    imageOnStartUserThree.setScaleY(1);
    imageOnStartUserThree.setEffect(null);
  }

  /** This method will set up the hover effect animation for user one when the mouse is entered */
  @FXML
  private void onEnterMouseUserOneOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserOne.setScaleX(1.2);
    imageOnDeleteUserOne.setScaleY(1.2);
    imageOnDeleteUserOne.setEffect(bloom);
  }

  /** This method will set up the hover effect animation for user one when the mouse is leaved */
  @FXML
  private void onLeaveMouseUserOneOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserOne.setScaleX(1);
    imageOnDeleteUserOne.setScaleY(1);
    imageOnDeleteUserOne.setEffect(null);
  }

  /** This method will set up the hover effect animation for user two when the mouse is entered */
  @FXML
  private void onEnterMouseUserTwoOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserTwo.setScaleX(1.2);
    imageOnDeleteUserTwo.setScaleY(1.2);
    imageOnDeleteUserTwo.setEffect(bloom);
  }

  /** This method will set up the hover effect animation for user two when the mouse is leaved */
  @FXML
  private void onLeaveMouseUserTwoOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserTwo.setScaleX(1);
    imageOnDeleteUserTwo.setScaleY(1);
    imageOnDeleteUserTwo.setEffect(null);
  }

  /** This method will set up the hover effect animation for user three when the mouse is entered */
  @FXML
  private void onEnterMouseUserThreeOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserThree.setScaleX(1.2);
    imageOnDeleteUserThree.setScaleY(1.2);
    imageOnDeleteUserThree.setEffect(bloom);
  }

  /** This method will set up the hover effect animation for user three when the mouse is leaved */
  @FXML
  private void onLeaveMouseUserThreeOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserThree.setScaleX(1);
    imageOnDeleteUserThree.setScaleY(1);
    imageOnDeleteUserThree.setEffect(null);
  }

  /**
   * This method will update the hash map of the profile repository and delete the respective user
   * that is associated to it in user one
   */
  @FXML
  private void onClickOnDeleteUserOne() {
    imageOnDeleteUserOne.setScaleX(1);
    imageOnDeleteUserOne.setScaleY(1);
    if (boxOnEnable.isSelected()) {
      // load the hashMap from the user repository
      ProfileRepository.loadProfiles();
      HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();

      // remove the respective user from the hashMap and update it
      hashMap.remove(labelOnUserOne.getText());
      ProfileRepository.updateHashMap(hashMap);
      ProfileRepository.updateProfiles();
      resetUserCards();

    } else {
      // if not selected remind user where the box is
      boxOnEnable.setEffect(new Bloom(0.3));
    }
  }
  /**
   * This method will update the hash map of the profile repository and delete the respective user
   * that is associated to it in user two
   */
  @FXML
  private void onClickOnDeleteUserTwo() {
    imageOnDeleteUserTwo.setScaleX(1);
    imageOnDeleteUserTwo.setScaleY(1);
    if (boxOnEnable.isSelected()) {
      // load the hashMap from the user repository
      ProfileRepository.loadProfiles();
      HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();

      // remove the respective user from the hashMap and update it
      hashMap.remove(labelOnUserTwo.getText());
      ProfileRepository.updateHashMap(hashMap);
      ProfileRepository.updateProfiles();
      resetUserCards();
    } else {
      // if not selected remind user where the box is
      boxOnEnable.setEffect(new Bloom(0.3));
    }
  }

  /**
   * This method will update the hash map of the profile repository and delete the respective user
   * that is associated to it in user three
   */
  @FXML
  private void onClickOnDeleteUserThree() {
    imageOnDeleteUserThree.setScaleX(1);
    imageOnDeleteUserThree.setScaleY(1);
    if (boxOnEnable.isSelected()) {
      // load the hashMap from the user repository
      ProfileRepository.loadProfiles();
      HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();

      // remove the respective user from the hashMap and update it
      hashMap.remove(labelOnUserThree.getText());
      ProfileRepository.updateHashMap(hashMap);
      // get the hashmap and update on it
      ProfileRepository.updateProfiles();
      resetUserCards();
    } else {
      // if not selected remind user where the box is
      boxOnEnable.setEffect(new Bloom(0.3));
    }
  }

  /**
   * This method will set up the sign up function of the scene and create the fade out transition
   * animation for it
   */
  @FXML
  private void onSignUp() {
    fadeOutToSignUp();
  }

  /** This method will set up the hover effect animation of a mouse enters the back button */
  @FXML
  private void onEnterMouseOnBack() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for back button on delete button

    imageOnBack.setScaleX(1.2);
    imageOnBack.setScaleY(1.2);
    imageOnBack.setEffect(bloom);
  }

  /**
   * This method will set up the hover effect animation when the mouse is leaved from the back
   * button
   */
  @FXML
  private void onLeaveMouseOnBack() {
    // set the hovering effect for back button on delete button
    imageOnBack.setScaleX(1);
    imageOnBack.setScaleY(1);
    imageOnBack.setEffect(null);
  }

  /** This method will handle the back to page event of the back button on this scene */
  @FXML
  private void onBackToPage() {
    imageOnBack.setScaleX(1);
    imageOnBack.setScaleY(1);
    fadeOutToPage();
  }

  /** This method will set up the hover animation when click on start button of user one */
  @FXML
  private void onClickOnStartUserOne() {
    imageOnStartUserOne.setScaleX(1);
    imageOnStartUserOne.setScaleY(1);
    fadeOutToStatsScene(labelOnUserOne);
    // james will see what the root is
  }

  /** This method will set up the basic parameters of the fade out to stats scene */
  private void fadeOutToStatsScene(Label label) {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadStatsScene(label));
    ft.play();
  }

  /** This method will fill up the set on finished behavior of a fade transition animation */
  private void loadStatsScene(Label label) {
    UserProfile currentUser = ProfileRepository.get(label.getText());
    // update the current user so that it can be accessed to
    ProfileRepository.setCurrentUser(currentUser);
    Scene sceneButtonIsIn = label.getScene();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
    // load the root of the next scene and fill it to the current scene
    Parent root = null;
    try {
      root = loader.load();
      // load the root
    } catch (IOException e) {
      e.printStackTrace();
    }
    sceneButtonIsIn.setRoot(root);
  }

  /** This method will set up the hover animation when click on start button of user two */
  @FXML
  private void onClickOnStartUserTwo() {
    imageOnStartUserTwo.setScaleX(1);
    imageOnStartUserTwo.setScaleY(1);
    fadeOutToStatsScene(labelOnUserTwo);
  }

  /** This method will set up the hover animation when click on start button of user three */
  @FXML
  private void onClickOnStartUserThree() {
    imageOnStartUserThree.setScaleX(1);
    imageOnStartUserThree.setScaleY(1);
    fadeOutToStatsScene(labelOnUserThree);
    // james will see what the root is
  }

  /** This method will set up the press integrated animation for start button in user one */
  @FXML
  private void onPressOnStartUserOne() {
    imageOnStartUserOne.setScaleX(0.9);
    imageOnStartUserOne.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for start button in user two */
  @FXML
  private void onPressOnStartUserTwo() {
    imageOnStartUserTwo.setScaleX(0.9);
    imageOnStartUserTwo.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for start button in user three */
  @FXML
  private void onPressOnStartUserThree() {
    imageOnStartUserThree.setScaleX(0.9);
    imageOnStartUserThree.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for delete button in user one */
  @FXML
  private void onPressOnDeleteUserOne() {
    imageOnDeleteUserOne.setScaleX(0.9);
    imageOnDeleteUserOne.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for delete button in user two */
  @FXML
  private void onPressOnDeleteUserTwo() {
    imageOnDeleteUserTwo.setScaleX(0.9);
    imageOnDeleteUserTwo.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for delete button in user three */
  @FXML
  private void onPressOnDeleteUserThree() {
    imageOnDeleteUserThree.setScaleX(0.9);
    imageOnDeleteUserThree.setScaleY(0.9);
  }

  /** This method will set up the press integrated animation for back button in master pane */
  @FXML
  private void onPressOnBackButton() {
    imageOnBack.setScaleX(0.9);
    imageOnBack.setScaleY(0.9);
  }

  /** Whenever an enabled box is clicked, the effect of it should be immediately removed. */
  @FXML
  private void onEnableBox() {
    boxOnEnable.setEffect(null);
  }

  /**
   * This method will set up the fade out animation in transition between different scenes (page
   * scene and the user scene)
   */
  private void fadeOutToPage() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    ft.setOnFinished((ActionEvent eventTwo) -> loadPageScene());
    // play the animation
    ft.play();
  }

  /**
   * This method will set up the fade out animation in transition between different scenes (sign Up
   * scene and the user scene)
   */
  private void fadeOutToSignUp() {
    FadeTransition ft = TransitionUtils.getFadeTransition(masterPane, 300, 1, 0.2);
    // when fade animation finished load the next scene
    ft.setOnFinished((ActionEvent eventTwo) -> loadSignUpScene());
    // play the animation
    ft.play();
  }

  /** This method loads the menu page scene. */
  private void loadPageScene() {
    Scene sceneButtonIsIn = imageOnBack.getScene();
    try {
      // load the canvas scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("page"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** This method loads the signup scene. */
  private void loadSignUpScene() {
    Scene sceneButtonIsIn = imageOnBack.getScene();
    try {
      // load the signup scene when press this button
      sceneButtonIsIn.setRoot(App.loadFxml("signUp"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
