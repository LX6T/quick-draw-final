package nz.ac.auckland.se206.user;

public class SettingsData {
  private String accuracyDifficulty;
  private String wordsDifficulty;
  private String timeDifficulty;
  private String confidenceDifficulty;
  private boolean hiddenMode;

  public boolean isHiddenMode() {
    return hiddenMode;
  }

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

  public boolean isComplete() {
    return (accuracyDifficulty != null
        && wordsDifficulty != null
        && timeDifficulty != null
        && confidenceDifficulty != null);
  }

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

  public static int toModeIndex(boolean isMode) {
    String mode;
    if (isMode) {
      mode = "yes";
    } else {
      mode = "no";
    }
    switch (mode) {
      case "yes":
        return 0;
      case "no":
        return 1;
      default:
        return -1;
    }
  }
}
