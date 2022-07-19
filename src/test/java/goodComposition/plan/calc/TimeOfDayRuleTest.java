package goodComposition.plan.calc;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Money;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeOfDayRuleTest {

    @DisplayName("")
    @Test
    void calculate() {

        final TimeOfDayRule rule = new TimeOfDayRule(Duration.ofSeconds(60),
            Money.of(10D),  // 60초당 10원씩 부과하며
            LocalTime.of(1, 0, 0), // 나의 구간은 1시까지다 -> 직전은 0시
            new TimeOfDayRule(Duration.ZERO, Money.ZERO, LocalTime.of(0, 0, 0), null));

        final Money actual = rule.calculate(Duration.between(LocalTime.of(0, 0, 0),
            LocalTime.of(2, 0, 0)));// 전체 처리할 구간은 0시~2시임.

        final Money expected = Money.of(3600 / 60 * 10D); // 1분당 10원이면, 1시간에는 600원
        assertThat(actual).isEqualTo(expected);
    }
}
