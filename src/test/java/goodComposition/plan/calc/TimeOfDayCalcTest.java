package goodComposition.plan.calc;

import static org.assertj.core.api.Assertions.assertThat;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calculator;
import goodComposition.plan.Plan;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import org.assertj.core.api.Assertions;
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
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0), LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //10시 ~10시30분은 1분초당, 1000원 -> 30000만원
            // -> 내가 안넣어준 구간 10시30분~11시는 list에 X, for문X
            // -> base기본요금price/몇초당duration으로 처리되어야한다
            // --> call의 전체구간 vs 내가 요금을 부가한 구간 -> 나의 구간이 구멍날 수 있다.
            new Calculator(
                new TimeOfDayCalc()));

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }

    @DisplayName("")
    @Test
    void calculate_call_oneDay_fit() {
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0), LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //10시 ~10시30분은 1분초당, 1000원 -> 30000만원
            //10시30 ~ 11시은 1분초당, 2000원 -> 30000 + 60000만원
            new Calculator(new TimeOfDayCalc()));

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }

    @DisplayName("")
    @Test
    void calculate_call_oneDay_not_cross() {
        final Call call = new Call(LocalDateTime.of(2022, 7, 16, 10, 0, 0), LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        // 10~11시 동안 call ( 날짜별로 interval로 나뉘나, 하루로 설정)

        final Plan plan = new Plan();
        plan.addCall(call);
        plan.setCalculator(
            //시간대별 요금부과하기를
            //11시 ~11시30분은 1분초당, 1000원 -> 겹치는 구간없어서 중간값 0 -> 에러
            //11시30 ~ 12시은 1분초당, 2000원 -> 겹치는 구간 없어서 중간값 0 -> 에러
            new Calculator(new TimeOfDayCalc()));

        final Money money = plan.calculateFee();
        System.out.println("money = " + money);
    }

    @DisplayName("")
    @Test
    void addRule() {
        final TimeOfDayCalc actual = new TimeOfDayCalc();
        final TimeOfDayRule expected = new TimeOfDayRule(Duration.ZERO, Money.ZERO, LocalTime.of(0, 0, 0), null);

        // rule은 받기전에는 NULL로 이루어진 시작특이점 객체로 초기화되어 있고
        assertThat(actual).extracting("rule").isEqualTo(expected);

        actual.addRule(Duration.ofSeconds(60), Money.of(10D), LocalTime.of(2, 0, 0));

        // rule은 받고 난 후에는  시작특이점 객체가 아니다.
        assertThat(actual).extracting("rule").isNotEqualTo(expected);
    }

    @DisplayName("")
    @Test
    void addRule_fail____다음타자의_구간은_같아서도_안된다() {
        final TimeOfDayCalc actual = new TimeOfDayCalc();

        Assertions.assertThatThrownBy(
                () -> actual.addRule(Duration.ofSeconds(60), Money.of(10D), LocalTime.of(0, 0, 0)))
            .isInstanceOf(IllegalArgumentException.class).hasMessage("invalid to");
    }


    @DisplayName("")
    @Test
    void calculate2() {
        final HashSet<Call> calls = new HashSet<>();
        final Call call = new Call(LocalDateTime.of(2022, 7, 15, 20, 0, 0), LocalDateTime.of(2022, 7, 16, 11, 0, 0));
        calls.add(call);
        // 20~24시, 00시~11시
        // 18시~24시는 시간당 30원이니까 -> 30 * 4 = 120원
        // 00시~9시는 10원
        // 9시~18시는 20원이니까 -> 9 * 10 + 2 * 20 = 130원

        final TimeOfDayCalc timeOfDayCalc = new TimeOfDayCalc();
        timeOfDayCalc.addRule(Duration.ofSeconds(3600), Money.of(10D), LocalTime.of(9, 0, 0));
        timeOfDayCalc.addRule(Duration.ofSeconds(3600), Money.of(20D), LocalTime.of(18, 0, 0));
        timeOfDayCalc.addRule(Duration.ofSeconds(3600), Money.of(30D), LocalTime.of(23, 59, 59));
        final Money result = timeOfDayCalc.calculate(Money.ZERO, calls);

        System.out.println("result = " + result); // 250
    }
}
