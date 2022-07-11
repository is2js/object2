package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class AmountDiscountCalc implements Calc {

    private Money amount;

    public AmountDiscountCalc(final Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(final Money result, final Set<Call> calls) {
        return result.minus(amount);
    }

}
