package functioalInterface;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoNumbersTest {

    @Test
    void create_요소를_add하면_새객체를_반환하므로_매번_재할당한다() {
        LottoNumbers lottoNumbers = new LottoNumbers();

        lottoNumbers = lottoNumbers.add(LottoNumber.of(1));
        lottoNumbers = lottoNumbers.add(LottoNumber.of(2));
        lottoNumbers = lottoNumbers.add(LottoNumber.of(3));
        lottoNumbers = lottoNumbers.add(LottoNumber.of(4));
        lottoNumbers = lottoNumbers.add(LottoNumber.of(5));
        lottoNumbers = lottoNumbers.add(LottoNumber.of(6));

        Assertions.assertThat(lottoNumbers.size()).isEqualTo(6);
    }
}
