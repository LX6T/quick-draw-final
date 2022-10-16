package nz.ac.auckland.se206.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BadgeAwarder {

  private static final List<String> badgeNames =
      new ArrayList<>(
          List.of(
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
}
