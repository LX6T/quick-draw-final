package nz.ac.auckland.se206.words;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CategorySelectorTest {

  @Test
  public void testCSVReader() throws IOException, CsvException, URISyntaxException {
    CategorySelector category = new CategorySelector();
    List<String[]> result = category.getLines();
    int size = result.size();
    assertTrue(size == 345);
  }
}
