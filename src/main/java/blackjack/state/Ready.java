package blackjack.state;

import blackjack.Card;
import blackjack.Cards;

public class Ready extends Running {

    @Override
    protected State drawFrom(final Card card) {
        final Cards newCards = cards().add(card);
        if (newCards.isReady()) {
            return new Ready().setCards(newCards);
        }
        if (newCards.isBlackjack()) {
            return new Blackjack().setCards(newCards);
        }

        return new Hit().setCards(newCards);
    }

    @Override
    protected Started stayEach() {
        throw new IllegalStateException("cannot call stay.");
    }
}
