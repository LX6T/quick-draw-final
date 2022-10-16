package nz.ac.auckland.se206.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BadgeManager {

  private static final List<String> badgeNames =
      new ArrayList<>(
          List.of(
              "allModes",
              "accuracy1",
              "accuracy2",
              "accuracy3",
              "words1",
              "words2",
              "words3",
              "words4",
              "time1",
              "time2",
              "time3",
              "time4",
              "confidence1",
              "confidence2",
              "confidence3",
              "confidence4",
              "streak2",
              "streak3",
              "streak5",
              "streak10",
              "win5",
              "win10",
              "win20",
              "win50"));

  public static HashMap<String, Boolean> getEmptyBadges() {
    HashMap<String, Boolean> emptyBadges = new HashMap<>();

    for (String badgeName : badgeNames) {
      emptyBadges.put(badgeName, false);
    }

    return emptyBadges;
  }

  public static void awardNewBadges(UserProfile user) {
    awardAccuracyBadges(user);
    awardWordsBadges(user);
    awardTimeBadges(user);
    awardConfidenceBadges(user);
    awardStreakBadges(user);
    awardWinBadges(user);
  }

  private static void awardAccuracyBadges(UserProfile user) {
    switch (user.getPreferredSettings().getAccuracyDifficulty()) {
      case "Easy":
        user.awardBadge("accuracy1");
        break;
      case "Medium":
        user.awardBadge("accuracy2");
        break;
      case "Hard":
        user.awardBadge("accuracy3");
        break;
    }
  }

  private static void awardWordsBadges(UserProfile user) {
    switch (user.getPreferredSettings().getWordsDifficulty()) {
      case "Easy":
        user.awardBadge("words1");
        break;
      case "Medium":
        user.awardBadge("words2");
        break;
      case "Hard":
        user.awardBadge("words3");
        break;
      case "Master":
        user.awardBadge("words4");
        break;
    }
  }

  private static void awardTimeBadges(UserProfile user) {
    switch (user.getPreferredSettings().getTimeDifficulty()) {
      case "Easy":
        user.awardBadge("time1");
        break;
      case "Medium":
        user.awardBadge("time2");
        break;
      case "Hard":
        user.awardBadge("time3");
        break;
      case "Master":
        user.awardBadge("time4");
        break;
    }
  }

  private static void awardConfidenceBadges(UserProfile user) {
    switch (user.getPreferredSettings().getConfidenceDifficulty()) {
      case "Easy":
        user.awardBadge("confidence1");
        break;
      case "Medium":
        user.awardBadge("confidence2");
        break;
      case "Hard":
        user.awardBadge("confidence3");
        break;
      case "Master":
        user.awardBadge("confidence4");
        break;
    }
  }

  private static void awardStreakBadges(UserProfile user) {
    switch (user.getWinStreak()) {
      case 10:
        user.awardBadge("streak10");
      case 5:
        user.awardBadge("streak5");
      case 3:
        user.awardBadge("streak3");
      case 2:
        user.awardBadge("streak2");
    }
  }

  private static void awardWinBadges(UserProfile user) {
    switch (user.getNumOfWin()) {
      case 50:
        user.awardBadge("win50");
      case 20:
        user.awardBadge("win20");
      case 10:
        user.awardBadge("win10");
      case 5:
        user.awardBadge("win5");
    }
  }
}
