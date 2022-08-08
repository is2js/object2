package functioalInterface;

import java.util.HashSet;
import java.util.Set;

public final class LottoNumbers {

    private Set<LottoNumber> values;

    public LottoNumbers() {
        this(new HashSet<>());
    }

    public LottoNumbers(final Set<LottoNumber> values) {
        this.values = values;
    }

    public LottoNumbers add(final LottoNumber lottoNumber) {
        final HashSet<LottoNumber> lottoNumbers = new HashSet<>(values);
        lottoNumbers.add(lottoNumber);
        return new LottoNumbers(lottoNumbers);
    }

    public int size() {
        return values.size();
    }
}
