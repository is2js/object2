package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public abstract class Calc {

    final Money calculateFee(final Money result, final Set<Call> calls) {
        return calculate(result, calls);
    }

    protected abstract Money calculate(Money result, final Set<Call> calls);
}
