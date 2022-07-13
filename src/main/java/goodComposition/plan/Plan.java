package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class Plan {

    private final Set<Call> calls = new HashSet<>();
    private Calculator calculator = new Calculator();

    public final void addCall(Call call){
        calls.add(call);
    }

    public void setCalculator(final Calculator calculator) {
        this.calculator = calculator;
    }

    public final Money calculateFee() {
        return calculator.calculateCallFee(Money.ZERO, calls);
    }

    public Calculator getCalculator() {
        return calculator;
    }
}
