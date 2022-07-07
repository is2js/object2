package practice;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class Plan {
    private CalculatorBasedOnCalls calculator;
    private Set<Call> calls = new HashSet<>();

    public void setCalculator(final CalculatorBasedOnCalls calculator) {
        this.calculator = calculator;
    }

    public Money calculateFee() {
        return calculator.calculateFee(calls, Money.ZERO);
    }
}
