package blackjack.testutil;

import blackjack.Card;
import blackjack.Deck;
import java.util.ArrayDeque;
import java.util.Deque;

public class FixtureGenerator
{
    public static Deck generateDeck(final Card first, final Card... others) {
        return new Deck(() -> generateFixedCards(first, others));
    }

    private static Deque<Card> generateFixedCards(final Card first, final Card... others) {
        final ArrayDeque<Card> cards = new ArrayDeque<>();
        cards.push(first);
        for (final Card card : others) {
            cards.push(card);
        }
        return cards;
    }
}
