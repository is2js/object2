package goodComposition.plan.calc;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.DateTimeInterval;
import goodComposition.Money;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekRuleTest {

    @DisplayName("")
    @Test
    void calculate() {
        //call 속 interval 중 하나가, 금요일 10시 ~11시
        final DateTimeInterval interval = DateTimeInterval.of(LocalDateTime.of(2022, 7, 22, 10, 0, 0),
            LocalDateTime.of(2022, 07, 22, 11, 0, 0));

        // 금요일이라면, 1분당 10원
        final DayOfWeekRule rule = new DayOfWeekRule(Set.of(DayOfWeek.FRIDAY),
            Duration.ofSeconds(60),
            Money.of(10D));

        final Money actual = rule.calculate(interval);

        assertThat(actual).isEqualTo(Money.of(10 * 60D));

    }
}
