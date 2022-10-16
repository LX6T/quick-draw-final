package nz.ac.auckland.se206.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is responsible for awarding badges to the user after their stats have been updated.
 */
public class BadgeManager {

  // This is the list of all possible badges the user can obtain
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

  /**
   * This method returns a HashMap of all badges un-awarded.
   *
   * @return the HashMap of un-awarded badges
   */
  public static HashMap<String, Boolean> getEmptyBadges() {
    HashMap<String, Boolean> emptyBadges = new HashMap<>();
    // set up the hash map

    for (String badgeName : badgeNames) {
      // get badge names
      emptyBadges.put(badgeName, false);
    }

    return emptyBadges;
  }

  /**
   * This method awards the user any new badges they may have earned since the last game
   *
   * @param user who's badges are to be updated
   * @param isWon whether the last game was a win or not
   */
  public static void awardNewBadges(UserProfile user, boolean isWon) {

    if (user.getNumOfWin() > 0 || user.getNumOfLost() > 0) {
      user.awardBadge("allModes");
    }

    if (isWon) {
      // only update these settings after a win
      awardAccuracyBadges(user);
      awardWordsBadges(user);
      awardTimeBadges(user);
      awardConfidenceBadges(user);
      awardStreakBadges(user);
      awardWinBadges(user);
    }
  }

  /**
   * This method awards the user badges associated with accuracy
   *
   * @param user who's badges are to be updated
   */
  private static void awardAccuracyBadges(UserProfile user) {
    // Badge is awarded depending on current difficulty setting
    switch (user.getPreferredSettings().getAccuracyDifficulty()) {
      case "Easy":
        user.awardBadge("accuracy1");
        break;
      case "Medium":
        // in case its medium
        user.awardBadge("accuracy2");
        break;
      case "Hard":
        user.awardBadge("accuracy3");
        break;
    }
  }

  /**
   * This method awards the user badges associated with word difficulty
   *
   * @param user who's badges are to be updated
   */
  private static void awardWordsBadges(UserProfile user) {
    // Badge is awarded depending on current difficulty setting
    switch (user.getPreferredSettings().getWordsDifficulty()) {
      case "Easy":
        user.awardBadge("words1");
        break;
      case "Medium":
        user.awardBadge("words2");
        break;
      case "Hard":
        // in case its hard
        user.awardBadge("words3");
        break;
      case "Master":
        // in case its master
        user.awardBadge("words4");
        break;
    }
  }

  /**
   * This method awards the user badges associated with time limit
   *
   * @param user who's badges are to be updated
   */
  private static void awardTimeBadges(UserProfile user) {
    // Badge is awarded depending on current difficulty setting
    switch (user.getPreferredSettings().getTimeDifficulty()) {
      case "Easy":
        user.awardBadge("time1");
        break;
      case "Medium":
        user.awardBadge("time2");
        break;
        // in case its hard
      case "Hard":
        user.awardBadge("time3");
        break;
      case "Master":
        // in case its master
        user.awardBadge("time4");
        break;
    }
  }

  /**
   * This method awards the user badges associated with confidence level
   *
   * @param user who's badges are to be updated
   */
  private static void awardConfidenceBadges(UserProfile user) {
    // Badge is awarded depending on current difficulty setting
    switch (user.getPreferredSettings().getConfidenceDifficulty()) {
      case "Easy":
        user.awardBadge("confidence1");
        break;
      case "Medium":
        user.awardBadge("confidence2");
        break;
        // in case it's hard
      case "Hard":
        user.awardBadge("confidence3");
        break;
      case "Master":
        // in case its master
        user.awardBadge("confidence4");
        break;
    }
  }

  /**
   * This method awards the user badges associated with win streak
   *
   * @param user who's badges are to be updated
   */
  private static void awardStreakBadges(UserProfile user) {
    // Badge is awarded depending on current streak
    switch (user.getWinStreak()) {
      case 10:
        user.awardBadge("streak10");
      case 5:
        user.awardBadge("streak5");
      case 3:
        // in case its 3
        user.awardBadge("streak3");
      case 2:
        user.awardBadge("streak2");
        // in case its 2
    }
  }

  /**
   * This method awards the user badges associated with total wins
   *
   * @param user who's badges are to be updated
   */
  private static void awardWinBadges(UserProfile user) {
    // Badge is awarded depending on total number of wins
    switch (user.getNumOfWin()) {
      case 50:
        user.awardBadge("win50");
        // in case its 50
      case 20:
        user.awardBadge("win20");
      case 10:
        // in case its 10
        user.awardBadge("win10");
      case 5:
        user.awardBadge("win5");
    }
  }
}
