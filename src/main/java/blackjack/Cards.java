package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cards {

    private final List<Card> cards;

    public Cards() {
        this(new ArrayList<>());
    }

    public Cards(final Card... cards) {
        this(toCards(cards));
    }

    private static List<Card> toCards(final Card[] cards) {
        return Arrays.stream(cards)
            .collect(Collectors.toList());
    }

    public Cards(final List<Card> cards) {
        this.cards = cards;
    }

    public Cards add(final Card card){
        final List<Card> newCards = new ArrayList<>(cards);
        newCards.add(card);
        return new Cards(newCards);
    }

    public boolean isBlackjack() {
        return (hasAce() && (getSum() == 11)) || getSum() == 21;
    }

    public boolean isBust() {
        return getSum() > 21;
    }

    public boolean isReady() {
        return cards.size() < 2;
    }

    boolean hasAce() {
        return cards.stream()
            .anyMatch(Card::isAce);
    }

    int getSum() {
        return cards.stream()
            .mapToInt(Card::getPoint)
            .sum();
    }
}
