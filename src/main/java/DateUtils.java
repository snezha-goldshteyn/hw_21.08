import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DateUtils {
    public static void displayNumberFridaysThirteen(int yearFrom, int yearTo) {
        Map<Integer, Long> numberOfFridays = IntStream.range(yearFrom, yearTo)
                .mapToObj(y -> LocalDate.ofYearDay(y, 13))
                .flatMap(DateUtils::getThirteenthOfMonth)
                .filter(DateUtils::isFridayThirteen)
                .collect(groupingBy(LocalDate::getYear, counting()));

        numberOfFridays.entrySet().stream().sorted((e1,e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println(e.getValue() + " " + e.getKey()));
    }

    private static Stream<LocalDate> getThirteenthOfMonth (LocalDate date) {
        return IntStream.range(1, 13).mapToObj(ind -> LocalDate.of(date.getYear(), ind, 13));
    }

    private static boolean isFridayThirteen (LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.ENGLISH).equalsIgnoreCase("f");
    }
}
