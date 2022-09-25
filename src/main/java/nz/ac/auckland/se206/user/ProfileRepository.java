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
  private final Path repoPathName;
  private HashMap<String, UserProfile> users;

  /**
   * Constructs an empty ProfileRepository
   *
   * @param pathName of repository
   */
  public ProfileRepository(String pathName) {
    repoPathName = Paths.get(pathName);
    users = new HashMap<>();
    loadProfiles();
  }

  /**
   * Gets the HashMap containing the user profiles
   *
   * @return users HashMap
   */
  public HashMap<String, UserProfile> getUsers() {
    return users;
  }

  /**
   * If the username doesn't already exist, the user profile is added to the HashMap and returns
   * true Otherwise, the method does nothing and returns false
   *
   * @param profile to be added to the HashMap
   * @return false if username is taken, otherwise return true
   */
  public boolean saveProfile(UserProfile profile) {
    String username = profile.getAccountName();
    if (users.containsKey(username)) {
      return false;
    } else {
      users.put(username, profile);
      return true;
    }
  }

  /**
   * Save the HashMap containing all the current user profiles
   *
   * @return true
   */
  public boolean saveProfiles() {
    String usersJson = gson.toJson(users);
    try {
      Files.writeString(repoPathName, usersJson);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Load user profiles from repository into the HashMap
   *
   * @return true if load is successful, otherwise return false
   */
  public boolean loadProfiles() {
    try {
      String userJson = Files.readString(repoPathName);
      Type type = new TypeToken<HashMap<String, UserProfile>>() {}.getType();
      users = gson.fromJson(userJson, type);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /** Clears the HashMap */
  public void clearRepository() {
    users = new HashMap<>();
  }
}
