package functioalInterface;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum LottoRank {
    RANK_1((matchCount, containsBonus) -> matchCount == 6),
    RANK_2((matchCount, containsBonus) -> matchCount == 5 && containsBonus),
    RANK_3((matchCount, containsBonus) -> matchCount == 5),
    RANK_4((matchCount, containsBonus) -> matchCount == 4),
    RANK_5((matchCount, containsBonus) -> matchCount == 3),
    RANK_NONE((matchCount, containsBonus) -> matchCount < 3),
    ;

    private BiPredicate<Integer, Boolean> condition;

    LottoRank(final BiPredicate<Integer, Boolean> condition) {
        this.condition = condition;
    }

    static LottoRank of(final int matchCount, final boolean containsBonus) {
        return Arrays.stream(values())
            .filter(it -> it.condition.test(matchCount, containsBonus))
            .findAny()
            .orElseThrow(RuntimeException::new);
    }
}
