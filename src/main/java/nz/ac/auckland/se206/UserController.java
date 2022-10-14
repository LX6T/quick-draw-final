package nz.ac.auckland.se206;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import javafx.util.Duration;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

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

  @FXML private ImageView imageStarUserOne;

  @FXML private ImageView imageOnBack;

  @FXML private ImageView imageStarUserTwo;

  @FXML private ImageView imageStarUserThree;

  @FXML private Label labelOnUserOne;

  @FXML private Label labelOnUserTwo;

  @FXML private Label labelOnUserThree;

  @FXML private ImageView imageOnUserOne;

  @FXML private ImageView imageOnUserTwo;

  @FXML private ImageView imageOnUserThree;

  @FXML private JFXButton buttonOnSignUp;

  private int numOfUsersPresentInTheFile = 0;

  URL cursorURL = App.class.getResource("/images/" + "middle-ages-custom-cursor.png");

  Image image = new Image(cursorURL.toExternalForm());

  public void initialize() {
    masterPane.setOpacity(0.2);
    fadeIn();
    masterPane.setCursor(new ImageCursor(image, 2.5, 2.5));
    // user Profile Repository HashMap to get the number of users present.
    ProfileRepository.loadProfiles();
    HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();
    numOfUsersPresentInTheFile = hashMap.size();

    resetUserCards();
  }

  public void resetUserCards() {
    HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();
    numOfUsersPresentInTheFile = hashMap.size();
    Set<String> userNameSet = hashMap.keySet();
    if (numOfUsersPresentInTheFile == 3) {
      buttonOnSignUp.setDisable(true);
    } else if (numOfUsersPresentInTheFile < 3) {
      buttonOnSignUp.setDisable(false);
    }
    if (numOfUsersPresentInTheFile == 0) {
      paneOnUserOne.setVisible(false);
      paneOnUserTwo.setVisible(false);
      paneOnUserThree.setVisible(false);
      // if there is no user existing in the profile repository the everything should
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
        labelOnUserOne.setText(string);
        imageOnUserOne.setImage(
            new Image(App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
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
      // add fade in transition animation the the respective image views
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
          labelOnUserOne.setText(string);
          imageOnUserOne.setImage(
              new Image(
                  App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
        } else if (i == 2) {
          labelOnUserTwo.setText(string);
          imageOnUserTwo.setImage(
              new Image(
                  App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
        }
        i++;
      }

      // set the respective image views to be visible and others to be invisible
      imageOnStartUserOne.setVisible(true);
      imageOnDeleteUserOne.setVisible(true);
      imageOnDeleteUserTwo.setVisible(true);
      imageOnStartUserTwo.setVisible(true);
      imageOnDeleteUserThree.setVisible(false);
      imageOnStartUserThree.setVisible(false);
      // add fade in transition animation the the respective image views
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
          imageOnUserOne.setImage(
              new Image(
                  App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
        } else if (i == 2) {
          labelOnUserTwo.setText(string);
          imageOnUserTwo.setImage(
              new Image(
                  App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
        } else if (i == 3) {
          labelOnUserThree.setText(string);
          imageOnUserThree.setImage(
              new Image(
                  App.class.getResource(hashMap.get(string).getPhotoPath()).toExternalForm()));
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
      // add fade in transition animation the the respective image views
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

  private void fadeIn(Node node) {
    // fade-in transition animation for a particular nodes
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(2000));
    // duration 2000 ms
    ft.setNode(node);
    ft.setFromValue(0);
    // opacity from 0 to 1
    ft.setToValue(1);
    ft.play();
  }

  /** this method will setup the fade in transition animation whenever the scene is initialized */
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
  private void enterMouseUserOne() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on start button

    imageOnStartUserOne.setScaleX(1.2);
    imageOnStartUserOne.setScaleY(1.2);
    imageOnStartUserOne.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserOne() {
    // set the hovering effect for user one on start button
    imageOnStartUserOne.setScaleX(1);
    imageOnStartUserOne.setScaleY(1);
    imageOnStartUserOne.setEffect(null);
  }

  @FXML
  private void enterMouseUserTwo() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user two on start button

    imageOnStartUserTwo.setScaleX(1.2);
    imageOnStartUserTwo.setScaleY(1.2);
    imageOnStartUserTwo.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserTwo() {
    // set the hovering effect for user two on start button
    imageOnStartUserTwo.setScaleX(1);
    imageOnStartUserTwo.setScaleY(1);
    imageOnStartUserTwo.setEffect(null);
  }

  @FXML
  private void enterMouseUserThree() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user three on start button

    imageOnStartUserThree.setScaleX(1.2);
    imageOnStartUserThree.setScaleY(1.2);
    imageOnStartUserThree.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserThree() {
    // set the hovering effect for user three on start button
    imageOnStartUserThree.setScaleX(1);
    imageOnStartUserThree.setScaleY(1);
    imageOnStartUserThree.setEffect(null);
  }

  @FXML
  private void enterMouseUserOneOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserOne.setScaleX(1.2);
    imageOnDeleteUserOne.setScaleY(1.2);
    imageOnDeleteUserOne.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserOneOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserOne.setScaleX(1);
    imageOnDeleteUserOne.setScaleY(1);
    imageOnDeleteUserOne.setEffect(null);
  }

  @FXML
  private void enterMouseUserTwoOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserTwo.setScaleX(1.2);
    imageOnDeleteUserTwo.setScaleY(1.2);
    imageOnDeleteUserTwo.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserTwoOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserTwo.setScaleX(1);
    imageOnDeleteUserTwo.setScaleY(1);
    imageOnDeleteUserTwo.setEffect(null);
  }

  @FXML
  private void enterMouseUserThreeOnDelete() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for user one on delete button

    imageOnDeleteUserThree.setScaleX(1.2);
    imageOnDeleteUserThree.setScaleY(1.2);
    imageOnDeleteUserThree.setEffect(bloom);
  }

  @FXML
  private void leaveMouseUserThreeOnDelete() {
    // set the hovering effect for user one on delete button
    imageOnDeleteUserThree.setScaleX(1);
    imageOnDeleteUserThree.setScaleY(1);
    imageOnDeleteUserThree.setEffect(null);
  }

  @FXML
  private void clickOnDeleteUserOne() {
    imageOnDeleteUserOne.setScaleX(0.9);
    imageOnDeleteUserOne.setScaleY(0.9);
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

  @FXML
  private void clickOnDeleteUserTwo() {
    imageOnDeleteUserTwo.setScaleX(0.9);
    imageOnDeleteUserTwo.setScaleY(0.9);
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

  @FXML
  private void clickOnDeleteUserThree() {
    imageOnDeleteUserThree.setScaleX(0.9);
    imageOnDeleteUserThree.setScaleY(0.9);
    if (boxOnEnable.isSelected()) {
      // load the hashMap from the user repository
      ProfileRepository.loadProfiles();
      HashMap<String, UserProfile> hashMap = ProfileRepository.getHashMapProfile();

      // remove the respective user from the hashMap and update it
      hashMap.remove(labelOnUserThree.getText());
      ProfileRepository.updateHashMap(hashMap);
      ProfileRepository.updateProfiles();
      resetUserCards();
    } else {
      // if not selected remind user where the box is
      boxOnEnable.setEffect(new Bloom(0.3));
    }
  }

  @FXML
  private void onSignUp(ActionEvent event) {
    fadeOutTwo();
  }

  @FXML
  private void enterMouseOnBack() {
    Bloom bloom = new Bloom(0.3);
    // set the hovering effect for back button on delete button

    imageOnBack.setScaleX(1.2);
    imageOnBack.setScaleY(1.2);
    imageOnBack.setEffect(bloom);
  }

  @FXML
  private void leaveMouseOnBack() {
    // set the hovering effect for back button on delete button
    imageOnBack.setScaleX(1);
    imageOnBack.setScaleY(1);
    imageOnBack.setEffect(null);
  }

  @FXML
  private void backToPage() {
    imageOnBack.setScaleX(1);
    imageOnBack.setScaleY(1);
    fadeOut();
  }

  @FXML
  private void clickOnStartUserOne() {
    imageOnStartUserOne.setScaleX(1);
    imageOnStartUserOne.setScaleY(1);
    UserProfile currentUser = ProfileRepository.get(labelOnUserOne.getText());
    ProfileRepository.setCurrentUser(currentUser);
    Scene sceneButtonIsIn = labelOnUserOne.getScene();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StatsController statsController = loader.getController();
    // get the controller from the stats controller
    statsController.setStats(currentUser);
    sceneButtonIsIn.setRoot(root);
    // james will see what the root is
  }

  @FXML
  private void clickOnStartUserTwo() {
    imageOnStartUserTwo.setScaleX(1);
    imageOnStartUserTwo.setScaleY(1);
    UserProfile currentUser = ProfileRepository.get(labelOnUserTwo.getText());
    ProfileRepository.setCurrentUser(currentUser);
    Scene sceneButtonIsIn = labelOnUserOne.getScene();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StatsController statsController = loader.getController();
    // get the controller from the stats controller
    statsController.setStats(currentUser);
    sceneButtonIsIn.setRoot(root);
    // james will see what the root is
  }

  @FXML
  private void clickOnStartUserThree() {
    imageOnStartUserThree.setScaleX(1);
    imageOnStartUserThree.setScaleY(1);
    UserProfile currentUser = ProfileRepository.get(labelOnUserThree.getText());
    ProfileRepository.setCurrentUser(currentUser);
    Scene sceneButtonIsIn = labelOnUserOne.getScene();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StatsController statsController = loader.getController();
    // get the controller from the stats controller
    statsController.setStats(currentUser);
    sceneButtonIsIn.setRoot(root);
    // james will see what the root is
  }

  @FXML
  /** this method will set up the press integrated animation for start button in user one */
  private void pressOnStartUserOne() {
    imageOnStartUserOne.setScaleX(0.9);
    imageOnStartUserOne.setScaleY(0.9);
  }

  @FXML
  /** this method will set up the press integrated animation for start button in user two */
  private void pressOnStartUserTwo() {
    imageOnStartUserTwo.setScaleX(0.9);
    imageOnStartUserTwo.setScaleY(0.9);
  }

  @FXML
  /** this method will set up the press integrated animation for start button in user three */
  private void pressOnStartUserThree() {
    imageOnStartUserThree.setScaleX(0.9);
    imageOnStartUserThree.setScaleY(0.9);
  }

  /** this method will set up the press integrated animation for delete button in user one */
  @FXML
  private void pressOnDeleteUserOne() {
    imageOnDeleteUserOne.setScaleX(0.9);
    imageOnDeleteUserOne.setScaleY(0.9);
  }

  /** this method will set up the press integrated animation for delete button in user two */
  @FXML
  private void pressOnDeleteUserTwo() {
    imageOnDeleteUserTwo.setScaleX(0.9);
    imageOnDeleteUserTwo.setScaleY(0.9);
  }

  /** this method will set up the press integrated animation for delete button in user three */
  @FXML
  private void pressOnDeleteUserThree() {
    imageOnDeleteUserThree.setScaleX(0.9);
    imageOnDeleteUserThree.setScaleY(0.9);
  }

  /** this method will set up the press integrated animation for back button in master pane */
  @FXML
  private void pressOnBackButton() {
    imageOnBack.setScaleX(0.9);
    imageOnBack.setScaleY(0.9);
  }

  /** whenever click on box enabled button, the effect of it should be immediately removed. */
  @FXML
  private void onBoxEnabled() {
    boxOnEnable.setEffect(null);
  }

  /**
   * this method will set up the fade out animation in transition between different scenes (page
   * scene and the user scene)
   */
  private void fadeOut() {
    FadeTransition ft = new FadeTransition();
    // set duration of the fade to be 500 ms
    ft.setDuration(Duration.millis(500));
    ft.setNode(masterPane);
    // opacity value from 1 to 0.2
    ft.setFromValue(1);
    ft.setToValue(0.2);
    // when fade animation finished load the next scene
    ft.setOnFinished(
        (ActionEvent eventTwo) -> {
          Scene sceneButtonIsIn = imageOnBack.getScene();
          try {
            // load the canvas scene when press this button
            sceneButtonIsIn.setRoot(App.loadFxml("page"));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    // play the animation
    ft.play();
  }

  /**
   * this method will set up the fade out animation in transition between different scenes (sign Up
   * scene and the user scene)
   */
  private void fadeOutTwo() {
    FadeTransition ft = new FadeTransition();
    // set duration of the fade to be 500 ms
    ft.setDuration(Duration.millis(500));
    ft.setNode(masterPane);
    // opacity value from 1 to 0.2
    ft.setFromValue(1);
    ft.setToValue(0.2);
    // when fade animation finished load the next scene
    ft.setOnFinished(
        (ActionEvent eventTwo) -> {
          Scene sceneButtonIsIn = imageOnBack.getScene();
          try {
            // load the canvas scene when press this button
            sceneButtonIsIn.setRoot(App.loadFxml("signUp"));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    // play the animation
    ft.play();
  }
}
