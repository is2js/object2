package blackjack.testutil;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.Card;
import blackjack.Denomination;
import blackjack.Suit;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FixturesTest {

    @Test
    void 픽스쳐_대상객체는_싱글톤으로_어디서_생성하던_같은객체여야_한다() {
        //given(조건)
        final Card actual = Card.from(Suit.SPADES, Denomination.TWO);
        final Card expected = Card.from(Suit.SPADES, Denomination.TWO);

        //when(행위) & then(검증)
        assertThat(actual).isSameAs(expected);
    }
}
