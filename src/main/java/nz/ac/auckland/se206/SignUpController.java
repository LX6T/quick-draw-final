package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nz.ac.auckland.se206.user.ProfileRepository;
import nz.ac.auckland.se206.user.UserProfile;

public class SignUpController implements Initializable {

	@FXML
	private AnchorPane paneOnOne;

	@FXML
	private AnchorPane paneOnTwo;

	@FXML
	private AnchorPane paneOnThree;

	@FXML
	private AnchorPane paneOnFour;

	@FXML
	private AnchorPane paneOnFive;

	@FXML
	private AnchorPane paneOnSix;

	@FXML
	private AnchorPane paneOnSeven;

	@FXML
	private AnchorPane paneOnEight;

	@FXML
	private AnchorPane paneOnNine;

	@FXML
	private AnchorPane paneOnZoom;

	@FXML
	private ImageView imageOnZoom;

	@FXML
	private JFXButton buttonOnBack;

	@FXML
	private JFXButton buttonOnConfirm;

	@FXML
	private TextField userName;

	@FXML
	private Label labelOnMessage;

	@FXML
	private AnchorPane masterPane;

	private URL linkOne = App.class.getResource("/images/" + "icons8-character-85 (3).png");

	private Image imageOne = new Image(linkOne.toExternalForm());

	private URL linkTwo = App.class.getResource("/images/" + "icons8-character-85 (5).png");

	private Image imageTwo = new Image(linkTwo.toExternalForm());

	private URL linkThree = App.class.getResource("/images/" + "icons8-character-85 (2).png");

	private Image imageThree = new Image(linkThree.toExternalForm());

	private URL linkFour = App.class.getResource("/images/" + "icons8-character-85.png");

	private Image imageFour = new Image(linkFour.toExternalForm());

	private URL linkFive = App.class.getResource("/images/" + "icons8-character-85 (4).png");

	private Image imageFive = new Image(linkFive.toExternalForm());

	private URL linkSix = App.class.getResource("/images/" + "icons8-character-64.png");

	private Image imageSix = new Image(linkSix.toExternalForm());

	private URL linkSeven = App.class.getResource("/images/" + "icons8-character-85 (1).png");

	private Image imageSeven = new Image(linkSeven.toExternalForm());

	private URL linkEight = App.class.getResource("/images/" + "icons8-ninja-64 (1).png");

	private Image imageEight = new Image(linkEight.toExternalForm());

	private Bloom bloom = new Bloom(0.3);

	private Glow glow = new Glow(1.0);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		masterPane.setOpacity(0.2);
		fadeIn();

	}

	/**
	 * this method will setup the fade in transition animation whenever the scene is
	 * initialized
	 */
	private void fadeIn() {
		// TODO Auto-generated method stub
		FadeTransition ft = new FadeTransition();
		// set the transition animation to be 500 ms
		ft.setDuration(Duration.millis(500));
		ft.setNode(masterPane);
		// opacity from 0.2 to 1.0
		ft.setFromValue(0.2);
		ft.setToValue(1);
		ft.play();
	}

	/**
	 * this method will set up the fade out animation in transition between
	 * different scenes (sign up scene and the user scene)
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
		ft.setOnFinished((ActionEvent eventTwo) -> {
			Scene sceneButtonIsIn = buttonOnBack.getScene();
			try {
				// load the canvas scene when press this button
				sceneButtonIsIn.setRoot(App.loadFxml("user"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		// play the animation
		ft.play();

	}

	/**
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane one
	 */
	@FXML
	private void clickOne() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane two
	 */
	@FXML
	private void clickTwo() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane three
	 */
	@FXML
	private void clickThree() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane four
	 */
	@FXML
	private void clickFour() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane Five
	 */
	@FXML
	private void clickFive() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane six
	 */
	@FXML
	private void clickSix() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane seven
	 */
	@FXML
	private void clickSeven() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane eight
	 */
	@FXML
	private void clickEight() {
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
	 * This method will animate the click action for this particular pane number and
	 * disable all the other useless effect for pane nine
	 */
	@FXML
	private void clickNine() {
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

		int a = getRandomNumber(1, 8);

		switch (a) {
		case 1:
			imageOnZoom.setImage(imageOne);
			break;
		case 2:
			imageOnZoom.setImage(imageTwo);
			break;
		case 3:
			imageOnZoom.setImage(imageThree);
			break;
		case 4:
			imageOnZoom.setImage(imageFour);
			break;
		case 5:
			imageOnZoom.setImage(imageFive);
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

		}

	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane one
	 */
	@FXML
	private void pressOne() {
		setPressedEffect(paneOnOne);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane two
	 */
	@FXML
	private void pressTwo() {
		setPressedEffect(paneOnTwo);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane three
	 */
	@FXML
	private void pressThree() {
		setPressedEffect(paneOnThree);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane four
	 */
	@FXML
	private void pressFour() {
		setPressedEffect(paneOnFour);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane five
	 */
	@FXML
	private void pressFive() {
		setPressedEffect(paneOnFive);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane six
	 */
	@FXML
	private void pressSix() {
		setPressedEffect(paneOnSix);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane seven
	 */
	@FXML
	private void pressSeven() {
		setPressedEffect(paneOnSeven);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane eight
	 */
	@FXML
	private void pressEight() {
		setPressedEffect(paneOnEight);
	}

	/**
	 * this method will set the animation for each anchor pane that the mouse is
	 * clicked on for pane nine
	 */
	@FXML
	private void pressNine() {
		setPressedEffect(paneOnNine);
	}

	@FXML
	private void enterOne() {
		setHoverEffectOnEnter(paneOnOne);
	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	@FXML
	private void leaveOne() {
		setHoverEffectOnLeave(paneOnOne);
	}

	@FXML
	private void enterTwo() {
		setHoverEffectOnEnter(paneOnTwo);
	}

	@FXML
	private void leaveTwo() {
		setHoverEffectOnLeave(paneOnTwo);
	}

	@FXML
	private void enterThree() {
		setHoverEffectOnEnter(paneOnThree);
	}

	@FXML
	private void leaveThree() {
		setHoverEffectOnLeave(paneOnThree);
	}

	@FXML
	private void enterFour() {
		setHoverEffectOnEnter(paneOnFour);
	}

	@FXML
	private void leaveFour() {
		setHoverEffectOnLeave(paneOnFour);
	}

	@FXML
	private void enterFive() {
		setHoverEffectOnEnter(paneOnFive);
	}

	@FXML
	private void leaveFive() {
		setHoverEffectOnLeave(paneOnFive);
	}

	@FXML
	private void enterSix() {
		setHoverEffectOnEnter(paneOnSix);
	}

	@FXML
	private void leaveSix() {
		setHoverEffectOnLeave(paneOnSix);
	}

	@FXML
	private void enterSeven() {
		setHoverEffectOnEnter(paneOnSeven);
	}

	@FXML
	private void leaveSeven() {
		setHoverEffectOnLeave(paneOnSeven);
	}

	@FXML
	private void enterEight() {
		setHoverEffectOnEnter(paneOnEight);
	}

	@FXML
	private void leaveEight() {
		setHoverEffectOnLeave(paneOnEight);
	}

	@FXML
	private void enterNine() {
		setHoverEffectOnEnter(paneOnNine);
	}

	@FXML
	private void leaveNine() {
		setHoverEffectOnLeave(paneOnNine);
	}

	@FXML
	/**
	 * this method will load the next scene for the App which is the user scene, and
	 * will add transition animation
	 */
	private void onBack() {
		fadeOut();

	}

	/**
	 * this method will confirm the sign up phase of users taking the selected
	 * profile photo and entered user name
	 */
	@FXML
	private void onConfirm() {
		String name = userName.getText();
		// get the hash map from the local repository
		HashMap<String, UserProfile> hashMap = new HashMap<String, UserProfile>();
		hashMap = ProfileRepository.getHashMapProfile();
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
				labelOnMessage.setText("Avatar Created Successfully");
			}
		} else {
			labelOnMessage.setTextFill(Color.ROSYBROWN);
			labelOnMessage.setText("Maximum number of avatars reached!");
		}

	}

	private void setHoverEffectOnEnter(AnchorPane anchorPane) {
		anchorPane.setScaleX(1.1);
		anchorPane.setScaleY(1.1);
		anchorPane.setEffect(bloom);

	}

	private void setHoverEffectOnLeave(AnchorPane anchorPane) {
		anchorPane.setScaleX(1);
		anchorPane.setScaleY(1);
		anchorPane.setEffect(null);
	}

	private void setPressedEffect(AnchorPane pane) {
		pane.setScaleX(0.9);
		pane.setScaleY(0.9);
	}

}
