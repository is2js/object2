package blackjack.strategy;

import blackjack.Card;
import blackjack.Denomination;
import blackjack.Suit;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomCardGenerator implements CardGenerator {

    private static final List<Card> rawCards;

    static {
        rawCards = createRawCards();
    }

    private static List<Card> createRawCards() {
        return Arrays.stream(Suit.values())
            .flatMap(RandomCardGenerator::toCard)
            .collect(Collectors.toList());
    }

    private static Stream<Card> toCard(final Suit suit) {
        return Arrays.stream(Denomination.values())
            .map(denomination -> Card.from(suit, denomination));
    }

    @Override
    public Deque<Card> generate() {
        Collections.shuffle(rawCards);
        return new ArrayDeque<>(rawCards);
    }

}
