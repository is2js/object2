package blackjack.state;

import blackjack.Card;
import blackjack.Cards;

public class Hit extends Running {

    Hit() {
    }

    @Override
    protected State drawFrom(final Card card) {
        final Cards newCards = cards().add(card);
        if (newCards.isBust()){
            return new Bust().setCards(newCards);
        }
        if (newCards.isBlackjack()) {
            return new Blackjack().setCards(newCards);
        }

        return new Hit().setCards(newCards);
    }

    @Override
    protected Started stayEach() {
        return new Stay().setCards(cards());
    }
}
