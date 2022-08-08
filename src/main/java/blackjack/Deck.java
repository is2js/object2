package blackjack;

import blackjack.strategy.CardGenerator;
import java.util.Deque;

public class Deck {

    private Deque<Card> cards;

    public Deck(final CardGenerator cardGenerator) {
        this.cards = cardGenerator.generate();
    }

    public Card pop() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
