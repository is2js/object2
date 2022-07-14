package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class AmountDiscountCalc extends Calc {

    private Money amount;

    public AmountDiscountCalc(final Money amount) {
        this.amount = amount;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        return result.minus(amount);
    }

}
