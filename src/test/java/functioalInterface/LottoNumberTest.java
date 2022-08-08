package functioalInterface;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoNumberTest {

    @ParameterizedTest
    @CsvSource({"-1,0,46"})
    void create_1과_45사이_범위를_벗어나면_예외가_발생한다(final int number) {
        Assertions.assertThatThrownBy(() -> LottoNumber.of(number))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void of_정적팩토리메서리를_캐싱된_객체를_생성한다() {
        final LottoNumber actual = LottoNumber.of(5);
        final LottoNumber expected = LottoNumber.of(5);

        Assertions.assertThat(actual).isSameAs(expected);
    }

    @Test
    void of_더_원시타입_string_파라미터_생성자를_추가한다() {
        final LottoNumber actual = LottoNumber.of("5");
        final LottoNumber expected = LottoNumber.of("5");

        Assertions.assertThat(actual).isSameAs(expected);
    }
}
