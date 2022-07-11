package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public class AmountDiscountCalculator extends Calculator {

    private Money amount;

    public AmountDiscountCalculator(final Money amount) {
        this.amount = amount;
    }

    @Override
    protected Money calculateFee(final Money result, final Set<Call> calls) {
        return result.minus(amount);
    }

}
