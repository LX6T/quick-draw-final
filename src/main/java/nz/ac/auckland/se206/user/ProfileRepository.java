package nz.ac.auckland.se206.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class ProfileRepository {

  private static final Gson gson = new GsonBuilder().create();
  private final Path repoPathName;
  private HashMap<String, UserProfile> users;

  public ProfileRepository(String pathName) {
    repoPathName = Paths.get(pathName);
    users = new HashMap<>();
    loadProfiles();
  }

  public HashMap<String, UserProfile> getUsers() {
    return users;
  }

  public boolean saveProfile(UserProfile profile) {
    String username = profile.getAccountName();
    if (users.containsKey(username)) {
      return false;
    } else {
      users.put(username, profile);
      return true;
    }
  }

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

  public boolean loadProfiles() {
    try {
      String userJson = Files.readString(repoPathName);
      if (Objects.equals(userJson, "")) {
        users = new HashMap<>();
      } else {
        Type type = new TypeToken<HashMap<String, UserProfile>>() {}.getType();
        users = gson.fromJson(userJson, type);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean clearRepository() {
    try {
      users = new HashMap<>();
      Files.writeString(repoPathName, "");
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
