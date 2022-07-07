package practice;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class CalculatorBasedOnCalls {

    private final Set<CalculatorStrategy> strategies = new HashSet<>();

    public CalculatorBasedOnCalls(final CalculatorStrategy strategy) {
        this.strategies.add(strategy);
    }

    public CalculatorBasedOnCalls setNext(final CalculatorStrategy strategy) {
        this.strategies.add(strategy);

        return this;
    }

    public Money calculateFee(final Set<Call> calls, Money result) {

        for (final CalculatorStrategy strategy : strategies) {
            result = result.plus(strategy.calculateFee(calls, result));
        }

        return result;
    }
}
