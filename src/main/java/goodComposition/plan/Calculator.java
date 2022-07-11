package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public abstract class Calculator {

    private Calculator next;

    public final Calculator setNext(final Calculator next) {
        this.next = next;
        return this;
    }

    public final Money calculateCallFee(Money result, final Set<Call> calls) {
        result = calculateFee(result, calls);

        return next == null ? result : next.calculateCallFee(result, calls);
    }

    protected abstract Money calculateFee(Money result, Set<Call> calls);
}
