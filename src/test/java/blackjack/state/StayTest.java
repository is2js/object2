package blackjack.state;

import static blackjack.testutil.Fixtures.스페이드_10;
import static blackjack.testutil.Fixtures.스페이드_2;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.Cards;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StayTest {

    @Test
    void stay상태에서_stay호출시_예외가_발생한다() {
        final State stay = new Stay().setCards(new Cards(스페이드_10, 스페이드_2));

        assertThatThrownBy(() -> stay.stay())
            .isInstanceOf(IllegalStateException.class);
    }

}
