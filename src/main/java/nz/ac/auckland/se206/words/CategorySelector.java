package nz.ac.auckland.se206.words;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** This class is responsible for generating a draw category at the beginning of each game. */
public class CategorySelector {

  public enum Difficulty {
    E,
    M,
    H
  }

  private final Map<Difficulty, List<String>> difficultyMap;

  public CategorySelector() throws IOException, CsvException, URISyntaxException {
    difficultyMap = new HashMap<>();
    for (Difficulty difficulty : Difficulty.values()) {
      difficultyMap.put(difficulty, new ArrayList<>());
    }

    for (String[] line : getLines()) {
      difficultyMap.get(Difficulty.valueOf(line[1])).add(line[0]);
    }
  }

  public String generateRandomCategory(Difficulty difficulty) {
    return difficultyMap
        .get(difficulty)
        .get(new Random().nextInt(difficultyMap.get(difficulty).size()));
  }

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
