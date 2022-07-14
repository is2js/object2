package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.util.Set;

public class TexCalc extends Calc {

    private Double ratio;

    public TexCalc(final Double ratio) {
        if (ratio == null || ratio < 0D) {
            throw new IllegalArgumentException("invalid ratio");
        }
        this.ratio = ratio;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        final Money tex = result.multi(ratio);
        if (tex.isGreaterThan(result) || tex.equals(result)) {
            throw new RuntimeException("calculate error");
        }
        return result.minus(tex);
    }
}
