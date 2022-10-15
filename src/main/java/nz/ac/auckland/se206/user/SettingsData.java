package nz.ac.auckland.se206.user;

public class SettingsData {
  private String accuracyDifficulty;
  private String wordsDifficulty;
  private String timeDifficulty;
  private String confidenceDifficulty;

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

  public static int toIndex(String difficulty) {
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
}
