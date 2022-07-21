package goodComposition.plan.calc;

import goodComposition.DateTimeInterval;
import goodComposition.Money;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Set;

public class DayOfWeekRule {

    private final Set<DayOfWeek> dayOfWeeks; // 내가 처리할 구간-> 복수여도 contains로 확인하면 된다.
    private final Duration duration; // 계산시 필요정보: 요금부과 단위시간
    private final Money price; // 단위시간당 부과할 요금

    public DayOfWeekRule(final Set<DayOfWeek> dayOfWeeks, final Duration duration, final Money price) {
        this.dayOfWeeks = dayOfWeeks;
        this.duration = duration;
        this.price = price;
    }

    public Money calculate(final DateTimeInterval interval) {
        // 내가 처리할 대상이 아니면, ZERO를 반환한다. -> 바깥에서는 전체경우를 돌며 누적하기 때문에
        //    해당하는 것만 ZERO가 아닌 것으로 누적되도록 해야한다.
        if (!dayOfWeeks.contains(interval.getFrom().getDayOfWeek())) {
            return Money.ZERO;
        }

        final long unitSeconds = interval.duration().getSeconds() / duration.getSeconds();
        return price.times(unitSeconds);
    }
}
