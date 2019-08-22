import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class WordUtils {
    @SneakyThrows
    public static List<String> getMostPopularWords (File file) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Map<String, Long> words = reader.lines().flatMap(line -> Arrays.stream(line.split("\\p{P}")))
                .map(word -> word.trim().toLowerCase()).collect(groupingBy(word -> word, counting()));

        Long maxCount = words.values().stream().max(Comparator.comparingLong(Long::longValue)).orElse(0L);
        return words.entrySet().stream().filter(e -> e.getValue().equals(maxCount)).map(Map.Entry::getKey).collect(toList());
    }
}
