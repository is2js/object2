package functioalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lotto {

    private final List<LottoNumber> values;

    public Lotto(final int... rawNumbers) {
        this(toLottoNumber(rawNumbers));
    }

    private static List<LottoNumber> toLottoNumber(final int[] rawNumbers) {
        return Arrays.stream(rawNumbers)
            .mapToObj(LottoNumber::of)
            .collect(Collectors.toList());
    }

    public Lotto(final List<LottoNumber> values) {
        checkDuplicate(values);
        checkCount(values);
        this.values = values;
    }

    private void checkDuplicate(final List<LottoNumber> values) {
        if (getDistinctCount(values) != values.size()) {
            throw new IllegalArgumentException("number duplicate");
        }
    }

    private long getDistinctCount(final List<LottoNumber> values) {
        return values.stream()
            .distinct()
            .count();
    }

    private void checkCount(final List<LottoNumber> values) {
        if (values.size() != 6) {
            throw new IllegalArgumentException("invalid size");
        }
    }

    public int calculateMatchCount(final Lotto other) {
        return values.stream()
            .mapToInt(lotto -> calculateMatchCount(other.contains(lotto)))
            .sum();
    }

    private int calculateMatchCount(final boolean contains) {
        if (contains) {
            //return matchCount +  1;
            return 1;
        }
        return 0; // 불만족시에도 [연산의 기본값]으로 업데이트하게 한다.
    }

    public boolean contains(final LottoNumber lottoNumber) {
        return values.contains(lottoNumber);
    }

    public List<LottoNumber> getValues() {
        return values;
    }
}
