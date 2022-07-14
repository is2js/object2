package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public class Calculator {

    private Set<Calc> calcs = new HashSet<>();

    public Calculator() {
    }

    public Calculator(final Calc calc) {
        if (calc == null) {
            throw new IllegalArgumentException("calc is null");
        }
        this.calcs.add(calc);
    }

    public Calculator setNext(final Calc calc) {
        if (calc == null) {
            throw new IllegalArgumentException("calc is null");
        }
        this.calcs.add(calc);
        return this;
    }

    final Money calculateCallFee(Money result, final Set<Call> calls) {
        check();

        for (final Calc calc : calcs) {
            result = calc.calculateFee(result, calls);
        }

        if (result.equals(Money.ZERO) || result.isLessThan(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }

        return result;
    }

    void check() {
        if (calcs.isEmpty()) {
            throw new IllegalArgumentException("calculator is empty");
        }
    }

    public Set<Calc> getCalcs() {
        return calcs;
    }
}
