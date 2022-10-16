package nz.ac.auckland.se206;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nz.ac.auckland.se206.user.ProfileRepository;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
// some comments//
public class App extends Application {

  private final URL cursorURL = App.class.getResource("/images/" + "middle-ages-custom-cursor.png");

  private final Image image;

  {
    assert cursorURL != null;
    image = new Image(cursorURL.toExternalForm());
  }

  public static void main(final String[] args) {
    launch();
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  protected static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {

    ProfileRepository.loadProfiles();

    final Scene scene = new Scene(loadFxml("page"));
    scene.setCursor(new ImageCursor(image, 2.5, 2.5));
    stage.setScene(scene);
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.show();
  }
}
