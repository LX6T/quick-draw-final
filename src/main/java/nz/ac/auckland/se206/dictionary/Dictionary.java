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

public class Dictionary {
  private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

  public static String searchDefinition(String word) throws IOException, WordNotFoundException {

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(API_URL + word).build();
    Response response = client.newCall(request).execute();
    ResponseBody responseBody = response.body();

    String jsonString = responseBody.string();

    try {
      JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
      String title = jsonObj.getString("title");
      String subMessage = jsonObj.getString("message");
      throw new WordNotFoundException(word, title, subMessage);
    } catch (ClassCastException e) {
    }

    JSONArray jArray = (JSONArray) new JSONTokener(jsonString).nextValue();
    List<String> definitions = new ArrayList<String>();

    JSONObject jsonEntryObj = jArray.getJSONObject(0);
    JSONArray jsonMeanings = jsonEntryObj.getJSONArray("meanings");

    JSONObject jsonMeaningObj = jsonMeanings.getJSONObject(0);

    JSONArray jsonDefinitions = jsonMeaningObj.getJSONArray("definitions");

    JSONObject jsonDefinitionObj = jsonDefinitions.getJSONObject(0);

    String definition = jsonDefinitionObj.getString("definition");
    if (!definition.isEmpty()) {
      definitions.add(definition);
    }

    return definitions.get(0);
  }
}
