package goodComposition.plan.calc;

import goodComposition.Call;
import goodComposition.Money;
import goodComposition.plan.Calc;
import java.util.Set;

public class AmountDiscountCalc extends Calc {

    private Money amount;

    public AmountDiscountCalc(final Money amount) {
        if (amount == null || amount.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("invalid amount");
        }
        this.amount = amount;
    }

    @Override
    protected Money calculate(final Money result, final Set<Call> calls) {
        if (amount.isgreaterThan(result) || amount.equals(result)) {
            throw new RuntimeException("calculate error");
        }
        return result.minus(amount);
    }

}
