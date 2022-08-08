package blackjack.state;

import blackjack.Card;

public abstract class Finished extends Started {

    @Override
    public final State draw(final Card card) {
        throw new IllegalStateException("cannot call draw");
    }

    @Override
    public final State stay() {
        throw new IllegalStateException("cannot call stay.");
    }

    @Override
    public final double profit(final double betMoney) {
        return betMoney * earningRate();
    }

    protected abstract double earningRate();

    @Override
    public final boolean isFinished() {
        return true;
    }
}
