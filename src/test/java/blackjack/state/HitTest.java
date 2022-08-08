package blackjack.state;

import static blackjack.testutil.Fixtures.스페이드_10;
import static blackjack.testutil.Fixtures.스페이드_2;
import static blackjack.testutil.Fixtures.스페이드_5;
import static blackjack.testutil.Fixtures.스페이드_9;
import static blackjack.testutil.Fixtures.스페이드_에이스;
import static blackjack.testutil.Fixtures.스페이드_잭;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.Cards;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HitTest {

    @Test
    void hit상태에서_카드1장을_추가로_받을때_합20이하가되는_카드를_받으면_다시_hit상태가_된다() {
        //given(조건)
        // 카드 2 + 5 -> hit상태의 객체 -> + 1(ACE) -> 히트상태
        final State hit = new Hit().setCards(new Cards(스페이드_2, 스페이드_5));

        //when(행위)
        final State actual = hit.draw(스페이드_에이스);

        //then(검증)
        assertThat(actual).isInstanceOf(Hit.class);
    }

    @Test
    void hit상태에서_카드1장을_추가로_받을때_합21초과하는_카드를_받으면_bust상태가_된다() {
        //given(조건)
        // 카드 2 + 10 -> hit상태의 객체 -> + 11(JACK) -> bust
        final State hit = new Hit().setCards(new Cards(스페이드_2, 스페이드_10));

        //when(행위)
        final State actual = hit.draw(스페이드_잭);

        //then(검증)
        assertThat(actual).isInstanceOf(Bust.class);
    }

    @Test
    void hit상태에서_카드1장을_추가로_받았을때_합이_21이되면_blackjack상태가_된다() {
        //given(조건)
        final State hit = new Hit().setCards(new Cards(스페이드_2, 스페이드_10)); // 2 + 10

        //when(행위)
        final State actual = hit.draw(스페이드_9); // 2+10+9

        //then(검증)
        assertThat(actual).isInstanceOf(Blackjack.class);
    }

    @Test
    void hit상태에서_stay를_호출하면_stay상태가_된다() {
        //given(조건)
        final State hit = new Hit().setCards(new Cards(스페이드_2, 스페이드_10)); // 2 + 10

        //when(행위) & //then(검증)
        assertThat(hit.stay()).isInstanceOf(Stay.class);
    }
}
