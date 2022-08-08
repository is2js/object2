package functioalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class forRefactoring {

    private static int convertAndSum(String[] values) {
        return sum(toInts(values));
    }

    private static List<Integer> toInts(final String[] values) {
        return Arrays.stream(values)
            .map(it -> Integer.parseInt(it))
            .collect(Collectors.toList());
    }

    private static int sum(final List<Integer> list) {
        return list.stream()
            .reduce(0, Integer::sum);
    }
}
