package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.time.Duration;
import java.util.Set;

public class PricePerTimeCalculator extends Calculator {

    private Money price; // 초당 요금
    private Duration second; // 총 통화시간에 대해 몇초마다 적용할 것인지

    public PricePerTimeCalculator(final Money price, final Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    protected Money calculateFee(Money result, final Set<Call> calls) {
        for (final Call call : calls) {
            result = result.plus(price.times(call.getDuration().getSeconds() / second.getSeconds()));
        }
        return result;
    }
}
