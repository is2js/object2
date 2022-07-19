package goodComposition.plan.calc;

import goodComposition.Money;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class TimeOfDayRule {

    private static final int SECONDS_OF_24_HOUR = 60 * 60 * 24 - 1;

    private final Duration duration;
    private final Money price;
    private final LocalTime to;
    private final TimeOfDayRule prev;

    public TimeOfDayRule(final Duration duration,
                         final Money price,
                         final LocalTime to,
                         final TimeOfDayRule prev) {

        this.duration = duration;
        this.price = price;
        this.to = to;
        this.prev = prev;
    }

    public Money calculate(final Duration duration) {
        //1) 외부에서 업데이트 되는 prev데코객체들은 개별일을 처리하기 전에, 나자신이 시작특이점이 아닌지부터 확인한다.
        //2) 첫번재, 처리할 구간이 나보다는 작아도 되지만, prev의 to(하한)보다는 커야 일을 착수한다.
        //  -> start가 0으로 같은 구간 Duration으로변환해서 비교 -> 구간의 상한 비교와 이꿜.
        if (prev == null || toDuration(prev.to).compareTo(duration) >= 0) {
            // 3) 미리 쪼개놓은 모든 구간을 돌기 때문에, 걸치지 않는 구간이 반드시 존재하므로
            //  --> 누적default값인 0를 반환해야한다.  -> 여기서 에러를 내면 안된다.
            return Money.ZERO;
        }

        //4) 일을 착수했으면, 내가처리할 부분만 짤라서 처리한다.
        //   하한은 정해져있으므로, 상한을 정하는데,
        //   -> 나보다 커서, 내상한을 상한으로 정할 수도 있고 vs 하한보단 크지만, 나보단 작아서, 그 부분까지 상한으로 정해서 처리할 수도 있다.
        final Duration targetDuration = toDuration(to);
        Duration upperbound = targetDuration.compareTo(duration) >= 0 ? duration : targetDuration;

        //6) ~ 23.59.59(LocalTime.of의 한계값)을 처리하는 구간이면서 && 실제 처리해야할 상한이 23, 59, 59에 물리게 된다면
        //   -> 2가지 조건에 물릴 땐, duration을 1초를 더해줘서, 24:00:00까지 계산되게 한다.
        if (targetDuration.getSeconds() == SECONDS_OF_24_HOUR && upperbound.getSeconds() == SECONDS_OF_24_HOUR ) {
            upperbound = upperbound.plus(Duration.ofSeconds(1));
        }

        //5) 하한~ 정한 상한까지 몇초인지로 반환후 -> 단위초당 요금을 계산한다.
        return price.times(upperbound.minus(toDuration(prev.to)).getSeconds() / (double) this.duration.getSeconds());
    }

    private Duration toDuration(final LocalTime localtime) {
        return Duration.between(LocalTime.of(0, 0, 0), localtime);
    }

    public TimeOfDayRule getPrev() {
        return prev;
    }

    public LocalTime getTo() {
        return to;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TimeOfDayRule that = (TimeOfDayRule) o;
        return Objects.equals(duration, that.duration) && Objects.equals(price, that.price)
            && Objects.equals(getTo(), that.getTo()) && Objects.equals(prev, that.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, price, getTo(), prev);
    }
}
