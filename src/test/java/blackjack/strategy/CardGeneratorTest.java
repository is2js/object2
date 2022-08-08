package blackjack.strategy;

import static blackjack.testutil.Fixtures.스페이드_2;
import static blackjack.testutil.Fixtures.스페이드_5;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.Card;
import blackjack.Deck;
import blackjack.testutil.FixtureGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CardGeneratorTest {

    @Test
    void generate() {
        final Deck deck = FixtureGenerator.generateDeck(스페이드_2, 스페이드_5);

        final Card actual = deck.pop();

        assertThat(actual).isEqualTo(스페이드_5);
    }

}
