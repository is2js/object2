package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class TexCalc implements Calc {

    private Double ratio;

    public TexCalc(final Double ratio) {
        this.ratio = ratio;
    }

    @Override
    public Money calculateFee(final Money result, final Set<Call> calls) {
        return result.minus(result.multi(ratio));
    }
}
