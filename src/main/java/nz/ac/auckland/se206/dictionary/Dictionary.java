package nz.ac.auckland.se206.dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/** This class is responsible for finding the definitions of words from the online dictionary API */
public class Dictionary {
  private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

  /**
   * This method searches for the definition of a word on the by querying an online dictionary API.
   *
   * @param word whose definition needs to be searched
   * @return the definition of the word as a string
   * @throws IOException if the I/O fails
   * @throws WordNotFoundException if the word cannot be found
   */
  public static String searchDefinition(String word) throws IOException, WordNotFoundException {
    // initialize the clients of the dictionary
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(API_URL + word).build();
    Response response = client.newCall(request).execute();
    // execute the call
    ResponseBody responseBody = response.body();

    assert responseBody != null;
    String jsonString = responseBody.string();
    // get response body to string

    try {
      JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
      // use Json objects to token
      String title = jsonObj.getString("title");
      throw new WordNotFoundException(title);
      // throw new exception
    } catch (ClassCastException ignored) {
    }

    JSONArray jArray = (JSONArray) new JSONTokener(jsonString).nextValue();
    // convert it to array
    List<String> definitions = new ArrayList<>();

    JSONObject jsonEntryObj = jArray.getJSONObject(0);
    JSONArray jsonMeanings = jsonEntryObj.getJSONArray("meanings");

    JSONObject jsonMeaningObj = jsonMeanings.getJSONObject(0);
    // get the correct meaning of it

    JSONArray jsonDefinitions = jsonMeaningObj.getJSONArray("definitions");

    JSONObject jsonDefinitionObj = jsonDefinitions.getJSONObject(0);

    String definition = jsonDefinitionObj.getString("definition");
    // turn the definition into string
    if (!definition.isEmpty()) {
      definitions.add(definition);
    }
    // if the definition is not empty

    return definitions.get(0);
  }
}
