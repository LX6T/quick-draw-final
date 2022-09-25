package nz.ac.auckland.se206.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ProfileRepository {

  private static final Gson gson = new GsonBuilder().create();

  private final Path repoPathName;
  private HashMap<String, UserProfile> users;

  public ProfileRepository(String pathName) {
    repoPathName = Paths.get(pathName);
    users = new HashMap<>();
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
}
