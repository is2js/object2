package functioalInterface;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class LottoNumber {

    private final int value;
    private static Map<Integer, LottoNumber> CACHE = new ConcurrentHashMap<>(45);

    static {
        for (int value = 1; value < 46; value++) {
            CACHE.put(value, new LottoNumber(value));
        }
    }

    private LottoNumber(final int value) {
        this.value = value;
    }

    public static LottoNumber of(final String rawValue) {
        return of(Integer.parseInt(rawValue));
    }

    public static LottoNumber of(final int value) {
        checkRange(value);
       return CACHE.get(value);
    }

    private static void checkRange(final int value) {
        if (value < 1 || value > 45) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LottoNumber that = (LottoNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LottoNumber{" +
            "value=" + value +
            '}';
    }
}
