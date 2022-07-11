package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class TexCalculator implements Calculator {

    private final Calculator next;
    private Double ratio;

    public TexCalculator(final Calculator next, final Double ratio) {
        this.next = next;
        this.ratio = ratio;
    }


    @Override
    public Money calculateCallFee(Money result, final Set<Call> calls) {
        result = result.minus(result.multi(ratio));

        return next == null ? result : next.calculateCallFee(result, calls);
    }
}
