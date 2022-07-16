package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calculator;
import goodComposition.plan.Plan;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeOfDayCalcTest {

    @DisplayName("")
    @Test
    void calculate() {
        final Duration between = Duration.between(LocalTime.of(10, 30, 15), LocalTime.of(10, 10, 00));
        System.out.println("between.getSeconds() = " + between.getSeconds());
    }

    @DisplayName("")
    @Test
    void calculate_call_oneDay_less() {
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0),
            LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //10시 ~10시30분은 1분초당, 1000원 -> 30000만원
            // -> 내가 안넣어준 구간 10시30분~11시는 list에 X, for문X
            // -> base기본요금price/몇초당duration으로 처리되어야한다
            // --> call의 전체구간 vs 내가 요금을 부가한 구간 -> 나의 구간이 구멍날 수 있다.
            new Calculator(new TimeOfDayCalc(Money.of(1000D), Duration.ofSeconds(3600),
                Arrays.asList(LocalTime.of(10, 0, 0)),
                Arrays.asList(LocalTime.of(10, 30, 0)),
                Arrays.asList(Duration.ofSeconds(60)),
                Arrays.asList(Money.of(1000D))
            ))
        );

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }

    @DisplayName("")
    @Test
    void calculate_call_oneDay_fit() {
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0),
            LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //10시 ~10시30분은 1분초당, 1000원 -> 30000만원
            //10시30 ~ 11시은 1분초당, 2000원 -> 30000 + 60000만원
            new Calculator(new TimeOfDayCalc(Money.of(1000D), Duration.ofSeconds(3600),
                Arrays.asList(LocalTime.of(10, 0, 0), LocalTime.of(10, 30, 0)),
                Arrays.asList(LocalTime.of(10, 30, 0), LocalTime.of(11, 0, 0)),
                Arrays.asList(Duration.ofSeconds(60), Duration.ofSeconds(60)),
                Arrays.asList(Money.of(1000D), Money.of(2000D))
            ))
        );

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }

    @DisplayName("")
    @Test
    void calculate_call_oneDay_not_cross() {
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0),
            LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //11시 ~11시30분은 1분초당, 1000원 -> 겹치는 구간없어서 중간값 0 -> 에러
            //11시30 ~ 12시은 1분초당, 2000원 -> 겹치는 구간 없어서 중간값 0 -> 에러
            new Calculator(new TimeOfDayCalc(Money.of(1000D), Duration.ofSeconds(3600),
                Arrays.asList(LocalTime.of(11, 0, 0), LocalTime.of(12, 30, 0)),
                Arrays.asList(LocalTime.of(11, 30, 0), LocalTime.of(12, 0, 0)),
                Arrays.asList(Duration.ofSeconds(60), Duration.ofSeconds(60)),
                Arrays.asList(Money.of(1000D), Money.of(2000D))
            ))
        );

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }
}
