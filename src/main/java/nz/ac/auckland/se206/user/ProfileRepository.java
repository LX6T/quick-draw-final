package nz.ac.auckland.se206.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * This class manages the storing, saving, and updating of all the user profiles contained in the
 * app.
 */
public class ProfileRepository {

  private static final Gson gson = new GsonBuilder().create();
  private static final Path repoPathName =
      Paths.get("src/main/java/nz/ac/auckland/se206/user profile repository");
  private static HashMap<String, UserProfile> users;

  private static UserProfile currentUser;

  /**
   * If the username doesn't already exist, the user profile is added to the HashMap and returns
   * true Otherwise, the method does nothing and returns false
   *
   * @param profile to be added to the HashMap
   */
  public static void saveProfile(UserProfile profile) {
    String username = profile.getAccountName();
    // save user profile to the hash map
    if (!users.containsKey(username)) {
      users.put(username, profile);
    }
  }

  /** Save the HashMap containing all the current user profiles */
  public static void updateProfiles() {
    String usersJson = gson.toJson(users);
    // update the local repositories of the user profile
    try {
      Files.writeString(repoPathName, usersJson);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Load user profiles from repository into the HashMap */
  public static void loadProfiles() {
    try {
      String userJson = Files.readString(repoPathName);
      // load the local user profile to the hash map
      Type type = new TypeToken<HashMap<String, UserProfile>>() {}.getType();
      users = gson.fromJson(userJson, type);
    } catch (Exception e) {
      users = new HashMap<>();
    }
  }

  public static UserProfile get(String username) {
    return users.get(username);
  }

  public static void setCurrentUser(UserProfile user) {
    currentUser = user;
  }

  /**
   * This method updates the user's statistics based on outcome of the latest game.
   *
   * @param gameData from the latest game
   */
  public static void updateUserStats(GameData gameData) {

    // update the user's word history
    currentUser.addWordToHistory(gameData.getWord());

    // update the user's wins/losses
    if (gameData.isWon()) {
      currentUser.wonTheGame();
    } else {
      currentUser.lostTheGame();
    }

    // updates the user's time record
    currentUser.updateRecord(gameData.getTime());
    // updates the user's badge list
    BadgeManager.awardNewBadges(currentUser, gameData.isWon());

    // saves profile to repository
    saveProfile(currentUser);
    updateProfiles();
  }

  /**
   * This method updates the user's preferred settings
   *
   * @param settingsData is the new preferred settings
   */
  public static void updateUserSettings(SettingsData settingsData) {
    currentUser.setPreferredSettings(settingsData);

    saveProfile(currentUser);
    updateProfiles();
  }

  /**
   * This method returns the current user's preferred settings
   *
   * @return the current user's preferred settings
   */
  public static SettingsData getSettings() {
    return currentUser.getPreferredSettings();
  }

  public static UserProfile getCurrentUser() {
    return currentUser;
  }

  /**
   * this functions gives the static value of the hash map field of this class
   *
   * @return the current hash map value
   */
  public static HashMap<String, UserProfile> getHashMapProfile() {
    return users;
  }

  /**
   * This method updates the user list
   *
   * @param hashMap containing new user list
   */
  public static void updateHashMap(HashMap<String, UserProfile> hashMap) {
    users = hashMap;
  }
}
