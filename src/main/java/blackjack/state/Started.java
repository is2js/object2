package blackjack.state;

import blackjack.Cards;

public abstract class Started implements State {

    private Cards cards = new Cards();

    @Override
    public final Cards cards() {
        return cards;
    }

    public final Started setCards(final Cards cards) {
        this.cards = cards;
        return this;
    }
}
