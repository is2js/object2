package practice;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class SecondStrategy implements CalculatorStrategy {
    @Override
    public Money calculateFee(final Set<Call> calls, final Money money) {
        throw new UnsupportedOperationException("SecondStrategy#calculateFee not implemented.");
    }
}
