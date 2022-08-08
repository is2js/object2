package functioalInterface;

import java.util.List;
import java.util.stream.Collectors;

public class LottoService {

    public static LottoRank match(final List<Integer> rawUserLotto,
                                  final List<Integer> rawWinningLotto,
                                  final int rawBonusNumber) {

        return match(new Lotto(toLottoNumber(rawUserLotto)), new Lotto(toLottoNumber(rawWinningLotto)),
            LottoNumber.of(rawBonusNumber)
        );
    }

    private static List<LottoNumber> toLottoNumber(final List<Integer> rawUserLotto) {
        return rawUserLotto.stream()
            .map(LottoNumber::of)
            .collect(Collectors.toList());
    }

    private static LottoRank match(final Lotto userLotto,
                                   final Lotto winningLotto,
                                   final LottoNumber bonusNumber) {

        int matchCount = userLotto.calculateMatchCount(winningLotto);
        final boolean containsBonus = winningLotto.contains(bonusNumber);

        return LottoRank.of(matchCount, containsBonus);
    }

}
