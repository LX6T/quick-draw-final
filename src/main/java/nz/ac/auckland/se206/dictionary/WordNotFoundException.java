package nz.ac.auckland.se206.dictionary;

/** This exception is thrown if a word cannot be found in the dictionary */
public class WordNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  WordNotFoundException(String message) {
    super(message);
  }
}
