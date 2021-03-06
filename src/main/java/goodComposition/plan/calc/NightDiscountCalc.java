package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class NightDiscountCalc extends Calc {

    private Money nightPrice; // 통화시작시간이 밤일 때  기준시간당 가격
    private Money dayPrice;  // 통화시작시간이 낮일 때 기준시간당 가격
    private LocalTime nightTime; // 밤의 기준시각
    private Duration second; // 몇초당 요금이 부과될지

    public NightDiscountCalc(final Money dayPrice,
                             final Money nightPrice,
                             final LocalTime nightTime,
                             final Duration second) {
        if (nightPrice == null || nightPrice.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid nightPrice");
        }
        if (dayPrice == null || dayPrice.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid dayPrice");
        }
        if (nightTime == null) {
            throw new IllegalArgumentException("invalid nightTime");
        }
        if (second == null || second.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("invalid second");
        }

        this.nightPrice = nightPrice;
        this.dayPrice = dayPrice;
        this.nightTime = nightTime;
        this.second = second;
    }

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
            // 통화시작시간이 22시를 넘으면 ? 할인 요금(야간요금) : 그렇지 않으면 일반요금(주간요금)을 기준으로 삼는다.
            // - 할당이 2가지를 if칠 땐 삼항연산자를 쓴다.
            final Money price = call.isFromOverAndEqualTo(nightTime) ? nightPrice : dayPrice;

            final Money tempResult = price.times(call.getDuration().getSeconds() / second.getSeconds());
            if (tempResult.isLessThanOrEqualTo(Money.ZERO)) {
                throw new RuntimeException("calculate error");
            }
            sum = sum.plus(tempResult);
        }

        if (sum.isLessThanOrEqualTo(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }

        return result.plus(sum);
    }
}
