package blackjack.testutil;

import blackjack.Card;
import blackjack.Denomination;
import blackjack.Suit;

public class Fixtures {
    public static final Card 스페이드_에이스 = Card.from(Suit.SPADES, Denomination.ACE);
    public static final Card 스페이드_2 = Card.from(Suit.SPADES, Denomination.TWO);
    public static final Card 스페이드_5 = Card.from(Suit.SPADES, Denomination.FIVE);
    public static final Card 스페이드_9 = Card.from(Suit.SPADES, Denomination.NINE);
    public static final Card 스페이드_10 = Card.from(Suit.SPADES, Denomination.TEN);
    public static final Card 스페이드_잭 = Card.from(Suit.SPADES, Denomination.JACK);
}
