package nz.ac.auckland.se206.words;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import nz.ac.auckland.se206.user.ProfileRepository;

/** This class is responsible for generating a draw category at the beginning of each game. */
public class CategorySelector {

  /** This enum represents the three word difficulties, Easy, Medium and Hard */
  public enum Difficulty {
    E,
    M,
    H
  }

  private final Map<Difficulty, List<String>> difficultyMap;

  /**
   * This constructor initialises the CategorySelector
   *
   * @throws IOException if the I/O fails
   * @throws CsvException if the CSV fails
   * @throws URISyntaxException if the string cannot be parsed as a URI reference
   */
  public CategorySelector() throws IOException, CsvException, URISyntaxException {
    difficultyMap = new HashMap<>();
    // initiate a hash map
    for (Difficulty difficulty : Difficulty.values()) {
      // allocate the elements accordingly
      difficultyMap.put(difficulty, new ArrayList<>());
    }

    for (String[] line : getLines()) {
      // get lines
      difficultyMap.get(Difficulty.valueOf(line[1])).add(line[0]);
    }
  }

  /**
   * This method generates a random category of a particular difficulty
   *
   * @param difficulty of the word
   * @return a word category for the user to draw
   */
  public String generateRandomCategory(Difficulty difficulty) {
    String newWord;
    // initialize the variable

    if (ProfileRepository.getCurrentUser() != null) {
      // run if and only if the current user is not null
      while (true) {
        newWord =
            difficultyMap
                // use of difficulty map
                .get(difficulty)
                .get(new Random().nextInt(difficultyMap.get(difficulty).size()));
        // run the while loop until reach a word not in the history
        if (!ProfileRepository.getCurrentUser().isContainedInHistory(newWord)) {
          break;
        }
      }

      return newWord;
      // return the word that is not in the history
    } else {
      // if the current user is null
      newWord =
          difficultyMap
              .get(difficulty)
              .get(new Random().nextInt(difficultyMap.get(difficulty).size()));
      // generate a difficulty randomly without any help

      // break on the first loop and return the word
      return newWord;
    }
  }

  /**
   * this method calculates the number of words in each difficulty return the size of the list
   *
   * @param wordDifficulty String input
   * @return an integer represents the size of the list
   */
  public int calculateNumOfWordsInDifficulty(String wordDifficulty) {
    int numOfWords = 0;
    // initiate the numOfWords variable
    switch (wordDifficulty) {
      case "Easy":
        // in case it's easy
        numOfWords += difficultyMap.get(Difficulty.E).size();
        break;
      case "Medium":
        // in case it's medium
        numOfWords +=
            difficultyMap.get(Difficulty.E).size() + difficultyMap.get(Difficulty.M).size();
        break;
      case "Hard":
        // in case it's hard
        numOfWords +=
            difficultyMap.get(Difficulty.E).size()
                + difficultyMap.get(Difficulty.M).size()
                + difficultyMap.get(Difficulty.H).size();
        // add the words into the hash map
        break;
      case "Master":
        // in case it's master
        numOfWords += difficultyMap.get(Difficulty.H).size();
        break;
    }
    return numOfWords;
  }

  /**
   * This method returns the lines of the CSV file
   *
   * @return the lines
   * @throws IOException if the I/O fails
   * @throws CsvException if the CSV fails
   * @throws URISyntaxException if the string cannot be parsed as a URI reference
   */
  protected List<String[]> getLines() throws IOException, CsvException, URISyntaxException {
    File file =
        // initiate a file variable
        new File(
            Objects.requireNonNull(CategorySelector.class.getResource("/category_difficulty.csv"))
                .toURI());
    // get number of lines from the file due to its properties

    try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(fr)) {
      return reader.readAll();
      // return a list of string
    }
  }
}
