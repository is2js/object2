package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.util.Set;

public class TexCalc extends Calc {

    private Double ratio;

    public TexCalc(final Double ratio) {
        this.ratio = ratio;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        return result.minus(result.multi(ratio));
    }
}
