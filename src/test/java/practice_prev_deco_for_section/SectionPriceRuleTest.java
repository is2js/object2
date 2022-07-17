package practice_prev_deco_for_section;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Money;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SectionPriceRuleTest {

    @DisplayName("")
    @Test
    void calculate() {
        final LocalDateTime from = LocalDateTime.of(2022, 7, 16, 10, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2022, 7, 16, 15, 30, 0);
        final Duration duration = Duration.between(from, to);

        //1. 구간별 요금을 인스턴스별로 처리해줄 데코레이터 객체 생성
        final SectionPriceRule sectionPriceRule = new SectionPriceRule(
            Duration.between(LocalTime.of(10, 0, 0), LocalTime.of(12, 0, 0)), // 내가 맞을 구간
            Money.of(1000D), // 내 구간별 계산 정보
            new SectionPriceRule(Duration.ZERO, Money.ZERO, null) //prev
            // prev (구간처리 데코, 시작특이점 데코는, 시작 특이점(구간 to 0)을 알고 있어 prev를 저장)
        );

        //2. 구간처리 데코객체의 메서드에서는, 전체 구간을 받되,
        //   내부에서 prev의 상한 ~ 나의 상한으로 짤라 계산하여 반환해준다. 바깥에서 누적할 것이다.

        // 10시 ~      3시 30분의 전체 구간 가운데
        // 10시 ~ 12시만   초당 1000원으로 계산 -> 2시간만 초당 1000원 -> 7200 x 1000
        final Money actual = sectionPriceRule.calculate(duration);

        final Money expected = Money.of(7200000D);
        assertThat(actual).isEqualTo(expected);
    }
}
