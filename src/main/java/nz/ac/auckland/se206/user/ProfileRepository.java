package nz.ac.auckland.se206.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ProfileRepository {

  private static final Gson gson = new GsonBuilder().create();
  private static final Path repoPathName =
      Paths.get("src/main/java/nz/ac/auckland/se206/profiles/repository");
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
    if (!users.containsKey(username)) {
      users.put(username, profile);
    }
  }

  /** Save the HashMap containing all the current user profiles */
  public static void saveProfiles() {
    String usersJson = gson.toJson(users);
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
      Type type = new TypeToken<HashMap<String, UserProfile>>() {}.getType();
      users = gson.fromJson(userJson, type);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param username key to be searched
   * @return true if the username exists, false otherwise
   */
  public static boolean containsKey(String username) {
    return users.containsKey(username);
  }

  public static UserProfile get(String username) {
    return users.get(username);
  }

  public static void setCurrentUser(UserProfile user) {
    currentUser = user;
  }

  public static UserProfile getCurrentUser() {
    return currentUser;
  }

  public static void addWord(String word) {
    currentUser.updateWordsHistory(word);
    saveProfile(currentUser);
    saveProfiles();
  }
}
