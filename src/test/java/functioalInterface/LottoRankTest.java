package functioalInterface;

import static functioalInterface.LottoRank.RANK_2;
import static functioalInterface.LottoRank.RANK_3;
import static functioalInterface.LottoRank.RANK_4;
import static functioalInterface.LottoRank.RANK_5;
import static functioalInterface.LottoRank.RANK_NONE;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoRankTest {

    @Test
    void of_일치개수_6을_입력하면_1등을_반환한다() {
        //given(조건)
        final int matchCount = 6;
        final boolean containsBonus = false;

        //when(행위)

        final LottoRank actual = LottoRank.of(matchCount, containsBonus);

        //then(검증)
        Assertions.assertThat(actual).isEqualTo(LottoRank.RANK_1);
    }

    public static Stream<Arguments> of_일치개수와_보너스볼일치여부를_입력하면_등수를_반환한다() {
        return Stream.of(
            Arguments.of(5, true, RANK_2),
            Arguments.of(5, false, RANK_3),
            Arguments.of(4, false, RANK_4),
            Arguments.of(3, false, RANK_5),
            Arguments.of(2, false, RANK_NONE)
        );
    }

    @ParameterizedTest
    @MethodSource
    void of_일치개수와_보너스볼일치여부를_입력하면_등수를_반환한다(final int matchCount, final boolean containsBonus, final LottoRank expected) {
        //given(조건) & when(행위)
        final LottoRank actual = LottoRank.of(matchCount, containsBonus);

        //then(검증)
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
