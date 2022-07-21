package goodComposition.plan.calc;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Call;
import goodComposition.Money;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekCalcTest {

    @DisplayName("")
    @Test
    void calculate() {
        // 전화는 금요일 22시 ~ 토요일 2시까지다 -> interval은 내부에서 금 2시간 + 토2시간으로 나눠질 것이다.
        final Call call = new Call(LocalDateTime.of(2022, 7, 22, 22, 0, 0), LocalDateTime.of(2022, 7, 23, 2, 0, 0));

        // 주중(목금)에는 시간당 1000원 요금 부과
        final DayOfWeekRule ruleOfWeekday = new DayOfWeekRule(Set.of(DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
            Duration.ofSeconds(3600), Money.of(1000D));
        // 주말(토일)에는 시간당 2000원 요금 부과
        final DayOfWeekRule ruleOfWeekend = new DayOfWeekRule(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            Duration.ofSeconds(3600), Money.of(2000D));

        final DayOfWeekCalc dayOfWeekCalc = new DayOfWeekCalc(Set.of(ruleOfWeekday, ruleOfWeekend));

        final Money actual = dayOfWeekCalc.calculate(Money.ZERO, Set.of(call));

        assertThat(actual).isEqualTo(Money.of(2000 + 4000D));
    }
}
