package functioalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String CUSTM_DELIMITER_PREFIX = "//";
    private static final int CUSTOM_DELIMITER_INDEX = 0;
    private static final int CUSTOM_TEXT_INDEX = 1;

    private static int splitAndSum(final String text, final String delimiter) {
        // 선언변수는 위로 빼서 제외시키고 추출하면, 파라미터로 올려준다.
        // 누적변수에 반복문속 업데이트는, 메서드 추출시 누적변수가 return된다.
        return sum(toInts(split(text, delimiter)));
    }

    private static String[] split(final String text, final String delimiter) {
        return text.split(delimiter);
    }

    private static int sum(final List<Integer> numbers) {
        int total = 0;
        // 1가지 일을 하는 반복문내부에서, 연산 전 개별요소에 대해 타 메서드를 적용한다면
        // -> 반복문을 돌기전에 미리 요소별 다 처리된 컬렉션을 만들고
        // -> 그 후 반복문으로 돌린다.
        // -> 미리 처리한 로직 -> 반환값을 파라미터 추출하면 -> 메서드가 인자로 빠진다.
        // values -> (String[] values ) -> numbers = toInts(values) -> numbers.for
        // --> toInts(vaules) -> (List<Integer> numbers ) -> numbers.for
        for (final Integer number : numbers) {
            if (number < 0) {
                throw new RuntimeException();
            }
            total += number;
        }
        return total;
    }

    private static List<Integer> toInts(final String[] values) {
        return Arrays.stream(values)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    public static int splitAndSum(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String value = text;
        if (value.startsWith(CUSTM_DELIMITER_PREFIX)) {
            final String delimiter = value.split("\n")[CUSTOM_DELIMITER_INDEX].substring(CUSTM_DELIMITER_PREFIX.length());
            value = text.split("\n")[CUSTOM_TEXT_INDEX];
            return splitAndSum(value, delimiter);
        }

        return splitAndSum(value, ",|:");
    }
}
