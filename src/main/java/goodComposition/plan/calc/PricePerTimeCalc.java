package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.time.Duration;
import java.util.Set;

public class PricePerTimeCalc extends Calc {

    private Money price; // 초당 요금
    private Duration second; // 총 통화시간에 대해 몇초마다 적용할 것인지

    public PricePerTimeCalc(final Money price, final Duration second) {
        if (price == null || price.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid price");
        }
        if (second == null || second.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("invalid second");
        }

        this.price = price;
        this.second = second;
    }

    @Override
    protected Money calculate(Money result, final Set<Call> calls) {
        Money sum = Money.ZERO;
        for (final Call call : calls) {
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
