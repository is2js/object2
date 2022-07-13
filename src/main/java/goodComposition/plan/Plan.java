package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class Plan {

    private final Set<Call> calls = new HashSet<>();
    private Calculator calculator = new Calculator();

    public final void addCall(Call call) {
        if (call == null) {
            throw new IllegalArgumentException("call is null");
        }
        calls.add(call);
    }

    public void setCalculator(final Calculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("calculator is null");
        }
        if (calculator.isEmpty()) {
            throw new IllegalArgumentException("calculator is empty");
        }

        this.calculator = calculator;
    }

    public final Money calculateFee() {
        final Money result = calculator.calculateCallFee(Money.ZERO, calls);
        if (calls.size() > 0 && (result.equals(Money.ZERO) || result.isLessThan(Money.ZERO))) {
            throw new RuntimeException("calculate error");
        }
        return result;
    }

    public Calculator getCalculator() {
        return calculator;
    }
}
