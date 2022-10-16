package nz.ac.auckland.se206.user;

/** This class stores the data recorded from a single game. */
public class GameData {
  private final String word;
  private final boolean isWon;
  private final int time;

  /**
   * This constructor initialises the instance fields.
   *
   * @param word is the category for this game
   * @param isWon is whether the user won the game
   * @param time is how long to user took to win (if at all, otherwise it is the max time)
   */
  public GameData(String word, boolean isWon, int time) {
    this.word = word;
    this.isWon = isWon;
    this.time = time;
  }

  public String getWord() {
    return word;
  }

  public boolean isWon() {
    return isWon;
  }

  public int getTime() {
    return time;
  }
}
