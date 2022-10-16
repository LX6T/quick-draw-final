package nz.ac.auckland.se206.user;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfile {
  /** This Class will store user statistics and allow user statistics to be changed and updated */
  private String accountName;

  private String photoPath;
  private Integer numOfWin;
  private Integer numOfLoss;
  private Integer winStreak;
  private Integer bestRecord;
  private final ArrayList<String> wordsHistory;
  private SettingsData preferredSettings;
  private final HashMap<String, Boolean> badges;

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

  public void addWordToHistory(String currentWord) {
    wordsHistory.add(currentWord);
  }

  public void wonTheGame() {
    numOfWin = numOfWin + 1;
    winStreak = winStreak + 1;
  }

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

  public void awardBadge(String badgeName) {
    if (badges.containsKey(badgeName)) {
      badges.put(badgeName, true);
    }
  }

  public boolean hasBadge(String badgeName) {
    return badges.get(badgeName);
  }
}
