import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class WordUtilsTest {

    @Test
    public void getMostPopularWords() {
        File file = new File("/Applications/Учеба/java/Project_CoffeeShop/hw_21.08/src/main/resources/example.txt");
        List<String> mostPopularWords = WordUtils.getMostPopularWords(file);
        assertEquals(2, mostPopularWords.size());
        assertTrue(mostPopularWords.contains("java"));
        assertTrue(mostPopularWords.contains("spring"));

        File empty = new File("/Applications/Учеба/java/Project_CoffeeShop/hw_21.08/src/main/resources/empty.txt");
        List<String> emptyList = WordUtils.getMostPopularWords(empty);
        assertTrue(emptyList.isEmpty());
    }

}