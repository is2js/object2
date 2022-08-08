package blackjack;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CardTest {


    @Test
    void from_제한된_수의_객체들은_캐싱하여_생성한다() {
        final Card cached = Card.from(Suit.HEARTS, Denomination.ACE);

        assertThat(cached).isSameAs(Card.from(Suit.HEARTS, Denomination.ACE));
    }


}
