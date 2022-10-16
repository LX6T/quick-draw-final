package nz.ac.auckland.se206.user;

import java.util.ArrayList;
import java.util.HashMap;

/** This Class will store user statistics and allow user statistics to be changed and updated */
public class UserProfile {
  private String accountName;
  private String photoPath;
  private Integer numOfWin;
  private Integer numOfLoss;
  private Integer winStreak;
  private Integer bestRecord;
  private final ArrayList<String> wordsHistory;
  private SettingsData preferredSettings;
  private final HashMap<String, Boolean> badges;

  /**
   * This constructor initialises the user's profile
   *
   * @param accountName of the user
   * @param photoPath where the user's profile picture is stored
   */
  public UserProfile(String accountName, String photoPath) {
    // initialise the user by their name
    this.accountName = accountName;
    this.photoPath = photoPath;

    numOfWin = 0;
    numOfLoss = 0;
    winStreak = 0;
    bestRecord = 61;

    wordsHistory = new ArrayList<>();
    preferredSettings = new SettingsData();
    badges = BadgeManager.getEmptyBadges();
  }

  /**
   * This method will update the record automatically based on the value of the records
   *
   * @param record which should be input each time the game is run
   */
  public void updateRecord(Integer record) {
    // automatically update the record based on its value
    if (bestRecord > record) {
      bestRecord = record;
    }
  }

  /**
   * This method adds a word to the user's word history
   *
   * @param currentWord to be added to the word history
   */
  public void addWordToHistory(String currentWord) {
    wordsHistory.add(currentWord);
  }

  /** This method updates the user's wins and win streak */
  public void wonTheGame() {
    numOfWin = numOfWin + 1;
    winStreak = winStreak + 1;
  }

  /** This method updates the user's losses and resets their win streak */
  public void lostTheGame() {
    numOfLoss = numOfLoss + 1;
    winStreak = 0;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public Integer getNumOfWin() {
    return numOfWin;
  }

  public Integer getNumOfLost() {
    return numOfLoss;
  }

  public Integer getWinStreak() {
    return winStreak;
  }

  /**
   * This method returns all the words the user has encountered in previous games
   *
   * @return the user's word history as a string
   */
  public String getWordsHistory() {
    // get words history from the arrayList
    StringBuilder wordsHistoryString = new StringBuilder();
    for (String word : wordsHistory) {
      wordsHistoryString.append(word).append(", ");
    }
    // if the length of the history is greater than 2
    if (wordsHistoryString.length() >= 2)
      wordsHistoryString.setLength(wordsHistoryString.length() - 2);
    return wordsHistoryString.toString();
  }

  public Integer getBestRecord() {
    return bestRecord;
  }

  /**
   * This method return the user's score = wins - losses
   *
   * @return wins minus losses
   */
  public Integer getScore() {
    return numOfWin - numOfLoss;
  }

  public void setPreferredSettings(SettingsData settingsData) {
    preferredSettings = settingsData;
  }

  public SettingsData getPreferredSettings() {
    return preferredSettings;
  }

  public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

  /**
   * This method adds a badge to the user's list of awarded badges
   *
   * @param badgeName of the badge to be awarded
   */
  public void awardBadge(String badgeName) {
    if (badges.containsKey(badgeName)) {
      badges.put(badgeName, true);
      System.out.println("awarded " + badgeName);
    }
  }
}
