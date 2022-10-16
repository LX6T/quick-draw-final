package nz.ac.auckland.se206.dictionary;

public class WordNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  WordNotFoundException(String message) {
    super(message);
  }
}
