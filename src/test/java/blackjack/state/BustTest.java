package blackjack.state;

import static blackjack.testutil.Fixtures.스페이드_10;
import static blackjack.testutil.Fixtures.스페이드_2;
import static blackjack.testutil.Fixtures.스페이드_잭;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.Cards;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BustTest {

    @Test
    void bust상태에서_카드1장을_추가로_받을때_예외가_발생한다() {
        //given(조건)
        // 카드 2 + 10 -> hit상태의 객체 -> + 11(JACK) -> bust -> +2 추가로뽑을시 -> 예외발생해서 종료
        final State bust = new Bust().setCards(new Cards(스페이드_10, 스페이드_2, 스페이드_잭));

        //when(행위) & then(검증)
        assertThatThrownBy(() -> bust.draw(스페이드_잭))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void bust상태에서_stay호출시_예외가_발생한다() {
        final State bust = new Bust().setCards(new Cards(스페이드_10, 스페이드_2));

        assertThatThrownBy(() -> bust.stay())
            .isInstanceOf(IllegalStateException.class);
    }
}
