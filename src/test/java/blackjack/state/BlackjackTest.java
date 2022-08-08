package blackjack.state;

import static blackjack.testutil.Fixtures.스페이드_10;
import static blackjack.testutil.Fixtures.스페이드_2;
import static blackjack.testutil.Fixtures.스페이드_에이스;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.Cards;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BlackjackTest {

    @Test
    void blackjack상태에서_카드1장을_추가로_받으면_게임이_에러호출로_종료된다() {
        //given(조건)
        final State blackjack = new Blackjack().setCards(new Cards(스페이드_에이스, 스페이드_10));

        //when(행위) & then(검증)
        assertThatThrownBy(
            () -> blackjack.draw(스페이드_2)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void blackjack상태에서_stay호출시_예외가_발생한다() {
        final State blackjack = new Blackjack().setCards(new Cards(스페이드_에이스, 스페이드_10));

        assertThatThrownBy(() -> blackjack.stay())
          .isInstanceOf(IllegalStateException.class);
    }
}
