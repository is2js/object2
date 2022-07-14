package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.DateTimeInterval;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TimeOfDayCalc extends Calc {

    private Money basePrice; // 초당 요금
    private Duration baseDuration; // 총 통화시간에 대해 몇초마다 적용할 것인지

    private final List<LocalTime> starts = new ArrayList<>();
    private final List<LocalTime> ends = new ArrayList<>();
    private final List<Duration> durations = new ArrayList<>();
    private final List<Money> prices = new ArrayList<>();

    public TimeOfDayCalc(final Money basePrice, final Duration baseDuration) {
        if (basePrice == null || basePrice.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid price");
        }
        if (baseDuration == null || baseDuration.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("invalid second");
        }

        this.basePrice = basePrice;
        this.baseDuration = baseDuration;
    }

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            //1. Call속의 데이터객체에서 날짜별 interval을 가져온다.
            for (final DateTimeInterval interval : call.splitByDay()) {
                //2. starts의 index길이만큼 돌면서,
                //   n번째 prices x
                //   ( n번째starts시간 vs interval의from시간 중 더 나중시간 ~  ends시간 vs interval의 to시간 중 더 이전시간)
                //     의 Duration.between -> getSeconds / durations( 몇초당 가격부여할 것인지)
                //   를 구해 sum에 누적한다.
                // -> call의 각 일마다, 약속된 start~end 시간구간들을 돌면서,  interval과 starts, ends의 구간을 비교해, 가장 짧은 구간을 만들어내서
                // --> 구간의 요금을 부여한다.
                for (int loop = 0; loop < starts.size(); loop++) {
                    final Money tempResult = prices.get(loop)
                        .times(Duration.between(from(interval, starts.get(loop)), to(interval, ends.get(loop))).getSeconds()
                            / (double) durations.get(loop).getSeconds());

                    if (tempResult.isLessThanOrEqualTo(Money.ZERO)) {
                        throw new RuntimeException("calculate error");
                    }
                    sum.plus(tempResult);
                }
            }
        }
        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }
        return result.plus(sum);
    }

    private LocalTime from(final DateTimeInterval interval, final LocalTime localTime) {
        return interval.getFrom().toLocalTime().isBefore(localTime)
            ? localTime : interval.getFrom().toLocalTime();
    }

    private LocalTime to(final DateTimeInterval interval, final LocalTime localTime) {
        return interval.getTo().toLocalTime().isAfter(localTime)
            ? localTime : interval.getTo().toLocalTime();
    }
}
