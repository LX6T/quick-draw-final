package nz.ac.auckland.se206.user;

/** This class stores a user's preferred settings. */
public class SettingsData {
  private String accuracyDifficulty;
  private String wordsDifficulty;
  private String timeDifficulty;
  private String confidenceDifficulty;
  private boolean hiddenMode;

  public boolean isHiddenMode() {
    return hiddenMode;
  }

  /**
   * This method toggles whether hidden mode is on/off
   *
   * @param hiddenMode is on(Yes) or off(No)
   */
  public void setHiddenMode(String hiddenMode) {
    if (hiddenMode.equals("Yes")) {
      this.hiddenMode = true;
    } else if (hiddenMode.equals("No")) {
      this.hiddenMode = false;
    }
  }

  public void setAccuracyDifficulty(String accuracyDifficulty) {
    this.accuracyDifficulty = accuracyDifficulty;
  }

  public void setWordsDifficulty(String wordsDifficulty) {
    this.wordsDifficulty = wordsDifficulty;
  }

  public void setTimeDifficulty(String timeDifficulty) {
    this.timeDifficulty = timeDifficulty;
  }

  public void setConfidenceDifficulty(String confidenceDifficulty) {
    this.confidenceDifficulty = confidenceDifficulty;
  }

  public String getAccuracyDifficulty() {
    return accuracyDifficulty;
  }

  public String getWordsDifficulty() {
    return wordsDifficulty;
  }

  public String getTimeDifficulty() {
    return timeDifficulty;
  }

  public String getConfidenceDifficulty() {
    return confidenceDifficulty;
  }

  /**
   * This method returns whether the settings have been filled out
   *
   * @return whether the settings have been filled out
   */
  public boolean isComplete() {
    return (accuracyDifficulty != null
        && wordsDifficulty != null
        && timeDifficulty != null
        && confidenceDifficulty != null);
  }

  /**
   * This method converts a difficulty string to an index
   *
   * @param difficulty to be converted
   * @return an index
   */
  public static int toDifficultyIndex(String difficulty) {
    switch (difficulty) {
      case "Easy":
        return 0;
      case "Medium":
        return 1;
      case "Hard":
        return 2;
      case "Master":
        return 3;
      default:
        return -1;
    }
  }

  /**
   * This method converts the isMode toggle to an index
   *
   * @param isMode is whether the hidden mode is on or off
   * @return 0 if isMode is true, 1 otherwise
   */
  public static int toModeIndex(boolean isMode) {
    if (isMode) {
      return 0;
    } else {
      return 1;
    }
  }
}
