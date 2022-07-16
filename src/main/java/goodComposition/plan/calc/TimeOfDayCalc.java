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

    private List<LocalTime> starts = new ArrayList<>();
    private List<LocalTime> ends = new ArrayList<>();
    private List<Duration> durations = new ArrayList<>(); // 몇초마다 요금을 부여할 것인지
    private List<Money> prices = new ArrayList<>(); // 초당 요금

    public TimeOfDayCalc(final Money basePrice, final Duration baseDuration,
                         final List<LocalTime> starts,
                         final List<LocalTime> ends,
                         final List<Duration> durations,
                         final List<Money> prices) {
        this.starts = starts;
        this.ends = ends;
        this.durations = durations;
        this.prices = prices;
    }

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            //1. Call속의 데이터객체에서 날짜별 interval을 가져온다.
            for (final DateTimeInterval interval : call.splitByDay()) {
                //2. interval은 1일에 대한 시간구간이며
                //   미리 하루를 start ~ end구간으로 짤라놓았으니, 짤라놓은 구간들을 돌면서
                //   겹치는 구간에 대해서, 해당구간별 amount를 계산해 누적한다.
                //  -> 시작vs시작 ~ 끝vs끝을 더 안쪽(좁은쪽)만 골라내면,
                //     겹칠 경우, 교집합의 걸리는 구간만 구할 수 있다.
                //     s1--[ss1]===[e3]= = =ee5
                //  -> error)안겹치는 구간인데, 더안쪽만 골라내면, 안겹치는 크로스구간이 구간으로 잡힌다.
                for (int loop = 0; loop < starts.size(); loop++) {
                    final Money tempResult = prices.get(loop)
                        .times(Duration.between(from(interval, starts.get(loop)), to(interval, ends.get(loop))).getSeconds()
                            / (double) durations.get(loop).getSeconds());

                    System.out.println("sum "+ loop + " => " + tempResult.getAmount());
                    if (tempResult.isLessThanOrEqualTo(Money.ZERO)) {
                        throw new RuntimeException("calculate error");
                    }

                    // 업데이트후, 누적시 값객체 재할당 빠짐.
                    sum = sum.plus(tempResult);
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
