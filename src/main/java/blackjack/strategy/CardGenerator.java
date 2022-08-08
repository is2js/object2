package blackjack.strategy;

import blackjack.Card;
import java.util.Deque;

@FunctionalInterface
public interface CardGenerator {

    Deque<Card> generate();
}
