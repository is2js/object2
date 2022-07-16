package practice_decorator;

import goodComposition.Money;
import java.time.Duration;

public class SectionPriceRule {

    private final Duration to;
    private final Money money;
    private final SectionPriceRule prev;

    public SectionPriceRule(final Duration to, final Money money, final SectionPriceRule prev) {
        this.to = to;
        this.money = money;
        this.prev = prev;
    }

    public Money calculate(final Duration duration) {
        //  데코객체의 개별구간 처리는, 전체 데코객체를 도는 로직에서 누적되니, 누적될 값을 반환한다.
        //1. invariant: prev는 시작특이점(연산X)가 아니여야한다. -> 맞다면, 연산하지말고, 누적될 결과값을 NULL객체를 반환한다.
        //2. precondition: 들어오는 전체구간은, 내 하한인 prev의 상한보다 더 길어야한다. 같거나 더 짧으면, 내가 처리할이유가 없어서 누적될 결과값을 NULL객체를 반환한다.
        if (prev == null | duration.compareTo(prev.to) <= 0) return Money.ZERO;

        //3. 전체구간 중 내 처리 구간만 잘라내서 계산한다.
        //   내 하한보다 큰 것은 알았지만, 내 상한보다 짧은지 vs 내 상한보다 긴지를 판별해서, 처리구간 상한을 만들어야한다.
        //   prev.to | ----------------?(duration)-------|to ========?(duration)
        final Duration upperbound = duration.compareTo(to) > 0 ? to : duration;
        final Duration targetDuration = upperbound.minus(prev.to);// upperbound - lowerbound

        return money.times(targetDuration.getSeconds());
    }
}
