package nz.ac.auckland.se206.user;

public class GameData {
  private final String word;
  private final boolean isWon;
  private final int time;

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
