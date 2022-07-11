package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class TexCalculator extends Calculator {

    private Double ratio;

    public TexCalculator(final Calculator next, final Double ratio) {
        this.ratio = ratio;
    }

    @Override
    protected Money calculateFee(final Money result, final Set<Call> calls) {
        return result.minus(result.multi(ratio));
    }
}
