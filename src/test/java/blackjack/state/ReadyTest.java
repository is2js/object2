package blackjack.state;

import static blackjack.testutil.Fixtures.스페이드_10;
import static blackjack.testutil.Fixtures.스페이드_2;
import static blackjack.testutil.Fixtures.스페이드_5;
import static blackjack.testutil.Fixtures.스페이드_에이스;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReadyTest {

    @Test
    void ready_2와5를_입력하면_hit상태를_응답한다() {
        // 카드 2 + 5 -> hit상태의 객체
        final State actual = new Ready().draw(스페이드_2)
            .draw(스페이드_5);

        assertThat(actual).isInstanceOf(Hit.class);
    }

    @Test
    void ready_ACE와_TEN_입력하면_blackjack상태를_응답한다() {
        //given(조건)

        //when(행위)
        final State actual = new Ready().draw(스페이드_에이스)
            .draw(스페이드_10);

        //then(검증)
        assertThat(actual).isInstanceOf(Blackjack.class);
    }

    @Test
    void ready상태에서_stay호출시_예외가_발생한다() {
        final State ready = new Ready();

        assertThatThrownBy(() -> ready.stay())
            .isInstanceOf(IllegalStateException.class);
    }

}
