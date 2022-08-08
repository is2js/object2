package blackjack.state;

import blackjack.Card;
import blackjack.Cards;

public interface State {

    State draw(Card card);

    State stay();

    double profit(final double betMoney);

    boolean isFinished();

    Cards cards();
}
