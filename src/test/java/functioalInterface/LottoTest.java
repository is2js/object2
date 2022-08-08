package functioalInterface;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoTest {

    @Test
    void 로또번호_중복_검증() {
        Assertions.assertThatThrownBy(() -> new Lotto(1, 2, 3, 4, 5, 5))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 로또번호_갯수_미만_검증() {
        Assertions.assertThatThrownBy(() -> new Lotto(1, 2, 3, 4))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 로또번호_갯수_초과_검증() {
        Assertions.assertThatThrownBy(() -> new Lotto(1, 2, 3, 4, 5, 6, 7))
          .isInstanceOf(IllegalArgumentException.class);
    }
}
