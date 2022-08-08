package blackjack.state;

import blackjack.Card;

public abstract class Running extends Started {

    @Override
    public final State draw(final Card card) {
        return drawFrom(card);
    }

    protected abstract State drawFrom(final Card card);

    @Override
    public final State stay() {
        return stayEach();
    }

    protected abstract Started stayEach();

    @Override
    public final double profit(final double betMoney) {
        throw new IllegalStateException("cannot call profit.");
    }

    @Override
    public final boolean isFinished() {
        return false;
    }
}
