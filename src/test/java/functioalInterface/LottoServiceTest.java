package functioalInterface;

import static functioalInterface.LottoRank.RANK_1;
import static functioalInterface.LottoRank.RANK_2;
import static functioalInterface.LottoRank.RANK_3;
import static functioalInterface.LottoRank.RANK_4;
import static functioalInterface.LottoRank.RANK_5;
import static functioalInterface.LottoRank.RANK_NONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoServiceTest {

    @Test
    void start_로또번호_보너스번호_당첨번호를_1등으로_입력하면_등수1을_응답한다() {
        //given

        //when
        // 사용자의 로또번호
        // 당첨번호 입력
        // 보너스번호 입력
        // -> 당첨 등수 응답
        final LottoRank actual = LottoService.match(
            List.of(1, 2, 3, 4, 5, 6), // 입력번호
            List.of(1, 2, 3, 4, 5, 6), // 당첨번호
            7//보너스번호
        );

        //then
        assertThat(actual).isEqualTo(RANK_1);
    }

    @ParameterizedTest
    @MethodSource
    void start_일치하는_로또번호에_따라_알맞은_등수를_반환한다(final List<Integer> userLotto1, final List<Integer> winningLotto1,
                                          final int bonusNumber1, final LottoRank expected) {
        //given
        final List<Integer> userLotto = userLotto1;
        final List<Integer> winningLotto = winningLotto1;
        final int bonusNumber = bonusNumber1;

        //when
        final LottoRank actual = LottoService.match(
            userLotto, // 입력번호
            winningLotto, //
            bonusNumber
        );

        assertThat(actual).isEqualTo(expected);
    }

    public Stream<Arguments> start_일치하는_로또번호에_따라_알맞은_등수를_반환한다() {
        return Stream.of(
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 3, 4, 5, 8), 3,
                RANK_2), // 2등 ( 5개일치 + 보너스볼 )
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 3, 4, 5, 8), 7,
                RANK_3), // 3등 ( 5개일치 )
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 3, 4, 8, 9), 7,
                RANK_4), // 4등 ( 4개일치 )
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 3, 8, 9, 10), 7,
                RANK_5), // 5등 ( 3개일치 )
            Arguments.of(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 8, 9, 10, 11), 7,
                RANK_NONE) //  0 (2개이하 일치)
        );
    }

    @Test
    void start_45보다_큰_수가_입력되면_예외를_발생시킨다() {
        final List<Integer> 로또번호_45보다_큰_수 = List.of(1,2,3,4,5,100);
        final List<Integer> 당첨번호_123456 = List.of(1,2,3,4,5,6);
        final int 보너스번호 = 7;

        Assertions.assertThatThrownBy(() -> LottoService.match(로또번호_45보다_큰_수,
                당첨번호_123456,
                보너스번호))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void start_중복된_로또번호가_입력되면_예외를_발생시시킨다() {
        final List<Integer> 로또번호_45보다_큰_수 = List.of(1,2,3,4,5,5);
        final List<Integer> 당첨번호_123456 = List.of(1,2,3,4,5,6);
        final int 보너스번호 = 7;

        Assertions.assertThatThrownBy(() -> LottoService.match(로또번호_45보다_큰_수,
                당첨번호_123456,
                보너스번호))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

