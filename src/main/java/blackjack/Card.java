package blackjack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {

    private final static Map<String, Card> CACHE = new HashMap<>(52);

    private final Suit suit;
    private final Denomination denomination;

    private Card(final Suit suit, final Denomination denomination) {
        this.suit = suit;
        this.denomination = denomination;
    }

    public static Card from(final Suit suit, final Denomination denomination) {

        return CACHE.computeIfAbsent(toKey(suit, denomination), ignored -> new Card(suit, denomination));
    }

    private static String toKey(final Suit suit, final Denomination denomination) {
        return suit.name() + denomination.name();
    }

    public Suit getSuit() {
        return suit;
    }

    public int getPoint() {
        return denomination.getPoint();
    }

    public boolean isAce() {
        return denomination == Denomination.ACE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Card card = (Card) o;
        return suit == card.suit && denomination == card.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, denomination);
    }

    @Override
    public String toString() {
        return "Card{" +
            "suit=" + suit +
            ", denomination=" + denomination +
            '}';
    }
}
