package blackjack.player;

import blackjack.Card;
import blackjack.Deck;
import blackjack.state.Ready;
import blackjack.state.State;

public class Player {

    private State state = new Ready();

    public void draw(final Card card) {
        if (!state.isFinished()) {
            state = state.draw(card);
        }
    }

    public void draw(final Deck deck) {
        if (!state.isFinished() && !deck.isEmpty()) {
            state = state.draw(deck.pop());
            return;
        }

        throw new IllegalStateException("끝난 상태에서 카드를 받을 수 없습니다.");
    }

    public State getState() {
        return state;
    }
}
