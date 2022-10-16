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
    for (Difficulty difficulty : Difficulty.values()) {
      difficultyMap.put(difficulty, new ArrayList<>());
    }

    for (String[] line : getLines()) {
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

    while (true) {
      newWord =
          difficultyMap
              .get(difficulty)
              .get(new Random().nextInt(difficultyMap.get(difficulty).size()));

      if (!ProfileRepository.getCurrentUser().isContainedInHistory(newWord)) {
        break;
      }
    }

    return newWord;
  }

  public int calculateNumOfWordsInDifficulty(String wordDifficulty) {
    int numOfWords = 0;
    switch (wordDifficulty) {
      case "Easy":
        numOfWords += difficultyMap.get(Difficulty.E).size();
        break;
      case "Medium":
        numOfWords +=
            difficultyMap.get(Difficulty.E).size() + difficultyMap.get(Difficulty.M).size();
        break;
      case "Hard":
        numOfWords +=
            difficultyMap.get(Difficulty.E).size()
                + difficultyMap.get(Difficulty.M).size()
                + difficultyMap.get(Difficulty.H).size();
        break;
      case "Master":
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
        new File(
            Objects.requireNonNull(CategorySelector.class.getResource("/category_difficulty.csv"))
                .toURI());

    try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(fr)) {
      return reader.readAll();
    }
  }
}
